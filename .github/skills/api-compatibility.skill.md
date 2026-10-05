---
name: API Compatibility
description: Shared skill -- backward-compatible API evolution for the commons-services shared library JAR.
applies-to:
  - Contracts Architect
  - Code Review Auditor
  - Docs Governance Maintainer
---

# API Compatibility -- Shared Skill

## Purpose
Minimise downstream breakage for the PRX microservices that consume this shared library from Repsy.

## Rules
1. Never change the signature (parameter types, parameter names, return type) of any published public method.
2. Never remove a public method, field, or class from a published package.
3. When adding a method to an interface, always provide a default implementation.
4. When adding a field to a @ConfigurationProperties class, make it optional (default value, no required=true).
5. Never rename a public class or interface without a migration path.

## Key Interfaces to Protect
```
com.umdc.commons.services.CrudService
com.umdc.commons.services.cloudflare.controller.ImageApi
com.umdc.commons.services.cloudflare.service.ImageService
com.umdc.commons.services.loggers.LoggingService
```

## Allowed Changes
- Adding new default methods to existing interfaces
- Adding new optional fields to @ConfigurationProperties classes
- Adding new classes, interfaces, or records
- Deprecating methods (with @Deprecated annotation and Javadoc)

## Forbidden Changes
- Removing public methods, classes, or constructors
- Changing parameter types or return types
- Renaming parameters in published interfaces
- Converting default methods to abstract methods
