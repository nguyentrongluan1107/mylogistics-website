package com.vtp.cms.content;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MockContent(
    UUID id,
    ContentType type,
    String title,
    String slug,
    String summary,
    String body,
    String imageUrl,
    String ctaLabel,
    String ctaUrl,
    int sortOrder,
    String locale,
    ContentStatus status,
    String createdBy,
    String reviewedBy,
    String publishedBy,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime publishedAt,
    long version
) {}
