---
name: JaCoCo
description: Code coverage tool for commons-services -- enforced at Maven verify phase via pom.xml plugin configuration.
type: terminal
command-prefix: mvn verify
used-by:
  - Build Quality Guardian
  - Code Review Auditor
---

# JaCoCo Tool

## Purpose
JaCoCo measures Java code coverage and enforces minimum thresholds at the Maven verify phase.
The project requires >= 80% line coverage (BUNDLE) and >= 50% branch coverage (PACKAGE).

## Thresholds (enforced -- do not lower)
| Metric | Element | Minimum |
|---|---|---|
| LINE | BUNDLE | 80% (0.80) |
| BRANCH | PACKAGE | 50% (0.50) |

## Coverage Exclusions (no enforcement required)
- `**/config/*`
- `**/loggers/*`
- `**/loggers/interceptor/*`
- `**/mapper/*`

## Available Commands

### Enforce coverage thresholds
```bash
mvn -DskipITs clean verify
```

### Generate coverage report only (no enforcement)
```bash
mvn jacoco:report
# HTML: target/site/jacoco/index.html
# XML:  target/site/jacoco/jacoco.xml
```

## Output Locations
| Artifact | Path |
|---|---|
| Coverage report (XML) | target/site/jacoco/jacoco.xml |
| Coverage report (HTML) | target/site/jacoco/index.html |
| Execution data | target/jacoco.exec |

## Notes
- Never lower thresholds in pom.xml
- Never add new packages to exclusion list without explicit approval
- XML report is used by SonarCloud (project key: umdc-commons-services)
