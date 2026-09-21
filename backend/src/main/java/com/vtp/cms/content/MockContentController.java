package com.vtp.cms.content;

import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("mock")
@RestController
@RequestMapping("/api")
public class MockContentController {
    private final MockContentStore store;

    public MockContentController(MockContentStore store) {
        this.store = store;
    }

    @GetMapping("/public/content")
    List<MockContent> published(@RequestParam(required = false) ContentType type,
                                @RequestParam(defaultValue = "en") String locale) {
        return store.published(type, locale);
    }

    @GetMapping("/admin/content")
    List<MockContent> admin() {
        return store.adminList();
    }

    @PostMapping("/admin/content")
    @ResponseStatus(HttpStatus.CREATED)
    MockContent create(@Valid @RequestBody ContentRequest request,
                       @RequestHeader(value = "X-Actor", defaultValue = "cms-admin") String actor) {
        return store.create(request, actor);
    }

    @PutMapping("/admin/content/{id}")
    MockContent update(@PathVariable UUID id, @Valid @RequestBody ContentRequest request) {
        return store.update(id, request);
    }

    @PostMapping("/admin/content/{id}/{action}")
    MockContent transition(@PathVariable UUID id, @PathVariable String action,
                           @RequestHeader(value = "X-Actor", defaultValue = "cms-reviewer") String actor) {
        return store.transition(id, action, actor);
    }
}
