README - Build & Run Guide

This guide explains how to build, test, and run the `commons-services` project locally and in CI. It also includes recommended settings when targeting Java 25 and Spring Boot 4.x.

Prerequisites
-------------
- JDK 25 installed and available on PATH. Verify with:

```powershell
java -version
```

- Apache Maven 3.8+ (3.9 recommended). Verify with:

```powershell
mvn -v
```

- Internet access to download dependencies from Maven Central or configured internal repositories.

Recommended environment variables
---------------------------------
- For builds that require repository credentials or publishing access, set the following in CI or your environment:
  - `REPSY_ACCOUNT_USER` - repository account user
  - `REPSY_ACCOUNT_PASSWORD` - repository account password

Note: The project `pom.xml` references `env.REPSY_ACCOUNT_USER` and `env.REPSY_ACCOUNT_PASSWORD`. Prefer configuring CI secrets and avoid setting these in local environments unless necessary.

Common build commands
---------------------
Run a standard build:

```powershell
mvn -U -DskipITs clean verify
```

Run unit tests only:

```powershell
mvn -DskipITs -DskipITs=false -DskipITs=false test
```

Run unit tests with a temporary Byte Buddy workaround (only if you see Java 25 compatibility errors):

```powershell
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

Run integration tests (requires configured test environment or profile):

```powershell
mvn -P integration-tests verify
```

Build for CI (fast path - skip integration tests):

```powershell
mvn -U -DskipITs -DskipTests -DskipITs=true clean package
```

Troubleshooting
---------------
- Byte Buddy compatibility error with Java 25:
  - Error: "Java 25 (69) is not supported by the current version of Byte Buddy..."
  - Short-term workaround: add `-Dnet.bytebuddy.experimental=true` to the maven command line for tests.
  - Long-term fix: upgrade `net.bytebuddy` and `mockito` to versions that support Java 25.

- Missing plugin or dependency versions:
  - Ensure your local Maven settings have access to central repositories.
  - If `jacoco-maven-plugin` or `mockito-inline` cannot be resolved, update `pom.xml` to use published versions and ensure proxy/repo settings are valid.

- Unresolved environment properties in `pom.xml`:
  - If you see unresolved `${env.REPSY_ACCOUNT_USER}` or `${env.REPSY_ACCOUNT_PASSWORD}`, set the environment variables in your shell, or configure CI secrets instead.

CI recommendations
------------------
- Use a JDK 25 capable runner/agent. For example on GitHub Actions use a runner or setup-jdk step that installs JDK 25.
- Add a job that runs unit tests and publishes test reports.
- Add a separate job that runs integration tests using a test environment or Testcontainers for dependent services (Eureka). Mark integration tests with a Maven profile and run them in a dedicated CI stage.
- Add an automated dependency CVE scan job (dependabot, OWASP Dependency-Check, or Mend/Snyk) to fail the build on critical vulnerabilities.

Sample GitHub Actions snippet (minimal)
---------------------------------------
This snippet installs JDK 25, runs the build, and runs integration tests in a separate job. Adapt as necessary.

```yaml
name: CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '25'
      - name: Build and run unit tests
        run: mvn -U -DskipITs clean verify

  integration-tests:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '25'
      - name: Build and run integration tests
        run: mvn -P integration-tests verify
```

Notes
-----
- Avoid long-lived feature branches that diverge from the centralized BOM or dependency management to minimize version conflicts.
- Prefer adding `jacoco` and other reporting plugins to CI for coverage verification.

If you want, I can now proceed to implement the prioritized fixes: centralize versions in `pom.xml`, pin Byte Buddy and Mockito, and run the test suite locally to validate the build. Would you like me to proceed with that?
