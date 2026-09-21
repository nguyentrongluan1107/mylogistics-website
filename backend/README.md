# VTP CMS API

Spring Boot 3 / Java 17 backend for PostgreSQL-backed website content.

Workflow: `DRAFT -> PENDING_REVIEW -> PUBLISHED -> ARCHIVED`. Only published records are returned by `/api/public/content`.

Required environment variables are listed in `.env.example`. Admin requests require `X-CMS-API-Key`; the public read endpoint does not.

Run with Maven: `mvn spring-boot:run`. Flyway creates the schema and demo content automatically.

## File-backed mock mode

Run the complete backend API without PostgreSQL:

```bash
SPRING_PROFILES_ACTIVE=mock \
CMS_API_KEY=local-development-key \
MOCK_DATA_FILE=./data/cms-content.json \
mvn spring-boot:run
```

On first startup, the backend copies seeded records from
`src/main/resources/mock/cms-content.json` to `MOCK_DATA_FILE`. All admin
creates, edits and workflow transitions are written back to that JSON file.
The public API returns only records with status `PUBLISHED`.

Endpoints are identical in mock and PostgreSQL modes:

- `GET /api/public/content?type=BANNER&locale=en`
- `GET /api/admin/content`
- `POST /api/admin/content`
- `PUT /api/admin/content/{id}`
- `POST /api/admin/content/{id}/submit`
- `POST /api/admin/content/{id}/return`
- `POST /api/admin/content/{id}/publish`
- `POST /api/admin/content/{id}/archive`
