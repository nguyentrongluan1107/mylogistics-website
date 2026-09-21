package com.vtp.cms.config;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component public class AdminApiKeyInterceptor implements HandlerInterceptor {
 @Value("${cms.api-key}") private String apiKey;
 public boolean preHandle(HttpServletRequest req,HttpServletResponse res,Object handler)throws Exception{if("OPTIONS".equals(req.getMethod()))return true;String supplied=req.getHeader("X-CMS-API-Key");if(apiKey.equals(supplied))return true;res.sendError(401,"Invalid CMS API key");return false;}
}
