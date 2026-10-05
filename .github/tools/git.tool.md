---
name: Git
description: Version control tool for commons-services hosted on GitHub.
type: terminal
command-prefix: git
used-by:
  - Build Quality Guardian
  - Code Review Auditor
  - Dependency Security Guardian
  - Docs Governance Maintainer
  - Release Publisher
---

# Git Tool

## Purpose
Git manages version control and is used to inspect diffs, check staged changes,
and verify no secrets are committed before publishing.

## Available Commands

### Inspect changes
```bash
# Show unstaged diff
git diff

# Show staged diff
git diff --cached

# Show recent commits
git --no-pager log --oneline -10

# Check working tree status
git status
```

### Branch management
```bash
# List branches
git --no-pager branch -a

# Current branch
git rev-parse --abbrev-ref HEAD
```

### Security checks
```bash
# Check for staged secrets (basic pattern scan)
git diff --cached | grep -E "(password|secret|token|key)" --color=always

# Verify .env.local is not tracked
git ls-files .env.local
```

## Notes
- NEVER run git push --force on any branch
- NEVER commit REPSY_ACCOUNT_USER, REPSY_ACCOUNT_PASSWORD, or any .env.local content
- main and develop are protected branches -- PRs only
