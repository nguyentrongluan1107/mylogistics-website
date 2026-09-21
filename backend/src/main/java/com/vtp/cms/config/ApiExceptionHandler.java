package com.vtp.cms.config;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<?> notFound(Exception e){return ResponseEntity.status(404).body(Map.of("message",e.getMessage()));}
 @ExceptionHandler({IllegalStateException.class,DataIntegrityViolationException.class}) ResponseEntity<?> conflict(Exception e){return ResponseEntity.status(409).body(Map.of("message",e.getMessage()==null?"Content conflict":e.getMessage()));}
}
