package com.vtp.cms.content;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Profile("!mock") @RestController @RequestMapping("/api") public class ContentController {
 private final ContentService service; public ContentController(ContentService service){this.service=service;}
 @GetMapping("/public/content") List<Content> published(@RequestParam(required=false) ContentType type,@RequestParam(defaultValue="en") String locale){return service.published(type,locale);}
 @GetMapping("/admin/content") List<Content> admin(){return service.adminList();}
 @PostMapping("/admin/content") @ResponseStatus(HttpStatus.CREATED) Content create(@Valid @RequestBody ContentRequest r,@RequestHeader(value="X-Actor",defaultValue="cms-admin") String actor){return service.create(r,actor);}
 @PutMapping("/admin/content/{id}") Content update(@PathVariable UUID id,@Valid @RequestBody ContentRequest r){return service.update(id,r);}
 @PostMapping("/admin/content/{id}/submit") Content submit(@PathVariable UUID id){return service.submit(id);}
 @PostMapping("/admin/content/{id}/return") Content reject(@PathVariable UUID id){return service.returnToDraft(id);}
 @PostMapping("/admin/content/{id}/publish") Content publish(@PathVariable UUID id,@RequestHeader(value="X-Actor",defaultValue="cms-reviewer") String actor){return service.publish(id,actor);}
 @PostMapping("/admin/content/{id}/archive") Content archive(@PathVariable UUID id){return service.archive(id);}
}
