package com.vtp.cms.content;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name="cms_content")
public class Content {
 @Id private UUID id;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private ContentType type;
 @Column(nullable=false,length=180) private String title;
 @Column(nullable=false,unique=true,length=200) private String slug;
 @Column(columnDefinition="text") private String summary;
 @Column(columnDefinition="text") private String body;
 @Column(name="image_url",length=1000) private String imageUrl;
 @Column(name="cta_label",length=100) private String ctaLabel;
 @Column(name="cta_url",length=1000) private String ctaUrl;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false,length=10) private String locale;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private ContentStatus status;
 @Column(name="created_by",nullable=false,length=100) private String createdBy;
 @Column(name="reviewed_by",length=100) private String reviewedBy;
 @Column(name="published_by",length=100) private String publishedBy;
 @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
 @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
 @Column(name="published_at") private OffsetDateTime publishedAt;
 @Version private long version;
 @PrePersist void create(){ if(id==null)id=UUID.randomUUID(); var now=OffsetDateTime.now();createdAt=now;updatedAt=now;if(status==null)status=ContentStatus.DRAFT;if(locale==null)locale="en"; }
 @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
 public UUID getId(){return id;} public ContentType getType(){return type;} public void setType(ContentType v){type=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getSlug(){return slug;} public void setSlug(String v){slug=v;} public String getSummary(){return summary;} public void setSummary(String v){summary=v;} public String getBody(){return body;} public void setBody(String v){body=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public String getCtaLabel(){return ctaLabel;} public void setCtaLabel(String v){ctaLabel=v;} public String getCtaUrl(){return ctaUrl;} public void setCtaUrl(String v){ctaUrl=v;} public int getSortOrder(){return sortOrder;} public void setSortOrder(int v){sortOrder=v;} public String getLocale(){return locale;} public void setLocale(String v){locale=v;} public ContentStatus getStatus(){return status;} public void setStatus(ContentStatus v){status=v;} public String getCreatedBy(){return createdBy;} public void setCreatedBy(String v){createdBy=v;} public String getReviewedBy(){return reviewedBy;} public void setReviewedBy(String v){reviewedBy=v;} public String getPublishedBy(){return publishedBy;} public void setPublishedBy(String v){publishedBy=v;} public OffsetDateTime getCreatedAt(){return createdAt;} public OffsetDateTime getUpdatedAt(){return updatedAt;} public OffsetDateTime getPublishedAt(){return publishedAt;} public void setPublishedAt(OffsetDateTime v){publishedAt=v;} public long getVersion(){return version;}
}
