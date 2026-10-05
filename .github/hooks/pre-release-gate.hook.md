---
name: Pre Release Gate
description: Blocking gate that must pass before any artifact publication to Repsy.
trigger: manual_or_tag
agents:
  - build-quality-guardian
  - code-review-auditor
  - dependency-security-guardian
  - release-publisher
auto-block: true
---

# Pre Release Gate

## Trigger Conditions
- Event: manual invocation OR git tag push (v*)
- Branch: main only
- Required: AI_GRANT_PUBLISH_ARTIFACT=true

## Steps (sequential -- each must pass before next)

### Step 1: Full quality gate (build-quality-guardian)
```bash
mvn -DskipITs clean verify
```
- PMD: zero violations -> PASS
- JaCoCo: line >= 80% BUNDLE, branch >= 50% PACKAGE -> PASS
- All tests: zero failures -> PASS

### Step 2: API contract review (code-review-auditor)
Review all changes since last release tag:
```bash
git --no-pager diff $(git describe --tags --abbrev=0 HEAD~1)..HEAD -- src/main/java/
```
- No CRITICAL contract issues -> PASS

### Step 3: Security scan (dependency-security-guardian)
CVE audit of current pom.xml.
- No CRITICAL or HIGH CVEs unmitigated -> PASS

### Step 4: Credential check
```bash
git diff --cached | grep -E "(password|secret|token|key)" | grep -v "get\|set\|//"
git ls-files .env.local secrets.yml
```
- No secrets staged -> PASS

### Step 5: Publish (release-publisher)
```bash
mvn clean deploy
```

## Fail Behavior
- Any step failure -> STOP, do not proceed to next step
- Post detailed failure report

## Output
```
## Pre-Release Gate Result: ${version}

Step 1 Build: PASS / FAIL
Step 2 Contracts: PASS / N CRITICAL issues
Step 3 Security: PASS / N CVEs
Step 4 Credentials: PASS / FAIL
Step 5 Publish: PASS / FAIL -- artifact URL: https://repo.repsy.io/mvn/lmata/prx
```
