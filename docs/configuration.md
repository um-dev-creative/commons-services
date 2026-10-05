# Configuration Reference

All configuration properties used by `commons-services` components. Consuming services must declare these properties in their `application.yml` / `application.properties` as needed.

---

## Logging Trace — `prx.logging`

Controls whether HTTP request/response trace logging is active.

| Property | Type | Required | Description |
|---|---|---|---|
| `prx.logging.trace.enabled` | boolean | Yes (when `LoggingServiceImp` is on classpath) | Enables trace-level HTTP logging |

**Recommended:** bind to an environment variable so it can be toggled per environment.

```yaml
prx:
  logging:
    trace:
      enabled: ${LOGGING_TRACE_ENABLED:false}
```

| Environment | Recommended Value |
|---|---|
| local / dev | `true` |
| staging | `true` |
| production | `false` |

---

## Cloudflare R2 Storage — `cloudflare.r2`

Required for `CloudflareR2StorageClient` and `ProfileImageApi`/`ProfileImageService` to function.

| Property | Type | Required | Description |
|---|---|---|---|
| `cloudflare.r2.accountId` | String | Yes | Cloudflare account ID |
| `cloudflare.r2.endpoint` | String | Yes | R2 S3-compatible endpoint URL (e.g., `https://<accountId>.r2.cloudflarestorage.com`) |
| `cloudflare.r2.accessKey` | String | Yes | R2 access key ID |
| `cloudflare.r2.secretKey` | String | Yes | R2 secret access key |
| `cloudflare.r2.bucketName` | String | Yes | Target R2 bucket name |
| `cloudflare.r2.publicUrl` | String | No | Public URL prefix for stored objects (e.g., `https://pub-xxx.r2.dev`). When set, `getPublicUrl(key)` returns `publicUrl/key`. |

```yaml
cloudflare:
  r2:
    accountId: ${CLOUDFLARE_ACCOUNT_ID}
    endpoint: ${CLOUDFLARE_R2_ENDPOINT}
    accessKey: ${CLOUDFLARE_R2_ACCESS_KEY}
    secretKey: ${CLOUDFLARE_R2_SECRET_KEY}
    bucketName: ${CLOUDFLARE_R2_BUCKET_NAME}
    publicUrl: ${CLOUDFLARE_R2_PUBLIC_URL:}
```

> **Security:** Never commit credentials. Always use environment variables or a secrets manager.

---

## Security — `prx.security`

Managed by `SecurityProperties`. Used for mutual TLS and management endpoint authentication.

### Keystore

| Property | Type | Description |
|---|---|---|
| `prx.security.keystore.location` | String | Classpath or file path to the keystore |
| `prx.security.keystore.password` | String | Keystore password |
| `prx.security.keystore.type` | String | Keystore type (e.g., `JKS`, `PKCS12`) |

### Truststore

| Property | Type | Description |
|---|---|---|
| `prx.security.truststore.location` | String | Classpath or file path to the truststore |
| `prx.security.truststore.password` | String | Truststore password |
| `prx.security.truststore.type` | String | Truststore type |

### Management Authenticator

| Property | Type | Description |
|---|---|---|
| `prx.security.managementAuthenticator.keyAlias` | String | Key alias within the keystore |
| `prx.security.managementAuthenticator.keystore.*` | StoreProperties | Nested keystore config (location, password, type) |
| `prx.security.managementAuthenticator.truststore.*` | StoreProperties | Nested truststore config (location, password, type) |

```yaml
prx:
  security:
    keystore:
      location: classpath:keystore.p12
      password: ${KEYSTORE_PASSWORD}
      type: PKCS12
    truststore:
      location: classpath:truststore.p12
      password: ${TRUSTSTORE_PASSWORD}
      type: PKCS12
    managementAuthenticator:
      keyAlias: management-key
      keystore:
        location: classpath:mgmt-keystore.p12
        password: ${MGMT_KEYSTORE_PASSWORD}
        type: PKCS12
      truststore:
        location: classpath:mgmt-truststore.p12
        password: ${MGMT_TRUSTSTORE_PASSWORD}
        type: PKCS12
```

---

## Discovery Client — `security.discovery.client`

Managed by `DiscoveryClientProperties`. Configures the Eureka discovery client SSL settings.

| Property | Type | Description |
|---|---|---|
| `security.discovery.client.name` | String | Client name used for discovery registration |
| `security.discovery.client.trustStoreFile` | String | Path to the truststore file |
| `security.discovery.client.trustStorePassword` | String | Truststore password |
| `security.discovery.client.maxTotalConnections` | Integer | Maximum total HTTP connections in the connection pool |
| `security.discovery.client.maxConnectionsPerHost` | Integer | Maximum connections per host |

```yaml
security:
  discovery:
    client:
      name: ${SERVICE_NAME}
      trustStoreFile: ${TRUST_STORE_FILE}
      trustStorePassword: ${TRUST_STORE_PASSWORD}
      maxTotalConnections: 200
      maxConnectionsPerHost: 50
```

---

## Debug / Print Utility — `log.debug`

Used by `PrinterUtil`.

| Property | Type | Default | Description |
|---|---|---|---|
| `log.debug` | boolean | — | Enables conditional object logging via `PrinterUtil.print(Object, Logger)` |

```yaml
log:
  debug: ${LOG_DEBUG:false}
```

---

## Environment Variable Reference

| Environment Variable | Maps To | Description |
|---|---|---|
| `LOGGING_TRACE_ENABLED` | `prx.logging.trace.enabled` | Enable HTTP trace logging |
| `CLOUDFLARE_ACCOUNT_ID` | `cloudflare.r2.accountId` | Cloudflare account ID |
| `CLOUDFLARE_R2_ENDPOINT` | `cloudflare.r2.endpoint` | R2 endpoint URL |
| `CLOUDFLARE_R2_ACCESS_KEY` | `cloudflare.r2.accessKey` | R2 access key |
| `CLOUDFLARE_R2_SECRET_KEY` | `cloudflare.r2.secretKey` | R2 secret key |
| `CLOUDFLARE_R2_BUCKET_NAME` | `cloudflare.r2.bucketName` | R2 bucket name |
| `CLOUDFLARE_R2_PUBLIC_URL` | `cloudflare.r2.publicUrl` | R2 public URL prefix |
| `REPSY_ACCOUNT_USER` | Maven publish credentials | Repsy repository username |
| `REPSY_ACCOUNT_PASSWORD` | Maven publish credentials | Repsy repository password |
