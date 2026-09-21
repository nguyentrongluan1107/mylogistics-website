package com.vtp.cms.content;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.*;
@Profile("!mock") @Service @Transactional public class ContentService {
 private final ContentRepository repo; public ContentService(ContentRepository repo){this.repo=repo;}
 @Transactional(readOnly=true) public List<Content> adminList(){return repo.findAllByOrderByUpdatedAtDesc();}
 @Transactional(readOnly=true) public List<Content> published(ContentType type,String locale){return type==null?repo.findByStatusAndLocaleOrderBySortOrderAscUpdatedAtDesc(ContentStatus.PUBLISHED,locale):repo.findByStatusAndTypeAndLocaleOrderBySortOrderAscUpdatedAtDesc(ContentStatus.PUBLISHED,type,locale);}
 public Content create(ContentRequest r,String actor){Content c=new Content();apply(c,r);c.setStatus(ContentStatus.DRAFT);c.setCreatedBy(actor);return repo.save(c);}
 public Content update(UUID id,ContentRequest r){Content c=get(id);if(c.getStatus()==ContentStatus.PUBLISHED)throw new IllegalStateException("Published content must be archived before editing");apply(c,r);return c;}
 public Content submit(UUID id){Content c=get(id);require(c,ContentStatus.DRAFT);c.setStatus(ContentStatus.PENDING_REVIEW);return c;}
 public Content returnToDraft(UUID id){Content c=get(id);require(c,ContentStatus.PENDING_REVIEW);c.setStatus(ContentStatus.DRAFT);return c;}
 public Content publish(UUID id,String actor){Content c=get(id);require(c,ContentStatus.PENDING_REVIEW);c.setReviewedBy(actor);c.setPublishedBy(actor);c.setPublishedAt(OffsetDateTime.now());c.setStatus(ContentStatus.PUBLISHED);return c;}
 public Content archive(UUID id){Content c=get(id);require(c,ContentStatus.PUBLISHED);c.setStatus(ContentStatus.ARCHIVED);return c;}
 private Content get(UUID id){return repo.findById(id).orElseThrow(()->new NoSuchElementException("Content not found"));}
 private void require(Content c,ContentStatus expected){if(c.getStatus()!=expected)throw new IllegalStateException("Expected status "+expected+" but was "+c.getStatus());}
 private void apply(Content c,ContentRequest r){c.setType(r.type());c.setTitle(r.title());c.setSlug(r.slug());c.setSummary(r.summary());c.setBody(r.body());c.setImageUrl(r.imageUrl());c.setCtaLabel(r.ctaLabel());c.setCtaUrl(r.ctaUrl());c.setSortOrder(r.sortOrder()==null?0:r.sortOrder());c.setLocale(r.locale()==null?"en":r.locale());}
}
