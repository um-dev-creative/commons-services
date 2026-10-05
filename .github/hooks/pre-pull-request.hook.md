---
name: Pre Pull Request Gate
description: Blocking quality gate that runs before any PR merge to main or develop.
trigger: pull_request.opened
agents:
  - build-quality-guardian
  - code-review-auditor
auto-block: true
---

# Pre Pull Request Gate

## Trigger Conditions
- Event: pull_request (opened, synchronize, reopened)
- Branches: main, develop, master
- File filter: any src/ or pom.xml change

## Steps (run in order)

### Step 1: Build and quality gates (build-quality-guardian)
Runs automatically via .github/workflows/ci.yml:
```bash
mvn -B -V -e clean verify
```
- If PMD violations found -> BLOCK merge, list violations with file:line
- If JaCoCo below threshold -> BLOCK merge, show coverage delta

### Step 2: Code review (code-review-auditor)
Agent reviews the PR diff using review-code.prompt.md:
- Check interface contract safety
- Check R2 S3 settings (if cloudflare/ changed)
- Check logging pipeline integrity (if loggers/ changed)
- Check for credential leaks
- Check Lombok/field-injection introduction

### Step 3: SonarCloud analysis
Automatic via .github/workflows/build.yml:
```bash
mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=umdc-commons-services
```

## Fail Behavior
- Step 1 failure -> BLOCK merge, post PMD/JaCoCo summary as PR comment
- Step 2 CRITICAL/HIGH findings -> BLOCK merge, post review report
- Step 2 MEDIUM/LOW findings -> WARNING comment only, does not block
- Step 3 failure -> WARNING comment only (SonarCloud is informational)

## Output
PR comment with structured report:
```
## Pre-PR Gate Result

### Build: PASS / FAIL
### PMD: 0 violations / N violations
### JaCoCo: line X%, branch Y%
### Contract Review: PASS / N findings (severity breakdown)
```
