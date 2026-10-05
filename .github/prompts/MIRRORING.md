# Mirroring Map

This map shows how `.github/prompts` mirrors the canonical machine-readable policy in `.ai/`.

## Agents

- `.github/prompts/agents/*.prompt.md` mirrors `.ai/agents.yml` entries.
- Naming convention: `<agent-id>.prompt.md`.

## Skills

- `.github/prompts/skills/*.prompt.md` mirrors `.ai/skills.yml` skill IDs.
- Each skill prompt keeps intent and guardrails compact for chat reuse.

## Features

- `.github/prompts/features/*.prompt.md` composes shared prompts + agents + skills.
- Workflows map to supporting-agent responsibilities from `AGENTS.md`.

## Drift control

Whenever `AGENTS.md`, `.github/copilot-instructions.md`, or `.ai/*.yml` changes:

1. Update affected prompt files.
2. Verify references and paths in `.github/prompts/README.md`.
3. Keep Java/Spring/build values aligned with `pom.xml`.

