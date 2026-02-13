# PRX Commons Services components

## Sonar Cloud badges
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=um-dev-creative_commons-services)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)

[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=coverage)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=um-dev-creative_commons-services&metric=bugs)](https://sonarcloud.io/summary/new_code?id=um-dev-creative_commons-services)

## Technologies
![Java](https://img.shields.io/badge/Java-21-green?logo=java&style=flat-square) ![Maven](https://img.shields.io/badge/Maven-3.8.0-lightgrey?logo=apachemaven&style=flat-square) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-green?logo=springboot&style=flat-square) ![Spring Core](https://img.shields.io/badge/Spring%20Core-6.2.1-green?logo=spring&style=flat-square) ![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-green?logo=spring&style=flat-square) ![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5.Final-green?style=flat-square) ![Log4j](https://img.shields.io/badge/Log4j-2.24.3-green?style=flat-square) ![JUnit](https://img.shields.io/badge/JUnit-5.11.3-green?logo=junit&style=flat-square) ![Mockito](https://img.shields.io/badge/Mockito-5.14.2-green?style=flat-square) ![Tomcat](https://img.shields.io/badge/Tomcat-11.0.15-green?logo=apachetomcat&style=flat-square) ![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.15-green?style=flat-square) ![PMD](https://img.shields.io/badge/PMD-3.23.0-green?style=flat-square) ![SpringDoc](https://img.shields.io/badge/SpringDoc%20OpenAPI-1.8.0-green?style=flat-square) ![Gson](https://img.shields.io/badge/Gson-2.13.2-lightgrey?style=flat-square) ![Jackson%20JSR310](https://img.shields.io/badge/Jackson%20JSR310-2.19.4-lightgrey?logo=jackson&style=flat-square)

One-line summary: Shared service components and utilities used by Java microservices (logging, interceptors, config helpers, and common integrations).

## Quick start

Minimum quick steps to build and run tests locally (assumes a JDK is installed and `mvn` is available on PATH):

```powershell
# Verify Java and Maven
java -version
mvn -v

# Run unit tests and produce reports
mvn -U -DskipITs clean verify

# Run tests with Byte Buddy experimental flag if you encounter Java compatibility errors
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Run integration tests (requires configured test environment/profile)
mvn -P integration-tests verify
```

## Tech stack and versions

Detected technologies (alphabetically). Each entry lists the technology, the detected version (or a conservative fallback), and the source file where the version was found.

| Technology | Version     | Source |
|---|-------------|---|
| Apache Tomcat | 11.0.15     | pom.xml (dependency: org.apache.tomcat.embed:tomcat-embed-core) |
| Gson | 2.13.2      | pom.xml (dependency: com.google.code.gson:gson) |
| Jackson Datatype JSR310 | 2.19.4      | pom.xml (dependency: com.fasterxml.jackson.datatype:jackson-datatype-jsr310) |
| JaCoCo Maven Plugin | 0.8.14      | pom.xml (property: maven.plugin.jacoco.version) |
| Java | 21          | pom.xml (property: java.version) |
| JUnit Jupiter BOM | 5.11.3      | pom.xml (dependencyManagement: junit-bom) |
| JUnit Jupiter Core | 5.9.3       | pom.xml (property: junit.jupiter.core.version) |
| Log4j | 2.24.3      | pom.xml (property: logging.log4j.version) |
| MapStruct | 1.5.5.Final | pom.xml (property: mapstruct.version) |
| Maven | 3.8.0       | pom.xml (presence of Maven project descriptor) |
| Maven Compiler Plugin | 3.13.0      | pom.xml (property: maven.compiler.plugin.version) |
| Maven Javadoc Plugin | 3.6.3       | pom.xml (property: maven.plugin.javadoc.version) |
| Maven JXR Plugin | 3.0.0       | pom.xml (property: maven.plugin.jxr.version) |
| Maven PMD Plugin | 3.23.0      | pom.xml (property: maven.plugin.pmd.version) |
| Maven Resource Plugin | 3.3.1       | pom.xml (property: maven.resource.plugin.version) |
| Maven Surefire Plugin | 3.2.5       | pom.xml (property: maven.surefire.version) |
| MockServer JUnit | 5.15.0      | pom.xml (property: mockserver.junit.jupiter.version) |
| Mockito | 5.14.2      | pom.xml (property: mockito.version) |
| Spring Boot | 3.5.8       | pom.xml  |
| Spring Cloud | 2025.0.1    | pom.xml (property: spring-cloud.version) |
| Spring Core | 6.2.1       | pom.xml (property: spring-core.version) |
| SpringDoc OpenAPI UI | 1.8.0       | pom.xml (dependency: org.springdoc:springdoc-openapi-ui) |


## Overview

This repository provides shared service-related components used across multiple microservices. It contains logging utilities, web logging configuration, interceptors, and other common building blocks.

## Requirements

- Java 21 (JDK 21) is required to build and run the project.
- Maven 3.8+ (3.9+ recommended).
- Network access to Maven Central or your organization's proxy/repository.

Minimum supported framework versions:
- Spring Boot 3.5.8

See `CHANGELOG.md` and `BUILD_VALIDATION_REPORT.md` for migration details and records of the Spring Boot / Java upgrade work.

## Quick build (PowerShell)

Run the following commands from the repository root:

```powershell
# Verify Java and Maven
java -version
mvn -v

# Clean and run unit tests (skip integration tests)
mvn -U -DskipITs clean verify

# Run tests with a temporary Byte Buddy workaround (only if you encounter Java 21 compatibility errors)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Run integration tests (requires configured test environment/profile)
mvn -P integration-tests verify
```

## Known issues and workarounds

- Byte Buddy / Mockito compatibility with Java 21:
  - Symptom: Tests may fail with errors mentioning an unsupported Java class file version (for example: "Java 21 (61) not supported") or with Byte Buddy/Mockito instrumentation errors during unit or integration test runs.
  - Short-term workaround: run Maven tests with the experimental Byte Buddy flag to allow newer class file versions while you update dependencies:

```powershell
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

You can also provide the JVM argument to Surefire/Failsafe or via MAVEN_OPTS if you prefer (for example: `-Dnet.bytebuddy.experimental=true`).
  - Recommended long-term fix: upgrade `net.bytebuddy` and `org.mockito` artifacts to versions that explicitly state support for Java 21. Check the projects' release notes/CHANGELOG and prefer published releases that list Java 21 compatibility.

- Spring Boot 3.5.8 compatibility notes:
  - Symptom: After upgrading to Spring Boot 3.5.8 you might encounter dependency conflicts, Jakarta/`javax` namespace issues, or autoconfiguration/test differences caused by dependency version mismatches.
  - Short-term workarounds:
    - Use the Spring Boot 3.5.8 BOM (`spring-boot-dependencies`) or the official parent POM to align transitive dependency versions.
    - Update code that references `javax.*` packages to `jakarta.*` where required by updated Spring libraries.
    - Refresh your local dependency cache and rebuild: `mvn -U clean verify`.
  - Recommended long-term fix: follow the migration guidance captured in `BUILD_VALIDATION_REPORT.md` (and `CHANGELOG.md`) and upgrade or replace any third-party libraries that are incompatible with Spring Boot 3.5.x.

- Environment variables referenced by the build:
  - The `pom.xml` references `env.REPSY_ACCOUNT_USER` and `env.REPSY_ACCOUNT_PASSWORD`. Provide these in CI or set them locally if required for publishing tasks.

For a more detailed validation report and remediation recommendations, see `BUILD_VALIDATION_REPORT.md`.

## Continuous Integration

CI should run on a JDK 21 runner. Recommended CI steps:
- Checkout repo
- Install JDK 21
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
- Ensure the build passes locally with JDK 21
- Update the changelog and documentation where appropriate

## License

See the `LICENSE` file in the repository root for license details.

