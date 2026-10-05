# API Reference — Profile Image API

The `ProfileImageApi` interface defines the REST contract for profile image operations. Consuming microservices implement this interface on a `@RestController` to expose the endpoints.

---

## Authentication

All endpoints require a `session-token` HTTP header. The value is an opaque session token validated by the implementing service.

```
session-token: <session-token-value>
```

---

## Base Path

The base path is defined by the implementing controller's `@RequestMapping`. The paths below are relative to that base.

---

## Endpoints

### POST `/application/{applicationId}` — Upload Profile Image

Uploads a profile image for a specific application context.

**Content-Type:** `multipart/form-data`  
**Produces:** `application/json`

#### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `applicationId` | UUID | Yes | The application identifier |

#### Request Headers

| Header | Required | Description |
|---|---|---|
| `session-token` | Yes | User session token |

#### Request Parts

| Part | Type | Required | Description |
|---|---|---|---|
| `image` | `byte[]` | Yes | Raw image file bytes |

#### Response

| Status | Description | Body |
|---|---|---|
| `201 Created` | Image uploaded successfully | `PostProfileImageResponse` |
| `400 Bad Request` | Invalid input data | — |
| `500 Internal Server Error` | Server error | — |
| `501 Not Implemented` | Default (interface not overridden) | `PostProfileImageResponse { ref: "" }` |

#### Response Body — `PostProfileImageResponse`

```json
{
  "ref": "profiles/app-uuid/user-image.jpg"
}
```

| Field | Type | Description |
|---|---|---|
| `ref` | String | Object key or reference path in the storage backend |

---

### GET `/` — Retrieve Profile Image

Retrieves the raw profile image bytes for the authenticated user.

**Produces:** `application/octet-stream` (raw bytes)

#### Request Headers

| Header | Required | Description |
|---|---|---|
| `session-token` | Yes | User session token |

#### Response

| Status | Description | Body |
|---|---|---|
| `200 OK` | Image retrieved successfully | Raw `byte[]` |
| `404 Not Found` | Image not found | — |
| `500 Internal Server Error` | Server error | — |
| `501 Not Implemented` | Default (interface not overridden) | Empty `byte[]` |

---

### GET `/application/{applicationId}/reference` — Get Profile Image Reference

Retrieves the storage reference (URL or object key) for a user's profile image.

**Produces:** `application/json`

#### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `applicationId` | UUID | Yes | The application identifier |

#### Request Headers

| Header | Required | Description |
|---|---|---|
| `session-token` | Yes | User session token |

#### Response

| Status | Description | Body |
|---|---|---|
| `200 OK` | Reference retrieved successfully | `GetProfileImageReferenceResponse` |
| `401 Unauthorized` | Unauthorized access | — |
| `404 Not Found` | Reference not found | — |
| `500 Internal Server Error` | Server error | — |
| `501 Not Implemented` | Default (interface not overridden) | `null` |

#### Response Body — `GetProfileImageReferenceResponse`

```json
{
  "ref": "https://pub-xxx.r2.dev/profiles/app-uuid/user-image.jpg"
}
```

| Field | Type | Description |
|---|---|---|
| `ref` | String | Public URL or storage reference for the profile image |

---

## OpenAPI / Swagger

All endpoints are annotated with SpringDoc OpenAPI (`@Operation`, `@ApiResponses`, `@Parameter`). When a consuming service includes `springdoc-openapi-ui`, the Swagger UI at `/swagger-ui.html` will automatically include these endpoints.

---

## Implementing the API

```java
@RestController
@RequestMapping("/v1/profile-images")
public class ProfileImageController implements ProfileImageApi {

    private final MyProfileImageService service;

    public ProfileImageController(MyProfileImageService service) {
        this.service = service;
    }

    @Override
    public ProfileImageService getService() {
        return service;
    }

    @Override
    public ResponseEntity<PostProfileImageResponse> uploadProfileImage(
            String token, UUID applicationId, byte[] image) throws Exception {
        return service.save(token, applicationId, image);
    }

    @Override
    public ResponseEntity<GetProfileImageReferenceResponse> getProfileImageReference(
            String token, UUID applicationId) throws Exception {
        return service.getProfileImageReference(token, applicationId);
    }
}
```

---

## Transfer Objects

### `PostProfileImageResponse`

Java record. Immutable response for image upload operations.

```java
public record PostProfileImageResponse(String ref) {}
```

### `GetProfileImageReferenceResponse`

Java record. Immutable response for image reference retrieval.

```java
public record GetProfileImageReferenceResponse(String ref) {}
```
