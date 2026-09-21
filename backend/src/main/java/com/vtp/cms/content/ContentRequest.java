package com.vtp.cms.content;
import jakarta.validation.constraints.*;
public record ContentRequest(@NotNull ContentType type,@NotBlank @Size(max=180) String title,@NotBlank @Pattern(regexp="[a-z0-9]+(?:-[a-z0-9]+)*") String slug,String summary,String body,String imageUrl,String ctaLabel,String ctaUrl,Integer sortOrder,@Pattern(regexp="[a-z]{2}") String locale) {}
