# Prompts Catalog — commons-services

All prompt files organized by category.

## Task Prompts (tasks/)

| Prompt File | Agent | Mode | Trigger |
|---|---|---|---|
| `tasks/implement-feature.prompt.md` | contracts-architect | agent | New feature story |
| `tasks/fix-bug.prompt.md` | contracts-architect | agent | Bug report |
| `tasks/fix-pmd-violations.prompt.md` | build-quality-guardian | agent | PMD failure in build |
| `tasks/write-unit-tests.prompt.md` | build-quality-guardian | agent | New code / missing tests |
| `tasks/improve-coverage.prompt.md` | build-quality-guardian | agent | JaCoCo below threshold |
| `tasks/review-code.prompt.md` | code-review-auditor | agent | PR / code change |
| `tasks/security-audit.prompt.md` | dependency-security-guardian | agent | Pre-release / dep change |
| `tasks/prepare-release.prompt.md` | release-publisher | agent | Release tag |
| `tasks/full-feature-delivery.prompt.md` | contracts-architect | agent | Complex multi-agent feature |
| `tasks/review-api-contract.prompt.md` | code-review-auditor | agent | API/interface change |

## Agent Prompts (agents/)

| Prompt File | Agent | Purpose |
|---|---|---|
| `agents/contracts-architect.prompt.md` | Contracts Architect | Agent system prompt |
| `agents/logging-flow-specialist.prompt.md` | Logging Flow Specialist | Agent system prompt |
| `agents/cloudflare-r2-specialist.prompt.md` | Cloudflare R2 Specialist | Agent system prompt |
| `agents/rest-config-maintainer.prompt.md` | REST and Config Maintainer | Agent system prompt |
| `agents/build-quality-guardian.prompt.md` | Build Quality Guardian | Agent system prompt |
| `agents/code-review-auditor.prompt.md` | Code Review Auditor | Agent system prompt |
| `agents/dependency-security-guardian.prompt.md` | Dependency Security Guardian | Agent system prompt |
| `agents/release-publisher.prompt.md` | Release Publisher | Agent system prompt |
| `agents/docs-governance-maintainer.prompt.md` | Docs Governance Maintainer | Agent system prompt |

## Feature/Workflow Prompts (features/)

| Prompt File | Purpose |
|---|---|
| `features/agent-router.prompt.md` | Routes incoming requests to the correct agent |
| `features/quality-gate-workflow.prompt.md` | Full PMD + JaCoCo quality gate workflow |
| `features/dependency-security-workflow.prompt.md` | Full CVE audit and remediation workflow |
| `features/release-readiness-workflow.prompt.md` | Pre-release gate sequence |
| `features/review-workflow.prompt.md` | Full PR review workflow |
| `features/docs-sync-workflow.prompt.md` | Documentation consistency sync workflow |

## Shared Context Prompts (shared/)

| Prompt File | Purpose |
|---|---|
| `shared/repo-baseline.prompt.md` | Project baseline facts (versions, packages, structure) |
| `shared/restrictions.prompt.md` | All restrictions and invariants shared across agents |

## Skill Prompts (skills/)

| Prompt File | Skill |
|---|---|
| `skills/api-compatibility.prompt.md` | API backward-compatibility rules |
| `skills/cloudflare-r2-client.prompt.md` | R2 S3 client configuration patterns |
| `skills/default-method-contracts.prompt.md` | Interface default-method patterns |
| `skills/interceptor-logging-flow.prompt.md` | Logging interceptor chain patterns |
| `skills/property-binding.prompt.md` | @ConfigurationProperties binding patterns |
| `skills/quality-gates-enforcement.prompt.md` | PMD and JaCoCo gate patterns |
