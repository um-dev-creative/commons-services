# AGENTS.md — Shared AI Policy for commons-services

This file is the **single source of truth** for all AI agents working in this repository.
It is read by both **GitHub Copilot** (via `.github/copilot-instructions.md`) and
**Claude Code** (via `CLAUDE.md`). Detailed agent, skill, role, and configuration
definitions live in `.ai/agents.yml`, `.ai/skills.yml`, `.ai/roles.yml`, `.ai/confs.yml`.

---

## 1. Project Identity

| Property | Value |
|---|---|
| Artifact | `com.prx:commons-services:0.0.1` |
| Type | **Shared library JAR** — not a runnable application |
| Java | 21 |
| Spring Boot | 3.5.8 |
| Spring Cloud | 2025.0.1 |
| Published to | Repsy private Maven (`repo.repsy.io/mvn/lmata/prx`) |
| Build truth | `pom.xml` |

> **Migration note:** `README-BUILD.md` and `BUILD_VALIDATION_REPORT.md` discuss a
> Java 25 / Boot 4 upgrade path. Treat those as future migration notes — they are
> **not** the current baseline. All agents must default to Java 21 + Spring Boot 3.5.8.

---

## 2. Read These Files First

Before making any change, read:

1. `pom.xml` — dependency versions, plugins, PMD/JaCoCo gates, Repsy repo
2. `src/main/java/com/prx/commons/services/CrudService.java` — canonical default-method contract style
3. `src/main/java/com/prx/commons/services/loggers/` — full logging interceptor chain
4. `src/main/java/com/prx/commons/services/cloudflare/` — R2 integration + profile image contracts
5. `src/test/java/com/prx/commons/services/` — tests document intended default behaviors

---

## 3. Rules — Invariants All Agents Must Preserve

### 3.1 Library Contract Rules

- This is a **shared library**. Every public API surface is consumed by downstream microservices.
- All shared interfaces MUST provide safe default method implementations:
  - `CrudService` defaults → `ResponseEntity` with `HttpStatus.NOT_IMPLEMENTED` (HTTP 501)
  - `ProfileImageService` defaults → `throw new UnsupportedOperationException("Not implemented")`
  - `ProfileImageApi` defaults → `ResponseEntity.status(HttpStatusUtil.NOT_IMPLEMENTED).body(...)`
- Consumers implement only what they need. New methods MUST have defaults — never add abstract-only methods to published interfaces.
- Prefer **additive** API evolution. Never remove or rename a public method, constructor, or class.
- Use `HttpStatusUtil` constants for all HTTP status references in `ProfileImageApi`. Never use raw integer literals or ad-hoc strings.

### 3.2 Logging Rules

- The logging pipeline is: `LoggerWebConfigurer → LogInterceptor → RequestBodyInterceptor → ResponseBodyInterceptor → LoggingService`
- All three interceptors MUST delegate to `LoggingService` — never log directly from a controller or endpoint.
- Tracing is controlled **only** by `prx.logging.trace.enabled` (env: `LOGGING_TRACE_ENABLED`).
- `LogInterceptor.preHandle` fires only for GET, POST, PUT, DELETE. Must always return `true`.
- `LoggingServiceImp` MUST use `ConcurrentHashMap` when collecting headers/parameters.

### 3.3 Cloudflare R2 Rules

The following S3 client settings in `CloudflareR2StorageClient` are **non-negotiable**:

| Setting | Value | Reason |
|---|---|---|
| `pathStyleAccessEnabled` | `true` | R2 does not support virtual-hosted-style URLs |
| `chunkedEncodingEnabled` | `false` | Chunked encoding causes HTTP 403 on R2 |
| `region` | `Region.of("auto")` | Required by AWS SDK v2; ignored by R2 |

The S3Client MUST be lazily initialised (not in the constructor). Credentials MUST come from `CloudflareR2Properties`.

### 3.4 Code Style Rules

- Constructor injection only — no `@Autowired` on fields, no Lombok `@RequiredArgsConstructor`
- No Lombok on any class — use explicit getters and setters
- Java records for all transfer objects (TOs)
- SLF4J for all logging: `LoggerFactory.getLogger(ClassName.class)`
- No `System.out.println` or `System.err`
- MapStruct mappers MUST declare `config = MapperAppConfig.class`

### 3.5 Build Rules

- Java 21 and Spring Boot 3.5.8 are the active baseline — do not change versions without an explicit migration scope
- `pom.xml` is the single source of truth for all dependency versions and plugin settings
- PMD configuration lives in `ruleset.xml` — do not inline PMD rules elsewhere

---

## 4. Restrictions — Things Agents Must Never Do

