# Agent Catalog — commons-services

All agents defined for the `commons-services` shared library. Full definitions in each `.agent.md` file.

## Developer Agents (own source code areas)

| Agent File | Agent | User-Invocable | Owned Area |
|---|---|---|---|
| `contracts-architect.agent.md` | Contracts Architect | Yes | CrudService, ImageApi, ImageService, LoggingService, TOs |
| `logging-flow-specialist.agent.md` | Logging Flow Specialist | Yes | loggers/ package, application.yml |
| `cloudflare-r2-specialist.agent.md` | Cloudflare R2 Specialist | Yes | cloudflare/ package (all) |
| `rest-config-maintainer.agent.md` | REST and Config Maintainer | Yes | config/, properties/, rest/, util/ |

## Supporting Agents (cross-cutting)

| Agent File | Agent | User-Invocable | Responsibility |
|---|---|---|---|
| `build-quality-guardian.agent.md` | Build Quality Guardian | Yes | PMD + JaCoCo gates, mvn verify |
| `code-review-auditor.agent.md` | Code Review Auditor | Yes | Diff review, contract risk, coverage gaps |
| `dependency-security-guardian.agent.md` | Dependency Security Guardian | Yes | CVE audits, pom.xml version upgrades |
| `release-publisher.agent.md` | Release Publisher | No (subagent only) | mvn deploy to Repsy |
| `docs-governance-maintainer.agent.md` | Docs Governance Maintainer | Yes | AGENTS.md, CLAUDE.md, docs/, .ai/ |

## Agent Routing Guide
| Task | Use Agent |
|---|---|
| New interface method | contracts-architect |
| New Java record TO | contracts-architect |
| Bug in logging interceptor | logging-flow-specialist |
| R2 upload/download issue | cloudflare-r2-specialist |
| RestTemplate or Jackson config | rest-config-maintainer |
| PMD violation | build-quality-guardian |
| Coverage gap | build-quality-guardian |
| PR code review | code-review-auditor |
| CVE found in dependency | dependency-security-guardian |
| Publish to Repsy | release-publisher (via pre-release gate) |
| Update docs or .ai/ YAML | docs-governance-maintainer |

See `.github/copilot-agents.md` for the full master index.
