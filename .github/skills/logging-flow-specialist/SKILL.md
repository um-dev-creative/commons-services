---
name: Logging Flow Specialist Skills
description: Consolidated skill set for the three-layer HTTP logging pipeline in commons-services.
applies-to:
  - Logging Flow Specialist
---

# Logging Flow Specialist -- Skill Definition

## 1. Project-Specific Patterns

### LogInterceptor.preHandle
```java
@Override
public boolean preHandle(@NonNull HttpServletRequest request,
                         @NonNull HttpServletResponse response,
                         @NonNull Object handler) throws Exception {
    if (request.getMethod().equals(HttpMethod.GET.name())
            || request.getMethod().equals(HttpMethod.POST.name())
            || request.getMethod().equals(HttpMethod.DELETE.name())
            || request.getMethod().equals(HttpMethod.PUT.name())) {
        loggingService.displayRequest(request, null);
    }
    return true;  // MUST always return true
}
```

### RequestBodyInterceptor
```java
@Override
public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
        MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
    loggingService.displayRequest(
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(),
        body);
    return body;
}
```

### LoggingServiceImp header collection
```java
// MUST use ConcurrentHashMap
Map<String, String> headersMap = new ConcurrentHashMap<>();
Enumeration<String> headerNames = request.getHeaderNames();
while (headerNames != null && headerNames.hasMoreElements()) {
    String key = headerNames.nextElement();
    headersMap.put(key, request.getHeader(key));
}
```

## 2. Naming Conventions
- Classes: `LogInterceptor`, `RequestBodyInterceptor`, `ResponseBodyInterceptor`
- Config: `LoggerWebConfigurer` (implements WebMvcConfigurer)
- Service impl: `LoggingServiceImp` (note: Imp not Impl)
- Property: `prx.logging.trace.enabled` (env: LOGGING_TRACE_ENABLED)

## 3. Interceptor Chain
```
LoggerWebConfigurer
  -> registers LogInterceptor (HandlerInterceptor.preHandle)
  -> RequestBodyInterceptor (RequestBodyAdviceAdapter.afterBodyRead)
  -> ResponseBodyInterceptor (ResponseBodyAdvice.beforeBodyWrite)
  -> ALL delegate to LoggingService.displayRequest / displayResponse
```

## 4. Key Files
```
src/main/java/com/umdc/commons/services/loggers/LoggingService.java
src/main/java/com/umdc/commons/services/loggers/LoggingServiceImp.java
src/main/java/com/umdc/commons/services/loggers/config/LoggerWebConfigurer.java
src/main/java/com/umdc/commons/services/loggers/interceptor/LogInterceptor.java
src/main/java/com/umdc/commons/services/loggers/interceptor/RequestBodyInterceptor.java
src/main/java/com/umdc/commons/services/loggers/interceptor/ResponseBodyInterceptor.java
```

## 5. Constraints
- preHandle MUST fire ONLY for GET, POST, PUT, DELETE -- skip all other methods
- preHandle MUST always return true
- LoggingServiceImp MUST use ConcurrentHashMap (not HashMap) for thread safety
- Tracing controlled ONLY by prx.logging.trace.enabled -- no per-endpoint flags
- SLF4J Logger obtained as LoggerFactory.getLogger(ClassName.class) -- no System.out
- Constructor injection only; no @Autowired fields

## 6. Checklist
- [ ] preHandle filters only GET, POST, PUT, DELETE
- [ ] preHandle always returns true
- [ ] ConcurrentHashMap used in LoggingServiceImp
- [ ] All three interceptors delegate to LoggingService -- no direct logging
- [ ] Tracing flag wired from prx.logging.trace.enabled via @Value
- [ ] Unit tests cover enabled and disabled tracing paths
- [ ] mvn -DskipITs clean verify passes
