---
name: Repsy Maven Repository
description: Private Maven repository for commons-services artifact publication at repo.repsy.io/mvn/lmata/prx.
type: terminal
command-prefix: mvn clean deploy
used-by:
  - Release Publisher
---

# Repsy Tool

## Purpose
Repsy hosts the private Maven repository where commons-services JAR artifacts are published
and consumed by downstream PRX microservices.

## Repository Coordinates
| Property | Value |
|---|---|
| Repository ID | repsy |
| Repository URL | https://repo.repsy.io/mvn/lmata/prx |
| Group ID | com.prx |
| Artifact ID | commons-services |
| Current Version | 0.0.1 |

## Deploy Command
```bash
# Prerequisites: REPSY_ACCOUNT_USER and REPSY_ACCOUNT_PASSWORD must be set in environment
# AI_GRANT_PUBLISH_ARTIFACT=true must be confirmed
mvn clean deploy
```

## Authentication
Credentials MUST come from environment variables -- never from committed files:
```bash
export REPSY_ACCOUNT_USER=<your-repsy-username>
export REPSY_ACCOUNT_PASSWORD=<your-repsy-password>
```

## Pre-Deploy Checklist
1. `mvn -DskipITs clean verify` exits with code 0
2. REPSY_ACCOUNT_USER is set
3. REPSY_ACCOUNT_PASSWORD is set
4. git status shows no secrets staged

## Notes
- Artifact verification URL: https://repo.repsy.io/mvn/lmata/prx/com/prx/commons-services/
- Never hardcode credentials in pom.xml or settings.xml committed to git
