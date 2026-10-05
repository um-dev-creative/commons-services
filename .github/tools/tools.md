# Tools Catalog — commons-services

All tools available to agents in `commons-services`.

## Tool Files

| Tool File | Tool | Type | Used By |
|---|---|---|---|
| `maven.tool.md` | Maven | terminal (mvn) | All developer and build agents |
| `pmd.tool.md` | PMD | terminal (mvn pmd:check) | Build Quality Guardian, Code Review Auditor |
| `jacoco.tool.md` | JaCoCo | terminal (mvn verify) | Build Quality Guardian, Code Review Auditor |
| `git.tool.md` | Git | terminal (git) | Build guardian, Auditor, Security guardian, Docs maintainer, Release publisher |
| `repsy.tool.md` | Repsy Maven Repository | terminal (mvn deploy) | Release Publisher |
| `sonar.tool.md` | SonarCloud | terminal (mvn sonar) | Build Quality Guardian, Code Review Auditor |

## Key Commands Quick Reference

| Task | Command |
|---|---|
| Full build + quality gates | `mvn -DskipITs clean verify` |
| Tests only | `mvn test` |
| Single test class | `mvn test -Dtest=ClassName` |
| Byte Buddy workaround | `mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test` |
| PMD only | `mvn pmd:check` |
| Coverage report | `mvn verify` then see `target/site/jacoco/` |
| Publish artifact | `mvn clean deploy` (requires REPSY credentials + AI_GRANT_PUBLISH_ARTIFACT=true) |
| SonarCloud | `mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=umdc-commons-services` |
