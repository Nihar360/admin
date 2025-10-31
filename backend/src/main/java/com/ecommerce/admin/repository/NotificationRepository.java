package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByIsReadFalse();
    
    Long countByIsReadFalse();
    
    List<Notification> findByUserId(Long userId);
    
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
}