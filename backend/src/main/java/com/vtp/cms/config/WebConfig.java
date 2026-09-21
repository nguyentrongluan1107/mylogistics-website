package com.vtp.cms.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration public class WebConfig implements WebMvcConfigurer {
 private final AdminApiKeyInterceptor auth; @Value("${cms.cors-origins}") private String[] origins;
 public WebConfig(AdminApiKeyInterceptor auth){this.auth=auth;}
 public void addInterceptors(InterceptorRegistry r){r.addInterceptor(auth).addPathPatterns("/api/admin/**");}
 public void addCorsMappings(CorsRegistry r){r.addMapping("/api/**").allowedOrigins(origins).allowedMethods("GET","POST","PUT","OPTIONS").allowedHeaders("Content-Type","X-CMS-API-Key","X-Actor");}
}
