package com.ecommerce.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private String type;
    private Boolean isRead;
    private String referenceType;
    private Long referenceId;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}