| ID | Restriction |
|---|---|
| `NO_BREAKING_INTERFACE_SIGNATURES` | Never change a published public method signature |
| `NO_ADHOC_HTTP_STATUS_LITERALS` | Never use raw integer or string HTTP status codes in `ProfileImageApi` |
| `NO_CONTROLLER_SPECIFIC_LOGGING_BYPASS` | Never add per-controller or per-endpoint logging bypasses |
| `NO_R2_S3_CONFIG_DRIFT` | Never change the three R2 S3 client settings without explicit approval |
| `NO_R2_CREDENTIAL_LOG` | Never log `accessKey` or `secretKey` at any log level |
| `NO_SKIP_VERIFY` | Never suggest bypassing `mvn verify` on the main build path |
| `NO_DISABLE_PMD` | Never disable or skip PMD checks |
| `NO_REDUCE_JACOCO_THRESHOLDS` | Never lower line (80%) or branch (50%) coverage minimums |
| `NO_PUBLISH_WITHOUT_GRANT` | Never run `mvn deploy` unless `AI_GRANT_PUBLISH_ARTIFACT=true` is set |
| `NO_FORCE_PUSH` | Never force-push to any branch |
| `NO_HARDCODED_CREDENTIALS` | Never embed credentials in source files |
| `NO_COMMIT_ENV_SECRETS` | Never commit `.env.local`, tokens, passwords, or keys |
| `NO_WEAKEN_SSL_CONFIG` | Never reduce SSL/TLS settings in SecurityProperties or DiscoveryClientProperties |
| `NO_SPECULATIVE_ABSTRACTIONS` | Do not create helpers, utilities, or abstractions for one-time operations |
| `NO_LOMBOK` | Do not introduce Lombok annotations anywhere in the project |
| `NO_FIELD_INJECTION` | Do not use `@Autowired` on fields |

---

## 5. Grants — Explicit Permissions

| ID | Permission | Default |
|---|---|---|
| `EDIT_SRC_MAIN` | Modify files under `src/main/java` | `true` |
| `EDIT_SRC_TEST` | Modify files under `src/test/java` | `true` |
| `EDIT_BUILD_FILES` | Modify `pom.xml`, `ruleset.xml` | `true` |
| `CREATE_TESTS` | Add new test classes | `true` |
| `CREATE_DOCS` | Add/update files in `docs/`, `AGENTS.md`, `CLAUDE.md` | `true` |
| `RUN_MAVEN_VERIFY` | Execute `mvn -DskipITs clean verify` | `true` |
| `RUN_TARGETED_TESTS` | Execute `mvn test -Dtest=...` | `true` |
| `USE_BYTE_BUDDY_WORKAROUND` | Add `-Dnet.bytebuddy.experimental=true` when needed | `true` |
| `ADD_NEW_INTERFACES` | Create new interfaces with default-method stubs | `true` |
| `ADD_NEW_PROPERTIES_CLASSES` | Create new `@ConfigurationProperties` classes | `true` |
| `ADD_OPENAPI_ANNOTATIONS` | Add SpringDoc annotations to controller interfaces | `true` |
| `PUBLISH_ARTIFACT` | Run `mvn clean deploy` | **`false`** |
| `DEPLOY_CHANGES` | Deploy to any environment | **`false`** |
| `MODIFY_CI_PIPELINES` | Edit `.github/workflows/` files | **`false`** |
| `FORCE_PUSH` | `git push --force` | **`false`** |

---

## 6. Agent Roster

### Developer Agents (own source code areas)

| Agent ID | Owned Area | Role |
|---|---|---|
| `contracts-architect` | `CrudService`, `ProfileImageApi`, `ProfileImageService`, `LoggingService`, `cloudflare/to/` | Shared Library Maintainer |
| `logging-flow-specialist` | `loggers/` package + `application.yml` | Integration Maintainer |
| `cloudflare-r2-specialist` | `cloudflare/` package (all) | Integration Maintainer |
| `rest-config-maintainer` | `config/`, `properties/`, `rest/`, `util/` | Shared Library Maintainer |

### Supporting Agents (cross-cutting)

| Agent ID | Responsibility | Role |
|---|---|---|
| `build-quality-guardian` | PMD + JaCoCo gates, `mvn verify` | Quality Gatekeeper |
| `code-review-auditor` | Diff review, contract risk, coverage gaps | Reviewer |
| `dependency-security-guardian` | CVE audits, dependency upgrades in `pom.xml` | Security Maintainer |
| `release-publisher` | `mvn clean deploy` to Repsy | Release Operator |
| `docs-governance-maintainer` | AGENTS.md, CLAUDE.md, docs/, copilot-instructions.md, .ai/ | Documentation Maintainer |

Full definitions: `.ai/agents.yml` · `.ai/roles.yml` · `.ai/skills.yml`

---

## 7. Build & Test Commands

```bash
# Standard local build (compile + test + PMD + JaCoCo)
mvn -DskipITs clean verify

# Tests only (no coverage enforcement)
mvn test

# Single test class
mvn test -Dtest=LoggingServiceImpTest

# Single test method
mvn test -Dtest=LoggingServiceImpTest#displayRequest_logs_with_parameters_and_body

# Byte Buddy workaround (only when JVM compatibility error occurs)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Generate JaCoCo HTML + XML reports
mvn verify
# Report: target/site/jacoco/jacoco.xml and target/site/jacoco/index.html
```

