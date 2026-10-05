---
name: Maven
description: Build tool for commons-services -- compile, test, PMD, JaCoCo, and deploy.
type: terminal
command-prefix: mvn
used-by:
  - Build Quality Guardian
  - Dependency Security Guardian
  - Release Publisher
  - Contracts Architect
  - Logging Flow Specialist
  - Cloudflare R2 Specialist
  - REST and Config Maintainer
---

# Maven Tool

## Purpose
Maven (via pom.xml) is the build, test, quality-gate, and publish tool for commons-services.
All build decisions are authoritative from pom.xml.

## Available Commands

### Full build with quality gates
```bash
# Compile + test + PMD + JaCoCo (standard local workflow)
mvn -DskipITs clean verify

# Tests only without JaCoCo enforcement
mvn test

# Single test class
mvn test -Dtest=ClassName

# Single test method
mvn test -Dtest=ClassName#methodName
```

### Diagnostics
```bash
# Byte Buddy workaround (only when JVM compatibility error)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test

# Verbose output
mvn -B -V -e clean verify
```

### Coverage report
```bash
mvn verify
# HTML: target/site/jacoco/index.html
# XML:  target/site/jacoco/jacoco.xml
```

### Publish (requires credentials and AI_GRANT_PUBLISH_ARTIFACT=true)
```bash
mvn clean deploy
```

## Output Locations
| Artifact | Path |
|---|---|
| Compiled classes | target/classes/ |
| Test classes | target/test-classes/ |
| JaCoCo report (XML) | target/site/jacoco/jacoco.xml |
| JaCoCo report (HTML) | target/site/jacoco/index.html |
| Surefire reports | target/surefire-reports/ |
| JaCoCo exec | target/jacoco.exec |

## Notes
- pom.xml is the single source of truth -- do not override plugin config inline
- PMD runs at test phase; JaCoCo enforcement at verify phase
- Required env vars for deploy: REPSY_ACCOUNT_USER, REPSY_ACCOUNT_PASSWORD
