---
name: Post Merge Security Scan
description: Non-blocking dependency security scan after merges to develop when pom.xml changes.
trigger: push.develop
agents:
  - dependency-security-guardian
auto-block: false
---

# Post Merge Security Scan

## Trigger Conditions
- Event: push (after merge to develop or main)
- File filter: pom.xml changes ONLY
- Branch: develop, main

## Steps

### Step 1: Extract dependency list from pom.xml
Read all direct dependencies and their versions from pom.xml.

### Step 2: CVE scan
For each dependency, call validate_cves (ecosystem: maven).
Focus on CRITICAL and HIGH severity CVEs.

### Step 3: Report
If CVEs found:
- Create a GitHub issue (or post comment) with CVE table
- Tag: security, dependencies
- Assign to dependency-security-guardian

## Fail Behavior
- Does NOT block the merge (runs post-merge)
- Creates a tracking issue for any CRITICAL or HIGH CVEs
- LOW/MEDIUM CVEs logged but no issue created automatically

## Output
GitHub issue (if CVEs found):
```
## Security Scan: ${date}
### CVE Findings
| Library | CVE-ID | Severity | Current | Patched |

Action required: Update pom.xml version properties and run mvn -DskipITs clean verify.
```