---

## 8. Quality Gates

| Gate | Tool | Threshold | Phase |
|---|---|---|---|
| Line coverage | JaCoCo | ≥ 80% (BUNDLE) | `verify` |
| Branch coverage | JaCoCo | ≥ 50% (PACKAGE) | `verify` |
| Static analysis | PMD (`ruleset.xml`) | No violations at priority ≤ 5 | `test` |
| Duplicate code | PMD CPD | No violations | `test` |

**Coverage exclusions** (no pressure, no required tests):
`**/config/*`, `**/loggers/*`, `**/loggers/interceptor/*`, `**/mapper/*`

---

## 9. Configuration Property Prefixes

| Class | Prefix | Bound By |
|---|---|---|
| `CloudflareR2Properties` | `cloudflare.r2` | `@ConfigurationProperties` + `@Configuration` |
| `SecurityProperties` | `prx.security` | `@ConfigurationProperties` + `@Component` |
| `DiscoveryClientProperties` | `security.discovery.client` | `@ConfigurationProperties` + `@Component` |
| `LoggingServiceImp` | `prx.logging.trace.enabled` | `@Value` |
| `PrinterUtil` | `log.debug` | `@Value` |

---

## 10. Key Environment Variables

| Variable | Maps To | Sensitive |
|---|---|---|
| `LOGGING_TRACE_ENABLED` | `prx.logging.trace.enabled` | No |
| `CLOUDFLARE_ACCOUNT_ID` | `cloudflare.r2.accountId` | No |
| `CLOUDFLARE_R2_ENDPOINT` | `cloudflare.r2.endpoint` | No |
| `CLOUDFLARE_R2_ACCESS_KEY` | `cloudflare.r2.accessKey` | **Yes** |
| `CLOUDFLARE_R2_SECRET_KEY` | `cloudflare.r2.secretKey` | **Yes** |
| `CLOUDFLARE_R2_BUCKET_NAME` | `cloudflare.r2.bucketName` | No |
| `CLOUDFLARE_R2_PUBLIC_URL` | `cloudflare.r2.publicUrl` | No |
| `REPSY_ACCOUNT_USER` | Maven publish only | **Yes** |
| `REPSY_ACCOUNT_PASSWORD` | Maven publish only | **Yes** |

Sensitive variables MUST be supplied via CI secrets or `.env.local`. Never commit them.

---

## 11. Architecture Summary

```
com.prx.commons.services
├── CrudService<A,T>              Generic CRUD contract (HTTP 501 defaults)
├── config/mapper/
│   ├── JacksonConfig             ObjectMapper + JavaTimeModule bean
│   └── MapperAppConfig           MapStruct base config (@MapperConfig)
├── loggers/
│   ├── LoggingService            HTTP logging contract
│   ├── LoggingServiceImp         @Service — controlled by prx.logging.trace.enabled
│   ├── config/LoggerWebConfigurer WebMvcConfigurer — registers LogInterceptor
│   └── interceptor/
│       ├── LogInterceptor        HandlerInterceptor (preHandle)
│       ├── RequestBodyInterceptor RequestBodyAdviceAdapter (afterBodyRead)
│       └── ResponseBodyInterceptor ResponseBodyAdvice (beforeBodyWrite)
├── properties/
│   ├── SecurityProperties        prx.security.*
│   └── DiscoveryClientProperties security.discovery.client.*
├── rest/ClientRestTemplate       Buffered RestTemplate wrapper
├── util/PrinterUtil              Conditional logger utility
└── cloudflare/
    ├── controller/ProfileImageApi   Spring MVC REST interface
    ├── service/ProfileImageService  Service contract
    ├── r2/client/CloudflareR2StorageClient  AWS SDK v2 S3 client
    ├── properties/
    │   ├── CloudflareR2Properties   cloudflare.r2.*
    │   ├── ManagementAuthenticatorProperties
    │   └── StoreProperties
    └── to/
        ├── PostProfileImageResponse          (record)
        └── GetProfileImageReferenceResponse  (record)
```

---

## 12. Testing Conventions

- Use `@ExtendWith(MockitoExtension.class)` — no full Spring context unless required
- Use `@ExtendWith(SpringExtension.class)` only when Spring bean lifecycle is needed
- Use `ReflectionTestUtils.setField(instance, "fieldName", value)` for `@Value`-injected fields
- Tests for default-method contracts assert the fallback behavior (not the override)
- One test class per unit — one interface, one class, one interceptor
- New code must not drop below 80% line / 50% branch thresholds

---

*This file is maintained by the `docs-governance-maintainer` agent. For the full agent, skill, role, and configuration specification, see `.ai/agents.yml`, `.ai/skills.yml`, `.ai/roles.yml`, `.ai/confs.yml`.*
