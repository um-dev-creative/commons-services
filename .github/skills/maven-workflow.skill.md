---
name: Maven Build Workflow
description: Shared skill -- Maven commands, lifecycle phases, and build configuration for commons-services.
applies-to:
  - Build Quality Guardian
  - Dependency Security Guardian
  - Release Publisher
---

# Maven Build Workflow -- Shared Skill

## Authoritative Commands

### Standard local build (compile + test + PMD + JaCoCo)
```bash
mvn -DskipITs clean verify
```

### Tests only (no JaCoCo enforcement)
```bash
mvn test
```

### Single test class
```bash
mvn test -Dtest=LoggingServiceImpTest
```

### Single test method
```bash
mvn test -Dtest=LoggingServiceImpTest#methodName
```

### Byte Buddy workaround (ONLY when JVM compatibility error occurs)
```bash
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

### Generate JaCoCo coverage report
```bash
mvn verify
# Report: target/site/jacoco/jacoco.xml and target/site/jacoco/index.html
```

### Publish to Repsy (requires AI_GRANT_PUBLISH_ARTIFACT=true)
```bash
mvn clean deploy
```

## Lifecycle Phase Map
| Phase | What runs |
|---|---|
| compile | Java compilation |
| test | JUnit tests + PMD analysis |
| package | JAR packaging |
| verify | JaCoCo threshold enforcement |
| deploy | Repsy publication |

## Project Baseline
| Property | Value |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.8 |
| Spring Cloud | 2025.0.1 |
| AWS SDK v2 | 2.21.0 |
| Packaging | jar (library, not executable) |

## Key Files
```
pom.xml       -- single source of truth for all versions and plugins
ruleset.xml   -- PMD rule configuration
target/site/jacoco/jacoco.xml  -- JaCoCo report (after mvn verify)
```
