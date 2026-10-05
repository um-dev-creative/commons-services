---
name: Dependency Security Guardian Skills
description: Consolidated skill set for CVE auditing and dependency management in commons-services.
applies-to:
  - Dependency Security Guardian
---

# Dependency Security Guardian -- Skill Definition

## 1. Project Dependency Baseline (from pom.xml)
- Java: 21
- Spring Boot: 3.5.8 (parent BOM)
- Spring Cloud: 2025.0.1 (dependencyManagement import)
- AWS SDK v2: 2.21.0
- MapStruct: 1.6.3
- JUnit: 5.14.1
- Mockito: 5.21.0
- Log4j: 2.25.3

## 2. Key pom.xml Coordinates
```xml
<groupId>com.prx</groupId>
<artifactId>commons-services</artifactId>
<version>0.0.1</version>

<!-- Repsy private repo -->
<distributionManagement>
    <repository>
        <id>repsy</id>
        <url>https://repo.repsy.io/mvn/lmata/prx</url>
    </repository>
</distributionManagement>

<!-- PRX internal dependency -->
<dependency>
    <groupId>com.prx</groupId>
    <artifactId>prx-commons</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 3. Audit Workflow
1. Read pom.xml to extract all dependency versions
2. Run validate_cves for each dependency (ecosystem: maven)
3. Identify minimum patched version for each CVE
4. Propose pom.xml property changes
5. Verify: mvn -DskipITs clean verify after changes

## 4. Key Files
```
pom.xml  (ONLY file that can be modified)
```

## 5. Constraints
- NEVER propose Java 25 or Spring Boot 4.x upgrades (migration notes only)
- NEVER change source files
- NEVER weaken SSL/TLS configuration
- ALWAYS verify build passes after version bumps
- NEVER introduce hardcoded credentials in pom.xml

## 6. Checklist
- [ ] All direct dependencies checked for CVEs
- [ ] Minimum patched versions identified
- [ ] pom.xml changes minimal (only affected versions)
- [ ] mvn -DskipITs clean verify passes after changes
- [ ] No credentials introduced
