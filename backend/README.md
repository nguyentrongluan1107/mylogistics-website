# VTP CMS API

Spring Boot 3 / Java 17 backend for PostgreSQL-backed website content.

Workflow: `DRAFT -> PENDING_REVIEW -> PUBLISHED -> ARCHIVED`. Only published records are returned by `/api/public/content`.

Required environment variables are listed in `.env.example`. Admin requests require `X-CMS-API-Key`; the public read endpoint does not.

Run with Maven: `mvn spring-boot:run`. Flyway creates the schema and demo content automatically.
