---
name: Docs Governance Maintainer Skills
description: Consolidated skill set for keeping documentation accurate and consistent in commons-services.
applies-to:
  - Docs Governance Maintainer
---

# Docs Governance Maintainer -- Skill Definition

## 1. Critical Facts to Verify Before Editing Docs
Always re-read these files before making any claim in documentation:
- pom.xml -- actual versions, coordinates, plugins
- src/main/java/com/umdc/commons/services/ -- actual class names, packages, interfaces

## 2. Known Accurate Facts (as of current baseline)
- Java version: 21
- Spring Boot: 3.5.8
- Spring Cloud: 2025.0.1
- Group ID: com.prx, Artifact ID: commons-services, Version: 0.0.1
- Base package: com.umdc.commons.services (NOT com.prx.commons.services)
- Controller interface: ImageApi (NOT ProfileImageApi)
- Service interface: ImageService (NOT ProfileImageService)
- TOs: ImageUploadRequest, ImageUploadResponse, ImageReferenceResponse (records)
- Logging property: prx.logging.trace.enabled (env: LOGGING_TRACE_ENABLED)
- S3 client class: CloudflareR2StorageClient
- Properties prefix: cloudflare.r2 (class: CloudflareR2Properties)

## 3. Cross-File Consistency Rules
| Claim | Must match in |
|---|---|
| Java version | pom.xml <java.version>, AGENTS.md, CLAUDE.md, copilot-instructions.md |
| Spring Boot version | pom.xml parent, AGENTS.md, CLAUDE.md, confs.yml |
| Package paths | src/ directory structure, .ai/agents.yml, doc examples |
| Interface names | actual .java files, all doc references |
| Build commands | pom.xml lifecycle phases, all docs command sections |

## 4. Mermaid Diagram Rules
- No subgraph used as edge target
- No double tilde (~~) in labels
- Verify syntax renders on GitHub before committing

## 5. Key Files to Keep in Sync
```
AGENTS.md                              (authoritative policy)
CLAUDE.md                              (Claude-specific summary)
.github/copilot-instructions.md        (Copilot-specific summary)
.ai/agents.yml                         (machine-readable agent defs)
.ai/skills.yml                         (machine-readable skill defs)
.ai/roles.yml                          (machine-readable role defs)
.ai/confs.yml                          (machine-readable configuration)
.github/agents/*.agent.md              (Copilot agent definition files)
.github/skills/*/SKILL.md              (agent skill files)
.github/tools/*.tool.md                (tool command references)
.github/prompts/                       (task prompt files)
.github/hooks/*.hook.md                (automation hooks)
```

## 6. Constraints
- NEVER derive facts from README-BUILD.md or BUILD_VALIDATION_REPORT.md
- NEVER present Java 25 / Boot 4 as current baseline
- NEVER touch .java source files
- NEVER touch .github/workflows/ files

## 7. Checklist
- [ ] All version numbers match pom.xml
- [ ] All package paths match src/ directory structure
- [ ] All class names match actual .java files
- [ ] AGENTS.md, CLAUDE.md, copilot-instructions.md are consistent
- [ ] Mermaid diagrams are syntactically valid
- [ ] Build commands match current Maven lifecycle
