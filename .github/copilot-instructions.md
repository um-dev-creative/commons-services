# GitHub Copilot Instructions — commons-services

Full policy lives in **`AGENTS.md`**. This file is a concise Copilot-optimised
summary. When in doubt, `AGENTS.md` takes precedence.

---

## What this repo is

`commons-services` is a **shared library JAR** (`com.prx:commons-services:0.0.1`),
not a runnable application. It is consumed as a Maven dependency by PRX microservices.
**Java 21 · Spring Boot 3.5.8** are the active baseline.

> `README-BUILD.md` and `BUILD_VALIDATION_REPORT.md` discuss a Java 25 / Boot 4
> migration. Treat those as future notes — **not** the current baseline.

---

## Rules (always apply these)

### Interface contract
- Default methods in shared interfaces MUST return safe stubs:
  - `CrudService` → `ResponseEntity` with `HttpStatus.NOT_IMPLEMENTED`
  - `ProfileImageService` → `throw new UnsupportedOperationException("Not implemented")`
  - `ProfileImageApi` → `ResponseEntity.status(HttpStatusUtil.NOT_IMPLEMENTED)`
- New interface methods MUST provide a default — never add abstract-only methods to a published interface.
- Never remove, rename, or change the signature of a public published method.
- Use `HttpStatusUtil` constants — never raw integer or string HTTP status codes in `ProfileImageApi`.

### Logging pipeline
- Chain: `LoggerWebConfigurer → LogInterceptor → RequestBodyInterceptor → ResponseBodyInterceptor → LoggingService`
- All three interceptors delegate to `LoggingService` — never log directly in a controller.
- `LogInterceptor.preHandle` covers GET, POST, PUT, DELETE only and always returns `true`.
- Tracing is controlled only by `prx.logging.trace.enabled`. No per-endpoint bypass.

### Cloudflare R2 — three settings are non-negotiable
```java
S3Configuration.builder()
    .pathStyleAccessEnabled(true)   // required for R2
    .chunkedEncodingEnabled(false)  // prevents HTTP 403
    .build();
// region must be Region.of("auto")
```
Never change these without explicit approval.

### Code style
- Constructor injection only — no `@Autowired` fields, no Lombok
- Java records for transfer objects
- SLF4J logging — `LoggerFactory.getLogger(ClassName.class)`, no `System.out`
- Explicit getters/setters on `@ConfigurationProperties` classes
- MapStruct mappers must use `config = MapperAppConfig.class`

---

## Restrictions (never do these)

| Restriction | Description |
|---|---|
| `NO_BREAKING_INTERFACE_SIGNATURES` | Never change a published public method signature |
| `NO_ADHOC_HTTP_STATUS_LITERALS` | No raw integer/string HTTP status codes in `ProfileImageApi` |
| `NO_CONTROLLER_SPECIFIC_LOGGING_BYPASS` | No per-endpoint logging shortcuts |
| `NO_R2_S3_CONFIG_DRIFT` | Do not change the three R2 S3 client settings |
| `NO_R2_CREDENTIAL_LOG` | Never log `accessKey` or `secretKey` |
| `NO_REDUCE_JACOCO_THRESHOLDS` | Never lower 80% line / 50% branch minimums |
| `NO_DISABLE_PMD` | Never skip or disable PMD |
| `NO_HARDCODED_CREDENTIALS` | No credentials in source files |
| `NO_LOMBOK` | No Lombok annotations anywhere |
| `NO_FIELD_INJECTION` | No `@Autowired` on fields |
| `NO_SPECULATIVE_ABSTRACTIONS` | No helpers built for hypothetical future requirements |

---

## Build & test commands

```bash
# Standard build (compile + test + PMD + JaCoCo)
mvn -DskipITs clean verify

# Tests only
mvn test

# Single test class
mvn test -Dtest=LoggingServiceImpTest

# Single test method
mvn test -Dtest=LoggingServiceImpTest#methodName

# Byte Buddy workaround (only when JVM compatibility error occurs)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

---

## Quality gates

| Gate | Threshold | Phase |
|---|---|---|
| JaCoCo line coverage | ≥ 80% (BUNDLE) | `verify` |
| JaCoCo branch coverage | ≥ 50% (PACKAGE) | `verify` |
| PMD (`ruleset.xml`) | No violations at priority ≤ 5 | `test` |

**Excluded from coverage:** `**/config/*`, `**/loggers/*`, `**/loggers/interceptor/*`, `**/mapper/*`

---

## Testing style

- `@ExtendWith(MockitoExtension.class)` — prefer narrow unit tests, no full Spring context
- `@ExtendWith(SpringExtension.class)` — only when Spring bean lifecycle is needed
- `ReflectionTestUtils.setField(instance, "fieldName", value)` for `@Value`-injected fields
- One test class per unit (one interface, one interceptor, one class)

---

## Configuration property prefixes

| Class | Prefix |
|---|---|
| `CloudflareR2Properties` | `cloudflare.r2` |
| `SecurityProperties` | `prx.security` |
| `DiscoveryClientProperties` | `security.discovery.client` |

---

## Agent system

This repo uses a structured AI agent system. All agents respect the same policy:

| Agent | Area |
|---|---|
| `contracts-architect` | Interfaces, TOs, OpenAPI annotations |
| `logging-flow-specialist` | `loggers/` package |
| `cloudflare-r2-specialist` | `cloudflare/` package |
| `rest-config-maintainer` | `config/`, `properties/`, `rest/`, `util/` |
| `build-quality-guardian` | PMD + JaCoCo gates |
| `code-review-auditor` | Diff review |
| `dependency-security-guardian` | `pom.xml` CVE/version audits |
| `release-publisher` | `mvn deploy` to Repsy |
| `docs-governance-maintainer` | AGENTS.md, CLAUDE.md, docs/, .ai/ |

Full definitions: `.ai/agents.yml` · `.ai/skills.yml` · `.ai/roles.yml` · `.ai/confs.yml`

---

## Additional policy files

| File | Purpose |
|---|---|
| `AGENTS.md` | Full shared policy — authoritative for all agents |
| `CLAUDE.md` | Claude Code specific guidance |
| `.ai/agents.yml` | Agent definitions with scope, rules, grants |
| `.ai/skills.yml` | Skill definitions with guardrails and code patterns |
| `.ai/roles.yml` | Role mandates with must-preserve / must-avoid |
| `.ai/confs.yml` | Central configuration (versions, commands, gates) |
| `.env` | Policy flags, restrictions, grants as env vars |

---

## Copilot prompt catalog (Markdown standard)

GitHub Copilot prompt definitions now live under `.github/prompts/` in Markdown format:

- Agents: `.github/prompts/agents/*.prompt.md`
- Skills: `.github/prompts/skills/*.prompt.md`
- Features/workflows: `.github/prompts/features/*.prompt.md`
- Shared context: `.github/prompts/shared/*.prompt.md`
- Catalog docs: `.github/prompts/README.md`, `.github/prompts/MIRRORING.md`

Use these prompt files as Copilot-native entry points while keeping policy source-of-truth aligned with `AGENTS.md` and `.ai/*.yml`.

