package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.response.UserResponse;
import com.ecommerce.admin.model.User;
import com.ecommerce.admin.model.Order;
import com.ecommerce.admin.repository.OrderRepository;
import com.ecommerce.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    public List<UserResponse> getAllUsers(String search, String status) {
        try {
            List<User> users;
            
            // Simplified - get all users first
            users = userRepository.findAll();
            
            // Filter by status if provided
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                Boolean isActive = status.equals("active");
                users = users.stream()
                        .filter(u -> u.getActive() != null && u.getActive().equals(isActive))
                        .collect(Collectors.toList());
            }
            
            // Apply search filter in Java
            if (search != null && !search.isEmpty()) {
                String searchLower = search.toLowerCase();
                users = users.stream()
                        .filter(u -> 
                            (u.getFullName() != null && u.getFullName().toLowerCase().contains(searchLower)) ||
                            (u.getEmail() != null && u.getEmail().toLowerCase().contains(searchLower)) ||
                            (u.getMobile() != null && u.getMobile().contains(search))
                        )
                        .collect(Collectors.toList());
            }
            
            return users.stream()
                    .map(this::convertToUserResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getAllUsers: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToUserResponse(user);
    }
    
    private UserResponse convertToUserResponse(User user) {
        try {
            Long totalOrders = 0L;
            BigDecimal totalSpent = BigDecimal.ZERO;
            
            try {
                List<Order> userOrders = orderRepository.findByUserId(user.getId());
                totalOrders = (long) userOrders.size();
                totalSpent = userOrders.stream()
                        .map(order -> order.getTotal() != null ? order.getTotal() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            } catch (Exception e) {
                System.err.println("Failed to calculate user orders: " + e.getMessage());
            }
            
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getFullName() != null ? user.getFullName() : "")
                    .email(user.getEmail() != null ? user.getEmail() : "")
                    .phone(user.getMobile() != null ? user.getMobile() : "")
                    .totalOrders(totalOrders)
                    .totalSpent(totalSpent)
                    .joinedAt(user.getCreatedAt())
                    .status(user.getActive() != null && user.getActive() ? "active" : "blocked")
                    .build();
        } catch (Exception e) {
            System.err.println("Error converting user to response: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert user", e);
        }
    }
}