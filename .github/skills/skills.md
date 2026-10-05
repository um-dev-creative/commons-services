# Skills Catalog — commons-services

All skills defined for `commons-services` agents.

## Agent-Specific Skills

| Skill Folder | Skill | Used By |
|---|---|---|
| `contracts-architect/SKILL.md` | Contracts Architect Skills | Contracts Architect |
| `logging-flow-specialist/SKILL.md` | Logging Flow Specialist Skills | Logging Flow Specialist |
| `cloudflare-r2-specialist/SKILL.md` | Cloudflare R2 Specialist Skills | Cloudflare R2 Specialist |
| `rest-config-maintainer/SKILL.md` | REST and Config Maintainer Skills | REST and Config Maintainer |
| `build-quality-guardian/SKILL.md` | Build Quality Guardian Skills | Build Quality Guardian |
| `code-review-auditor/SKILL.md` | Code Review Auditor Skills | Code Review Auditor |
| `dependency-security-guardian/SKILL.md` | Dependency Security Guardian Skills | Dependency Security Guardian |
| `release-publisher/SKILL.md` | Release Publisher Skills | Release Publisher |
| `docs-governance-maintainer/SKILL.md` | Docs Governance Maintainer Skills | Docs Governance Maintainer |

## Shared Skills

| Skill File | Skill | Used By |
|---|---|---|
| `api-compatibility.skill.md` | API Compatibility | Contracts Architect, Code Review Auditor, Docs Governance Maintainer |
| `maven-workflow.skill.md` | Maven Build Workflow | Build Quality Guardian, Dependency Security Guardian, Release Publisher |

## Skill-to-Agent Map (from .ai/skills.yml)

| Skill ID | Agent(s) |
|---|---|
| default-method-contracts | contracts-architect |
| api-compatibility | contracts-architect, code-review-auditor, docs-governance-maintainer |
| interceptor-logging-flow | logging-flow-specialist |
| cloudflare-r2-client | cloudflare-r2-specialist |
| openapi-annotation | contracts-architect |
| mapstruct-consistency | rest-config-maintainer |
| property-binding | cloudflare-r2-specialist, rest-config-maintainer, logging-flow-specialist |
| rest-template-config | rest-config-maintainer |
| jackson-configuration | rest-config-maintainer |
| maven-build-workflow | build-quality-guardian, dependency-security-guardian, release-publisher |
| quality-gates-enforcement | build-quality-guardian, code-review-auditor |
| lightweight-unit-testing | all developer agents |
| profile-image-contracts | cloudflare-r2-specialist, contracts-architect |
| dependency-source-of-truth | dependency-security-guardian, build-quality-guardian, docs-governance-maintainer |
| security-hygiene | dependency-security-guardian, code-review-auditor |
| release-publishing | release-publisher |
| docs-maintenance | docs-governance-maintainer |
