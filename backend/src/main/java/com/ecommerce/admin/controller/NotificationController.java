package com.ecommerce.admin.controller;

import com.ecommerce.admin.dto.response.ApiResponse;
import com.ecommerce.admin.dto.response.NotificationResponse;
import com.ecommerce.admin.model.Notification;
import com.ecommerce.admin.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {
    
    private final NotificationRepository notificationRepository;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findAll();
            List<NotificationResponse> response = notifications.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve notifications"));
        }
    }
    
    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnreadNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findByIsReadFalse();
            List<NotificationResponse> response = notifications.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve unread notifications"));
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        try {
            Long count = notificationRepository.countByIsReadFalse();
            return ResponseEntity.ok(ApiResponse.success(count));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to count notifications"));
        }
    }
    
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
            notificationRepository.save(notification);
            
            return ResponseEntity.ok(ApiResponse.success("Notification marked as read", mapToResponse(notification)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Notification not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to mark notification as read"));
        }
    }
    
    @PatchMapping("/mark-all-read")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead() {
        try {
            List<Notification> unreadNotifications = notificationRepository.findByIsReadFalse();
            LocalDateTime now = LocalDateTime.now();
            
            unreadNotifications.forEach(notification -> {
                notification.setIsRead(true);
                notification.setReadAt(now);
            });
            
            notificationRepository.saveAll(unreadNotifications);
            return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to mark all notifications as read"));
        }
    }
    
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType().name())
                .isRead(notification.getIsRead())
                .referenceType(notification.getReferenceType())
                .referenceId(notification.getReferenceId())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .build();
    }
}