# Copilot Agents — commons-services

Master index for all AI agent infrastructure in `commons-services`.

> **Policy authority:** `AGENTS.md` is the single source of truth for all agent policies.
> This file is the navigation hub pointing to all detailed artifacts.

---

## Project Identity

| Property     | Value                                           |
|--------------|-------------------------------------------------|
| Artifact     | `com.umdc:commons-services:0.0.1`               |
| Type         | Shared library JAR (not a runnable application) |
| Base Package | `com.umdc.commons.services`                     |
| Java         | 21                                              |
| Spring Boot  | 3.5.8                                           |
| Spring Cloud | 2025.0.1                                        |
| Published to | `repo.repsy.io/mvn/lmata/prx`                   |

---

## Agent Infrastructure Directories

| Directory                   | Purpose                                                                                  |
|-----------------------------|------------------------------------------------------------------------------------------|
| `.github/agents/`           | Agent definition files (`.agent.md`)                                                     |
| `.github/skills/`           | Agent-specific and shared skill files (`SKILL.md`, `.skill.md`)                          |
| `.github/tools/`            | Tool command references (`.tool.md`)                                                     |
| `.github/prompts/tasks/`    | Executable task prompts (`.prompt.md`)                                                   |
| `.github/prompts/agents/`   | Agent system prompts                                                                     |
| `.github/prompts/features/` | Multi-step workflow prompts                                                              |
| `.github/prompts/skills/`   | Skill codification prompts                                                               |
| `.github/prompts/shared/`   | Shared context (baseline facts, restrictions)                                            |
| `.github/hooks/`            | Automation hook definitions (`.hook.md`)                                                 |
| `.ai/`                      | Machine-readable YAML definitions (`agents.yml`, `skills.yml`, `roles.yml`, `confs.yml`) |

---

## Agent Roster

### Developer Agents

| Agent                    | Owned Area                                                                    | Definition                                                                            |
|--------------------------|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| Contracts Architect      | `CrudService`, `ImageApi`, `ImageService`, `LoggingService`, `cloudflare/to/` | [contracts-architect.agent.md](.github/agents/contracts-architect.agent.md)           |
| Logging Flow Specialist  | `loggers/` package                                                            | [logging-flow-specialist.agent.md](.github/agents/logging-flow-specialist.agent.md)   |
| Cloudflare R2 Specialist | `cloudflare/` package (all)                                                   | [cloudflare-r2-specialist.agent.md](.github/agents/cloudflare-r2-specialist.agent.md) |
| REST & Config Maintainer | `config/`, `properties/`, `rest/`, `util/`                                    | [rest-config-maintainer.agent.md](.github/agents/rest-config-maintainer.agent.md)     |

### Supporting Agents

| Agent                        | Responsibility                    | Definition                                                                                    |
|------------------------------|-----------------------------------|-----------------------------------------------------------------------------------------------|
| Build Quality Guardian       | PMD + JaCoCo gates                | [build-quality-guardian.agent.md](.github/agents/build-quality-guardian.agent.md)             |
| Code Review Auditor          | Diff review, contract safety      | [code-review-auditor.agent.md](.github/agents/code-review-auditor.agent.md)                   |
| Dependency Security Guardian | CVE audits, pom.xml upgrades      | [dependency-security-guardian.agent.md](.github/agents/dependency-security-guardian.agent.md) |
| Release Publisher            | `mvn deploy` to Repsy             | [release-publisher.agent.md](.github/agents/release-publisher.agent.md)                       |
| Docs Governance Maintainer   | AGENTS.md, CLAUDE.md, docs/, .ai/ | [docs-governance-maintainer.agent.md](.github/agents/docs-governance-maintainer.agent.md)     |

See full catalog: [.github/agents/agents.md](.github/agents/agents.md)

---

## Skills

### Agent-Specific Skills
Each agent has a dedicated `SKILL.md` under `.github/skills/<agent-name>/`.

### Shared Skills
| Skill                                                                   | Used By                                                                 |
|-------------------------------------------------------------------------|-------------------------------------------------------------------------|
| [api-compatibility.skill.md](.github/skills/api-compatibility.skill.md) | Contracts Architect, Code Review Auditor, Docs Governance Maintainer    |
| [maven-workflow.skill.md](.github/skills/maven-workflow.skill.md)       | Build Quality Guardian, Dependency Security Guardian, Release Publisher |

See full catalog: [.github/skills/skills.md](.github/skills/skills.md)

---

## Tools

| Tool       | Command                            | Used By                                     |
|------------|------------------------------------|---------------------------------------------|
| Maven      | `mvn -DskipITs clean verify`       | All agents                                  |
| PMD        | `mvn pmd:check`                    | Build Quality Guardian, Code Review Auditor |
| JaCoCo     | `mvn verify`                       | Build Quality Guardian                      |
| Git        | `git diff`, `git status`           | All agents                                  |
| Repsy      | `mvn clean deploy`                 | Release Publisher                           |
| SonarCloud | `mvn ... sonar-maven-plugin:sonar` | Build Quality Guardian                      |

See full catalog: [.github/tools/tools.md](.github/tools/tools.md)

---

## Hooks

| Hook                                                                           | Trigger                            | Blocking |
|--------------------------------------------------------------------------------|------------------------------------|----------|
| [pre-pull-request](.github/hooks/pre-pull-request.hook.md)                     | PR to main/develop                 | **YES**  |
| [post-implementation-review](.github/hooks/post-implementation-review.hook.md) | Push to feature/*                  | No       |
| [post-merge-security](.github/hooks/post-merge-security.hook.md)               | Merge to develop (pom.xml changed) | No       |
| [pre-release-gate](.github/hooks/pre-release-gate.hook.md)                     | Manual or v* tag                   | **YES**  |

See full catalog: [.github/hooks/hooks.md](.github/hooks/hooks.md)

---

## Build & Test Quick Reference

```bash
# Standard local build (compile + test + PMD + JaCoCo)
mvn -DskipITs clean verify

# Tests only (no JaCoCo enforcement)
mvn test

# Single test class
mvn test -Dtest=LoggingServiceImpTest

# Coverage report
mvn verify
# HTML: target/site/jacoco/index.html
# XML:  target/site/jacoco/jacoco.xml

# Byte Buddy workaround (only when JVM compatibility error occurs)
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

---

## Policy Files

| File                              | Purpose                                           |
|-----------------------------------|---------------------------------------------------|
| `AGENTS.md`                       | **Authoritative** shared policy for all agents    |
| `CLAUDE.md`                       | Claude Code specific guidance                     |
| `.github/copilot-instructions.md` | GitHub Copilot specific summary                   |
| `.ai/agents.yml`                  | Machine-readable agent definitions                |
| `.ai/skills.yml`                  | Machine-readable skill definitions                |
| `.ai/roles.yml`                   | Machine-readable role definitions                 |
| `.ai/confs.yml`                   | Central configuration (versions, commands, gates) |

