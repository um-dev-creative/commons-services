# commons-services

Commons components and utilities used by service implementations.

## Overview

This repository provides shared service-related components used across multiple microservices. It contains logging utilities, web logging configuration, interceptors, and other common building blocks.

## Requirements

- Java 25 (JDK 25) is required to build and run the project.
- Maven 3.8+ (3.9+ recommended).
- Network access to Maven Central or your organization's proxy/repository.

Minimum supported framework versions:
- Spring Boot 4.x

See `CHANGELOG.md` and `BUILD_VALIDATION_REPORT.md` for migration details and records of the Spring Boot / Java upgrade work.

## Quick build (PowerShell)

Run the following commands from the repository root:

```powershell
# Verify Java and Maven
java -version
mvn -v

# Clean and run unit tests (skip integration tests)
mvn -U -DskipITs clean verify

# Run tests with a temporary Byte Buddy workaround (only if you encounter Java 25 compatibility errors)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Run integration tests (requires configured test environment/profile)
mvn -P integration-tests verify
```

## Known issues and workarounds

- Byte Buddy / Mockito compatibility with Java 25:
  - Symptom: Tests fail with the exception: "Java 25 (69) is not supported by the current version of Byte Buddy which officially supports Java 24 (68) - update Byte Buddy or set net.bytebuddy.experimental as a VM property".
  - Short-term workaround: run the Maven command with `-Dnet.bytebuddy.experimental=true` during test runs.
  - Recommended long-term fix: upgrade `net.bytebuddy` and `org.mockito` artifacts to published versions that explicitly support Java 25.

- Environment variables referenced by the build:
  - The `pom.xml` references `env.REPSY_ACCOUNT_USER` and `env.REPSY_ACCOUNT_PASSWORD`. Provide these in CI or set them locally if required for publishing tasks.

For a more detailed validation report and remediation recommendations, see `BUILD_VALIDATION_REPORT.md`.

## Continuous Integration

CI should run on a JDK 25 runner. Recommended CI steps:
- Checkout repo
- Install JDK 25
- Build and run unit tests
- Run integration tests in a separate job (use Testcontainers or a dedicated test environment for Eureka)
- Run dependency CVE scan (Dependabot, Snyk, Mend, or equivalent)

See `README-BUILD.md` for a sample GitHub Actions snippet and more CI guidance.

## Documentation

- CHANGELOG.md — project changelog and migration notes
- BUILD_VALIDATION_REPORT.md — results of the upgrade validation and recommended fixes
- README-BUILD.md — build/run instructions and CI guidance

## Contributing

Contributions are welcome. When opening pull requests:
- Update or add unit/integration tests for functional changes
- Ensure the build passes locally with JDK 25
- Update the changelog and documentation where appropriate

## License

See the `LICENSE` file in the repository root for license details.

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=prx-dev_commons-services)](https://sonarcloud.io/summary/new_code?id=prx-dev_commons-services)
