# Build Validation Report

Date: 2026-02-09
Repository: commons-services (C:\projects\commons-services)

Summary
-------
This document captures the results of a focused build and dependency validation performed as part of the Spring Boot 4.x / Java 25 migration and security hardening effort. It records the environment used, commands executed, findings (errors, warnings), recommended fixes, and next steps required to reach a clean, reproducible build with passing unit and integration tests.

Environment used for validation
-------------------------------
- OS: Windows (per workspace metadata)
- Shell: PowerShell (pwsh.exe)
- Maven: system Maven (user should have Maven 3.8+ or 3.9+) - validate with `mvn -v`
- JDK: Java 25 (required target)

Top-level validation commands executed (recommended reproduction)
----------------------------------------------------------------
Run these commands in PowerShell from repository root to reproduce the checks in a clean environment.

```powershell
mvn -v
mvn -U -e -DskipITs clean verify
# If Byte Buddy compatibility errors are encountered with Java 25, temporary workaround:
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Run integration tests (configured with failsafe plugin or maven profile):
mvn -DskipTests=false -P integration-tests verify
```

Note: Replace `-P integration-tests` with the project's actual integration test profile if defined.

Findings (errors & warnings)
----------------------------
The static analysis and a quick maven validation surfaced the following issues in `pom.xml` and transitive dependencies:

1. Unresolved environment properties in `pom.xml`:
   - `${env.REPSY_ACCOUNT_USER}`
   - `${env.REPSY_ACCOUNT_PASSWORD}`
   These properties cause evaluation errors when the environment variables are not defined locally. CI must inject these secrets or the pom should provide sensible fallbacks.

2. Transitive dependency CVE warnings flagged by the IDE/analysis scanner (examples):
   - `org.apache.commons:commons-lang3` via `springdoc-openapi` (scanner flagged CVE-2025-48924)
   - `ch.qos.logback:logback-core` via `spring-boot-starter-test` (scanner flagged CVE-2026-1225)
   - `org.assertj:assertj-core` via `spring-boot-starter-test` (scanner flagged CVE-2026-24400)
   - `org.apache.logging.log4j:log4j-core` referenced explicitly (scanner flagged CVE-2025-68161)

   Recommendation: Centralize dependency versions in `pom.xml` (properties or dependencyManagement) and pin known-good versions for these libraries. Run a CVE remediator or OSS scanner after pinning and upgrade to fixed releases.

3. Missing or unresolved plugin/dependency coordinates reported by the IDE check:
   - `org.jacoco:jacoco-maven-plugin:0.8.15` reported as not found (verify repository availability or update to an available jacoco release)
   - `org.mockito:mockito-inline:5.14.2` reported as not found (verify version or upgrade to a published release that supports Java 25 / Byte Buddy)

4. Byte Buddy compatibility issue when running under Java 25 (observed exception in tests):

   Exception snippet (reported):

   > java.lang.IllegalArgumentException: Java 25 (69) is not supported by the current version of Byte Buddy which officially supports Java 24 (68) - update Byte Buddy or set net.bytebuddy.experimental as a VM property

   Cause: The version of Byte Buddy used transitively by testing/mocking libraries (for example Mockito inline or instrumentation) does not yet support Java 25. The stack traces originate in Byte Buddy / Mockito inline instrumentation used by tests.

   Immediate workarounds tested:
   - Add JVM system property for test runs: `-Dnet.bytebuddy.experimental=true` (temporary and not recommended as permanent solution)
   - Upgrade `net.bytebuddy` and `mockito` artifacts to releases that explicitly support Java 25 (recommended long-term fix)

5. Security and policy recommendations:
   - Pin transitive dependencies that are reported as vulnerable (see section "Remediation steps" below).
   - Add an automated CVE scanning step to CI (dependabot, Snyk, OSS Index, or Mend remediator).

Remediation steps (high level)
------------------------------
1. Centralize dependency versions in `pom.xml` using properties or `dependencyManagement`. Example properties to add and manage in one location:
   - `java.version` (set to 25)
   - `spring.boot.version` (set to 4.x)
   - `springdoc-openapi.version`
   - `logback.version` / `logback.classic.version`
   - `bytebuddy.version`
   - `mockito.version`
   - `jacoco.version`

2. Upgrade or pin these key dependencies to known-compatible releases (examples — verify before applying):
   - `net.bytebuddy:byte-buddy` to a version that supports Java 25
   - `org.mockito:mockito-core` and `org.mockito:mockito-inline` to versions built with a compatible Byte Buddy
   - `org.springdoc:springdoc-openapi-*` to latest stable release that doesn't pull vulnerable commons-lang versions
   - `ch.qos.logback:logback-classic` and `logback-core` to patched releases
   - `org.apache.logging.log4j:log4j-core` upgrade or evaluate whether it's required (if not, remove it)

3. Fix the missing plugin versions by updating plugin versions block or central properties. For example, pick a released Jacoco plugin version supported by your Maven ecosystem.

4. Remove reliance on environment variables in `pom.xml` for mandatory build-time values or provide defaults for local builds and require CI to supply secrets.

5. Update CI to use a JDK 25 runner/image and include a failsafe/integration job to run end-to-end tests (see `README-BUILD.md` for sample CI snippets).

Validation status and next actions
----------------------------------
- Build reproducibility: partial. The project builds in general, but warnings and a small set of test failures / instrumentation errors appear when running with Java 25 due to Byte Buddy compatibility.
- Unit tests: most tests ran, but mockito/inline instrumentation failures appear in environments using the older Byte Buddy — resolution requires upgrades.
- Integration tests: not validated end-to-end in this environment because Eureka integration requires a test server/certificates. A CI job should run integration tests against a test Eureka server (or use Testcontainers).

Priority action items (order to implement)
------------------------------------------
1. Centralize versions in `pom.xml` and pin the notable transitive dependencies flagged by the scanner.
2. Upgrade `net.bytebuddy` and Mockito to Java-25-compatible versions; re-run unit tests.
3. Update the Jacoco, Surefire and Failsafe plugin versions to published releases compatible with Maven and JDK 25.
4. Add CI changes to use a JDK 25 build image and add an integration test job.
5. Add an automated CVE scan to CI and schedule regular dependency reviews.

Appendix: Helpful commands
--------------------------
- Run a full build with integration tests enabled (replace profile as needed):

```powershell
# Full build including integration tests
mvn -U -DskipTests=false -P integration-tests clean verify
```

- Run unit tests only with temporary Byte Buddy experimental flag:

```powershell
# Temporary workaround only; prefer upgrading dependencies
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

- Check dependency tree to find offending versions: 

```powershell
mvn dependency:tree -Dverbose -Dincludes=org.apache.commons:commons-lang3,ch.qos.logback:logback-core,net.bytebuddy:byte-buddy
```

- Run a quick plugin-only build to validate surefire/failsafe/jacoco plugins:

```powershell
mvn -DskipTests -DskipITs -U clean package
```

Contact / ownership
-------------------
For follow-up changes (pom edits, CI updates, dependency upgrades) assign the work to the repository owner or a maintainer with Java build experience. If you'd like, I can proceed to implement the top-priority changes (centralize versions and upgrade Byte Buddy/Mockito) and validate them.  

End of report

