CREATE TABLE cms_content (
 id UUID PRIMARY KEY,
 type VARCHAR(30) NOT NULL,
 title VARCHAR(180) NOT NULL,
 slug VARCHAR(200) NOT NULL UNIQUE,
 summary TEXT,
 body TEXT,
 image_url VARCHAR(1000),
 cta_label VARCHAR(100),
 cta_url VARCHAR(1000),
 sort_order INTEGER NOT NULL DEFAULT 0,
 locale VARCHAR(10) NOT NULL DEFAULT 'en',
 status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
 created_by VARCHAR(100) NOT NULL,
 reviewed_by VARCHAR(100),
 published_by VARCHAR(100),
 created_at TIMESTAMPTZ NOT NULL,
 updated_at TIMESTAMPTZ NOT NULL,
 published_at TIMESTAMPTZ,
 version BIGINT NOT NULL DEFAULT 0,
 CONSTRAINT ck_content_status CHECK(status IN ('DRAFT','PENDING_REVIEW','PUBLISHED','ARCHIVED')),
 CONSTRAINT ck_content_type CHECK(type IN ('BANNER','PAGE','SERVICE','NEWS','CAMPAIGN','CAREER','CONTACT'))
);
CREATE INDEX idx_content_public ON cms_content(status,locale,type,sort_order,updated_at DESC);
CREATE INDEX idx_content_admin_updated ON cms_content(updated_at DESC);
