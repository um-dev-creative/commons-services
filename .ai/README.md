# Copilot Agent Pack

This folder defines the project-specific AI agents used by GitHub Copilot and Claude Code.

## Files

- `.ai/agents.yml`: agent roster, ownership boundaries, grants, restrictions
- `.ai/skills.yml`: reusable skills with guardrails and code patterns
- `.ai/roles.yml`: role mandates, required skills, and definition of done
- `.ai/confs.yml`: shared runtime config (baseline, build commands, quality gates)

## Source-of-truth order

1. `AGENTS.md` (authoritative policy)
2. `pom.xml` (build/dependency truth)
3. `.github/copilot-instructions.md` and `CLAUDE.md` (platform summaries)
4. `.ai/*.yml` (machine-readable agent config)

## Agent roster

Developer agents:

- `contracts-architect`
- `logging-flow-specialist`
- `cloudflare-r2-specialist`
- `rest-config-maintainer`

Supporting agents:

- `build-quality-guardian`
- `code-review-auditor`
- `dependency-security-guardian`
- `release-publisher`
- `docs-governance-maintainer`

## Sync rules

- Keep Java and Spring baselines aligned with `pom.xml` (Java 21, Spring Boot 3.5.8).
- Keep contract defaults aligned with code:
  - `CrudService` -> HTTP 501 default
  - `ProfileImageService` -> throws `UnsupportedOperationException("Not implemented")`
  - `ProfileImageApi` -> `HttpStatusUtil.NOT_IMPLEMENTED`
- Preserve logging and Cloudflare R2 invariants from `AGENTS.md`.
- Do not change `.github/workflows/` through agent config updates.

