package com.ecommerce.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_activity_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminActivityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "admin_id", nullable = false)
    private Long adminId;
    
    @Column(name = "action_type", nullable = false, length = 100)
    private String actionType;
    
    @Column(name = "resource_type", nullable = false, length = 50)
    private String resourceType;
    
    @Column(name = "resource_id", length = 100)
    private String resourceId;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
