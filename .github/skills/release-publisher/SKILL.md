---
name: Release Publisher Skills
description: Consolidated skill set for publishing JAR artifacts to Repsy from commons-services.
applies-to:
  - Release Publisher
---

# Release Publisher -- Skill Definition

## 1. Repository Coordinates
- Group ID: com.prx
- Artifact ID: commons-services
- Repository ID: repsy
- Repository URL: https://repo.repsy.io/mvn/lmata/prx

## 2. Pre-Publish Gate Sequence
```bash
# Step 1: Verify build passes
mvn -DskipITs clean verify
# Expected: BUILD SUCCESS, exit code 0

# Step 2: Check credentials
echo "REPSY_ACCOUNT_USER is set: ${REPSY_ACCOUNT_USER:+YES}"
echo "REPSY_ACCOUNT_PASSWORD is set: ${REPSY_ACCOUNT_PASSWORD:+YES}"

# Step 3: Deploy (ONLY if verify passed and credentials present)
mvn clean deploy
```

## 3. Maven settings.xml credentials pattern
```xml
<!-- Server credentials MUST come from environment -- never hardcode -->
<server>
    <id>repsy</id>
    <username>${env.REPSY_ACCOUNT_USER}</username>
    <password>${env.REPSY_ACCOUNT_PASSWORD}</password>
</server>
```

## 4. Key Files
```
pom.xml  (distributionManagement section)
```

## 5. Constraints
- NEVER run mvn deploy if verify failed
- NEVER commit REPSY_ACCOUNT_USER or REPSY_ACCOUNT_PASSWORD
- NEVER force-push to any branch
- NEVER bump version without explicit instruction
- AI_GRANT_PUBLISH_ARTIFACT=true MUST be set

## 6. Checklist
- [ ] mvn -DskipITs clean verify exits with code 0
- [ ] REPSY_ACCOUNT_USER set in environment
- [ ] REPSY_ACCOUNT_PASSWORD set in environment
- [ ] No secrets in staged files (git status clean)
- [ ] AI_GRANT_PUBLISH_ARTIFACT=true confirmed
- [ ] Version bump was explicitly requested (or not needed)
