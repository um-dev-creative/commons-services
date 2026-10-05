# Copilot Prompt Catalog

This folder contains GitHub Copilot prompt files in Markdown format.

## Structure

- `.github/prompts/agents/`: one prompt per project agent
- `.github/prompts/skills/`: reusable skill prompts with guardrails and patterns
- `.github/prompts/features/`: workflow prompts that compose agents + skills
- `.github/prompts/shared/`: baseline and policy snippets used by all prompts

## Source of truth

1. `AGENTS.md`
2. `pom.xml`
3. `.github/copilot-instructions.md`
4. `.ai/agents.yml`, `.ai/skills.yml`, `.ai/roles.yml`, `.ai/confs.yml`

If any prompt drifts from those files, update this prompt catalog to match.

