---
name: Post Implementation Review
description: Non-blocking review that fires on feature branch pushes to catch issues early.
trigger: push.feature_branch
agents:
  - code-review-auditor
  - build-quality-guardian
auto-block: false
---

# Post Implementation Review

## Trigger Conditions
- Event: push
- Branch filter: feature/*, fix/*, chore/* (NOT main, develop, master)
- File filter: src/main/java/ changes

## Steps (run in parallel where possible)

### Step 1: Targeted build validation
```bash
mvn -DskipITs clean test
```
Report PMD violations, test failures. Do not block -- informational.

### Step 2: Contract drift check
Scan changed files for:
```bash
git diff HEAD~1 -- src/main/java/ | grep -E "^\+.*(public|interface|default|abstract)"
```
Flag any signature changes to CrudService, ImageApi, ImageService, LoggingService.

### Step 3: Style check
Scan for common style regressions:
```bash
git diff HEAD~1 -- src/ | grep -E "^\+.*(@Autowired|System\.out|System\.err|lombok)"
```

## Fail Behavior
- All steps are informational -- no branch block
- Post summary as commit status check (informational / warning)

## Output
Commit status with brief summary:
```
Post-Implementation Check: PASS / N issues
Build: PASS / FAIL | PMD: 0 / N | Style: OK / N regressions
```
