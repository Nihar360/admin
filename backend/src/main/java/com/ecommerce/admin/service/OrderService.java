package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.request.OrderStatusUpdateRequest;
import com.ecommerce.admin.dto.response.OrderResponse;
import com.ecommerce.admin.model.*;
import com.ecommerce.admin.model.enums.OrderStatus;
import com.ecommerce.admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    
    public Map<String, Object> getOrders(String status, String search, int page, int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
            Page<Order> orderPage;
            
            // Simplified query - just filter by status if provided
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                try {
                    OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                    orderPage = orderRepository.findByStatus(orderStatus, pageRequest);
                } catch (IllegalArgumentException e) {
                    // Invalid status, return all
                    orderPage = orderRepository.findAll(pageRequest);
                }
            } else {
                orderPage = orderRepository.findAll(pageRequest);
            }
            
            List<OrderResponse> orders = orderPage.getContent().stream()
                    .map(this::convertToOrderResponse)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("orders", orders);
            response.put("total", orderPage.getTotalElements());
            response.put("page", page);
            response.put("totalPages", orderPage.getTotalPages());
            
            return response;
        } catch (Exception e) {
            System.err.println("Error in getOrders: " + e.getMessage());
            e.printStackTrace();
            
            // Return empty result instead of failing
            Map<String, Object> response = new HashMap<>();
            response.put("orders", new ArrayList<>());
            response.put("total", 0);
            response.put("page", page);
            response.put("totalPages", 0);
            return response;
        }
    }
    
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToOrderResponse(order);
    }
    
    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = OrderStatus.valueOf(request.getStatus().toUpperCase());
        
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        
        // Save status history
        try {
            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrderId(order.getId());
            history.setOldStatus(oldStatus);
            history.setNewStatus(newStatus);
            history.setChangedBy(1L);
            history.setNotes("Status updated via admin panel");
            orderStatusHistoryRepository.save(history);
        } catch (Exception e) {
            System.err.println("Failed to save order status history: " + e.getMessage());
        }
        
        return convertToOrderResponse(updatedOrder);
    }
    
    private OrderResponse convertToOrderResponse(Order order) {
        try {
            User user = null;
            Address address = null;
            List<OrderItem> items = new ArrayList<>();
            
            try {
                user = userRepository.findById(order.getUserId()).orElse(null);
            } catch (Exception e) {
                System.err.println("Failed to load user: " + e.getMessage());
            }
            
            try {
                if (order.getShippingAddressId() != null) {
                    address = addressRepository.findById(order.getShippingAddressId()).orElse(null);
                }
            } catch (Exception e) {
                System.err.println("Failed to load address: " + e.getMessage());
            }
            
            try {
                items = orderItemRepository.findByOrderId(order.getId());
            } catch (Exception e) {
                System.err.println("Failed to load order items: " + e.getMessage());
            }
            
            return OrderResponse.builder()
                    .id(order.getId())
                    .orderNumber(order.getOrderNumber())
                    .customer(OrderResponse.CustomerInfo.builder()
                            .name(user != null ? user.getFullName() : "Unknown")
                            .email(user != null ? user.getEmail() : "")
                            .phone(user != null ? user.getMobile() : "")
                            .build())
                    .items(items.stream()
                            .map(item -> {
                                Product product = null;
                                try {
                                    product = productRepository.findById(item.getProductId()).orElse(null);
                                } catch (Exception e) {
                                    System.err.println("Failed to load product: " + e.getMessage());
                                }
                                return OrderResponse.OrderItemInfo.builder()
                                        .id(item.getId())
                                        .name(product != null ? product.getName() : "Unknown Product")
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .image(product != null && product.getImage() != null ? product.getImage() : "")
                                        .build();
                            })
                            .collect(Collectors.toList()))
                    .total(order.getTotal())
                    .status(order.getStatus().toString().toLowerCase())
                    .paymentStatus("paid")
                    .shippingAddress(address != null ? OrderResponse.ShippingAddressInfo.builder()
                            .street(address.getAddressLine1() != null ? address.getAddressLine1() : "")
                            .city(address.getCity() != null ? address.getCity() : "")
                            .state(address.getState() != null ? address.getState() : "")
                            .zipCode(address.getZipCode() != null ? address.getZipCode() : "")
                            .country(address.getCountry() != null ? address.getCountry() : "")
                            .build() : OrderResponse.ShippingAddressInfo.builder()
                            .street("")
                            .city("")
                            .state("")
                            .zipCode("")
                            .country("")
                            .build())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
        } catch (Exception e) {
            System.err.println("Error converting order to response: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert order", e);
        }
    }
}