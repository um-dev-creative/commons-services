# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

`commons-services` is a **shared library** (JAR), not a runnable application. It provides reusable service components, interfaces, and utilities consumed by other PRX microservices. It is published to a private Repsy Maven repository (`repo.repsy.io/mvn/lmata/prx`).

## Build and test commands

```bash
# Full build: compile, test, PMD check, JaCoCo coverage verification
mvn clean verify

# Skip integration tests (standard local workflow)
mvn -DskipITs clean verify

# Run tests only (no coverage enforcement)
mvn test

# Run a single test class
mvn test -Dtest=LoggingServiceImpTest

# Run a single test method
mvn test -Dtest=LoggingServiceImpTest#methodName

# If Byte Buddy/Mockito fails with Java compatibility errors
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Generate JaCoCo coverage report (HTML + XML at target/site/jacoco/)
mvn verify
```

## Quality gates (enforced at build time)

- **PMD**: runs at `test` phase using `ruleset.xml`. `failOnViolation=true` at priority 5. Exclusions match `sonar.exclusions` (config, loggers, mapper packages).
- **JaCoCo**: enforced at `verify` phase. Minimums: 80% line coverage (BUNDLE), 50% branch coverage (PACKAGE). Same exclusions apply.
- Coverage is excluded from: `**/config/*`, `**/loggers/*`, `**/loggers/interceptor/*`, `**/mapper/*`

## Architecture

The library uses an **interface-with-default-methods** pattern throughout. Consumers implement the interface and override only what they need; unimplemented operations return HTTP 501 or throw `UnsupportedOperationException` by default.

### Core abstractions

| Interface / Class | Package | Purpose |
|---|---|---|
| `CrudService<A,T>` | `com.prx.commons.services` | Generic CRUD contract returning `ResponseEntity`; all methods default to HTTP 501 |
| `LoggingService` | `com.umdc.commons.services.loggers` | HTTP request/response logging contract |
| `LoggingServiceImp` | `com.umdc.commons.services.loggers` | Concrete logging implementation; enabled via `prx.logging.trace.enabled` |
| `ClientRestTemplate` | `com.umdc.commons.services.rest` | `RestTemplate` wrapper with buffered factory and Jackson converter |
| `ProfileImageApi` | `com.umdc.commons.services.cloudflare.controller` | Spring MVC interface for profile image REST endpoints; default methods return 501 |
| `ProfileImageService` | `com.umdc.commons.services.cloudflare.service` | Service contract for profile image save/retrieve operations |
| `CloudflareR2StorageClient` | `com.umdc.commons.services.cloudflare.r2.client` | AWS SDK v2 S3 client configured for Cloudflare R2 (path-style, chunked encoding disabled) |

### Configuration properties

| Class | Prefix | Purpose |
|---|---|---|
| `CloudflareR2Properties` | `cloudflare.r2` | R2 account ID, endpoint, credentials, bucket, public URL |
| `SecurityProperties` | `prx.security` | Keystore, truststore, management authenticator |
| `ManagementAuthenticatorProperties` | (nested under `prx.security`) | Auth credentials for management endpoints |
| `StoreProperties` | (nested under `prx.security`) | Keystore/truststore path, password, type |
| `DiscoveryClientProperties` | (own prefix) | Eureka/discovery client settings |

### Cloudflare R2 integration notes

`CloudflareR2StorageClient` wraps the AWS SDK v2 S3 client pointed at a Cloudflare R2 endpoint. Two non-obvious configuration requirements:
- `pathStyleAccessEnabled(true)` — required for R2
- `chunkedEncodingEnabled(false)` — prevents 403 errors from R2
- Region is set to `"auto"` (required by the SDK but ignored by R2)

### Logging interceptors

`LoggerWebConfigurer` registers `RequestBodyInterceptor` and `ResponseBodyInterceptor` (Spring MVC `HandlerInterceptor`) which delegate to `LoggingServiceImp`. Trace logging is controlled by `${LOGGING_TRACE_ENABLED}` (mapped to `prx.logging.trace.enabled` in `application.yml`).

## Dependency notes

- `prx-commons` (`com.prx:prx-commons:0.0.1`) is a PRX internal library resolved from the Repsy repo — required for `HttpStatusUtil` and other shared types.
- Requires `REPSY_ACCOUNT_USER` / `REPSY_ACCOUNT_PASSWORD` environment variables for publishing; reads from `env.*` in `pom.xml`.
