# Hooks Catalog — commons-services

All automation hooks defined for `commons-services`.

## Hook Files

| Hook File | Name | Trigger | Blocking | Agents |
|---|---|---|---|---|
| `pre-pull-request.hook.md` | Pre Pull Request Gate | PR opened to main/develop | YES | build-quality-guardian, code-review-auditor |
| `post-implementation-review.hook.md` | Post Implementation Review | Push to feature/* branch | NO | code-review-auditor, build-quality-guardian |
| `post-merge-security.hook.md` | Post Merge Security Scan | Push to develop (pom.xml changed) | NO | dependency-security-guardian |
| `pre-release-gate.hook.md` | Pre Release Gate | Manual or tag push (v*) | YES | build-quality-guardian, code-review-auditor, dependency-security-guardian, release-publisher |

## Lifecycle Diagram

```
feature/* push
  --> post-implementation-review  (non-blocking: build + style + contract drift)
        |
        v
PR to main/develop
  --> pre-pull-request            (BLOCKING: build + PMD + JaCoCo + review)
        |
        v
Merge to develop
  --> post-merge-security         (non-blocking: CVE scan if pom.xml changed)
        |
        v
Release tag / manual
  --> pre-release-gate            (BLOCKING: build + contracts + CVE + credentials + deploy)
```

## CI Workflow Integration

| Hook | Backed by CI workflow |
|---|---|
| pre-pull-request (build step) | `.github/workflows/ci.yml` |
| pre-pull-request (SonarCloud step) | `.github/workflows/build.yml` |
| pre-release-gate (deploy step) | Manual -- requires AI_GRANT_PUBLISH_ARTIFACT=true |
