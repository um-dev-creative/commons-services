---
name: PMD
description: Static analysis tool for commons-services -- runs at Maven test phase via pom.xml plugin configuration.
type: terminal
command-prefix: mvn pmd:check
used-by:
  - Build Quality Guardian
  - Code Review Auditor
---

# PMD Tool

## Purpose
PMD performs static code analysis on Java sources to catch rule violations defined in ruleset.xml.
It runs automatically during the Maven test phase and will fail the build on violations at priority <= 5.

## Configuration
- Ruleset file: ruleset.xml (project root)
- Phase: test
- failOnViolation: true
- failurePriority: 5
- Excluded packages: config/*, loggers/*, loggers/interceptor/*, mapper/*

## Available Commands

### Run PMD check standalone
```bash
mvn pmd:check
```

### Run PMD with full test phase (standard)
```bash
mvn -DskipITs clean test
```

### Generate PMD report only
```bash
mvn pmd:pmd
# Report: target/pmd.xml
```

## Output Locations
| Artifact | Path |
|---|---|
| PMD violations report | target/pmd.xml |
| CPD (copy-paste) report | target/cpd.xml |

## Notes
- Never disable PMD via -Dpmd.skip=true on the main build
- ruleset.xml is the authoritative rule configuration -- do not inline rule overrides
- Priority 1-5: failure; Priority 6+ (if any): informational only
