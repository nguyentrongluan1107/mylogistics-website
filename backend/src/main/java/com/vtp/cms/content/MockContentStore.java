package com.vtp.cms.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.OffsetDateTime;
import java.util.*;

@Profile("mock")
@Service
public class MockContentStore {
    private final ObjectMapper mapper;
    private final Path dataFile;
    private List<MockContent> items = new ArrayList<>();

    public MockContentStore(ObjectMapper mapper, @Value("${mock.data-file}") String dataFile) {
        this.mapper = mapper;
        this.dataFile = Path.of(dataFile).toAbsolutePath().normalize();
    }

    @PostConstruct
    synchronized void init() throws IOException {
        if (dataFile.getParent() != null) Files.createDirectories(dataFile.getParent());
        if (Files.notExists(dataFile)) {
            try (var input = new ClassPathResource("mock/cms-content.json").getInputStream()) {
                Files.copy(input, dataFile);
            }
        }
        items = mapper.readValue(dataFile.toFile(), new TypeReference<>() {});
    }

    public synchronized List<MockContent> adminList() {
        return items.stream().sorted(Comparator.comparing(MockContent::updatedAt).reversed()).toList();
    }

    public synchronized List<MockContent> published(ContentType type, String locale) {
        return items.stream()
            .filter(item -> item.status() == ContentStatus.PUBLISHED)
            .filter(item -> type == null || item.type() == type)
            .filter(item -> Objects.equals(item.locale(), locale))
            .sorted(Comparator.comparingInt(MockContent::sortOrder)
                .thenComparing(MockContent::updatedAt, Comparator.reverseOrder()))
            .toList();
    }

    public synchronized MockContent create(ContentRequest request, String actor) {
        ensureSlugAvailable(request.slug(), null);
        var now = OffsetDateTime.now();
        var content = fromRequest(UUID.randomUUID(), request, ContentStatus.DRAFT, actor, null, null, now, now, null, 0);
        items.add(content);
        persist();
        return content;
    }

    public synchronized MockContent update(UUID id, ContentRequest request) {
        var current = get(id);
        if (current.status() == ContentStatus.PUBLISHED) {
            throw new IllegalStateException("Published content must be archived before editing");
        }
        ensureSlugAvailable(request.slug(), id);
        var updated = fromRequest(id, request, current.status(), current.createdBy(), current.reviewedBy(),
            current.publishedBy(), current.createdAt(), OffsetDateTime.now(), current.publishedAt(), current.version() + 1);
        replace(updated);
        return updated;
    }

    public synchronized MockContent transition(UUID id, String action, String actor) {
        var current = get(id);
        var now = OffsetDateTime.now();
        MockContent updated;
        switch (action) {
            case "submit" -> {
                require(current, ContentStatus.DRAFT);
                updated = copy(current, ContentStatus.PENDING_REVIEW, current.reviewedBy(), current.publishedBy(), current.publishedAt(), now);
            }
            case "return" -> {
                require(current, ContentStatus.PENDING_REVIEW);
                updated = copy(current, ContentStatus.DRAFT, current.reviewedBy(), current.publishedBy(), current.publishedAt(), now);
            }
            case "publish" -> {
                require(current, ContentStatus.PENDING_REVIEW);
                updated = copy(current, ContentStatus.PUBLISHED, actor, actor, now, now);
            }
            case "archive" -> {
                require(current, ContentStatus.PUBLISHED);
                updated = copy(current, ContentStatus.ARCHIVED, current.reviewedBy(), current.publishedBy(), current.publishedAt(), now);
            }
            default -> throw new IllegalArgumentException("Unsupported action " + action);
        }
        replace(updated);
        return updated;
    }

    private MockContent fromRequest(UUID id, ContentRequest request, ContentStatus status, String createdBy,
                                    String reviewedBy, String publishedBy, OffsetDateTime createdAt,
                                    OffsetDateTime updatedAt, OffsetDateTime publishedAt, long version) {
        return new MockContent(id, request.type(), request.title(), request.slug(), request.summary(), request.body(),
            request.imageUrl(), request.ctaLabel(), request.ctaUrl(), request.sortOrder() == null ? 0 : request.sortOrder(),
            request.locale() == null ? "en" : request.locale(), status, createdBy, reviewedBy, publishedBy,
            createdAt, updatedAt, publishedAt, version);
    }

    private MockContent copy(MockContent item, ContentStatus status, String reviewedBy, String publishedBy,
                             OffsetDateTime publishedAt, OffsetDateTime updatedAt) {
        return new MockContent(item.id(), item.type(), item.title(), item.slug(), item.summary(), item.body(),
            item.imageUrl(), item.ctaLabel(), item.ctaUrl(), item.sortOrder(), item.locale(), status,
            item.createdBy(), reviewedBy, publishedBy, item.createdAt(), updatedAt, publishedAt, item.version() + 1);
    }

    private MockContent get(UUID id) {
        return items.stream().filter(item -> item.id().equals(id)).findFirst()
            .orElseThrow(() -> new NoSuchElementException("Content not found"));
    }

    private void replace(MockContent updated) {
        items = new ArrayList<>(items);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id().equals(updated.id())) {
                items.set(i, updated);
                persist();
                return;
            }
        }
        throw new NoSuchElementException("Content not found");
    }

    private void ensureSlugAvailable(String slug, UUID currentId) {
        boolean exists = items.stream().anyMatch(item -> item.slug().equals(slug) && !item.id().equals(currentId));
        if (exists) throw new IllegalStateException("Slug already exists");
    }

    private void require(MockContent item, ContentStatus expected) {
        if (item.status() != expected) {
            throw new IllegalStateException("Expected status " + expected + " but was " + item.status());
        }
    }

    private void persist() {
        try {
            Path temporary = dataFile.resolveSibling(dataFile.getFileName() + ".tmp");
            mapper.writerWithDefaultPrettyPrinter().writeValue(temporary.toFile(), items);
            try {
                Files.move(temporary, dataFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temporary, dataFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not save mock content file", exception);
        }
    }
}
