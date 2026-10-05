---
name: Build Quality Guardian Skills
description: Consolidated skill set for PMD and JaCoCo quality gate enforcement in commons-services.
applies-to:
  - Build Quality Guardian
---

# Build Quality Guardian -- Skill Definition

## 1. Project-Specific Patterns

### Standard verify command
```bash
mvn -DskipITs clean verify
```

### Tests only (no JaCoCo enforcement)
```bash
mvn test
```

### Single test class
```bash
mvn test -Dtest=LoggingServiceImpTest
```

### Byte Buddy workaround (only when JVM compatibility error occurs)
```bash
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

### JaCoCo coverage report location
```
target/site/jacoco/jacoco.xml
target/site/jacoco/index.html
```

## 2. Quality Gate Configuration

### PMD
- Phase: test
- Ruleset: ruleset.xml
- failOnViolation: true
- failurePriority: 5
- Exclusions: config/*, loggers/*, loggers/interceptor/*, mapper/*

### JaCoCo
- Phase: verify
- Line coverage minimum: 0.80 (BUNDLE level)
- Branch coverage minimum: 0.50 (PACKAGE level)
- Excluded packages: config/*, loggers/*, loggers/interceptor/*, mapper/*

## 3. Key Files
```
pom.xml  (plugin declarations, thresholds, exclusions)
ruleset.xml  (PMD rules)
target/site/jacoco/jacoco.xml  (coverage report after mvn verify)
```

## 4. Constraints
- NEVER lower JaCoCo thresholds below 80% line / 50% branch
- NEVER disable PMD or add new blanket exclusions
- NEVER suggest -DskipTests on the main verify run
- Apply byte-buddy flag ONLY when a JVM compatibility error is observed in output

## 5. Checklist
- [ ] mvn -DskipITs clean verify exits with code 0
- [ ] No PMD violations at priority <= 5 in non-excluded packages
- [ ] JaCoCo line coverage >= 80% BUNDLE
- [ ] JaCoCo branch coverage >= 50% PACKAGE
- [ ] JaCoCo XML report present at target/site/jacoco/jacoco.xml
