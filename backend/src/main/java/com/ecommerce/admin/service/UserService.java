package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.response.UserResponse;
import com.ecommerce.admin.model.User;
import com.ecommerce.admin.model.enums.UserRole;
import com.ecommerce.admin.repository.OrderRepository;
import com.ecommerce.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    public List<UserResponse> getAllUsers(String search, String status) {
        List<User> users;
        
        if (search != null && !search.isEmpty()) {
            users = userRepository.searchCustomers(search);
        } else if (status != null && !status.equals("all")) {
            Boolean isActive = status.equals("active");
            users = userRepository.findByActive(isActive);
        } else {
            users = userRepository.findByRole(UserRole.CUSTOMER);
        }
        
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
    
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToUserResponse(user);
    }
    
    private UserResponse convertToUserResponse(User user) {
        Long totalOrders = (long) orderRepository.findByUserId(user.getId()).size();
        BigDecimal totalSpent = orderRepository.findByUserId(user.getId()).stream()
                .map(order -> order.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .phone(user.getMobile())
                .totalOrders(totalOrders)
                .totalSpent(totalSpent)
                .joinedAt(user.getCreatedAt())
                .status(user.getActive() ? "active" : "blocked")
                .build();
    }
}
