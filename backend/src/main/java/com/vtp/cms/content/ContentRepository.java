package com.vtp.cms.content;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ContentRepository extends JpaRepository<Content,UUID>{
 List<Content> findAllByOrderByUpdatedAtDesc();
 List<Content> findByStatusAndLocaleOrderBySortOrderAscUpdatedAtDesc(ContentStatus status,String locale);
 List<Content> findByStatusAndTypeAndLocaleOrderBySortOrderAscUpdatedAtDesc(ContentStatus status,ContentType type,String locale);
}
