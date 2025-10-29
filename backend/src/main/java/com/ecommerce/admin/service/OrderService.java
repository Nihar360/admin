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
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<Order> orderPage;
        
        if (status != null && !status.equals("all") && search != null && !search.isEmpty()) {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            orderPage = orderRepository.searchOrdersByStatus(orderStatus, search, pageRequest);
        } else if (status != null && !status.equals("all")) {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            orderPage = orderRepository.findByStatus(orderStatus, pageRequest);
        } else if (search != null && !search.isEmpty()) {
            orderPage = orderRepository.searchOrders(search, pageRequest);
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
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(1L); // TODO: Get from authenticated admin
        history.setNotes("Status updated via admin panel");
        orderStatusHistoryRepository.save(history);
        
        return convertToOrderResponse(updatedOrder);
    }
    
    private OrderResponse convertToOrderResponse(Order order) {
        User user = userRepository.findById(order.getUserId()).orElse(null);
        Address address = addressRepository.findById(order.getShippingAddressId()).orElse(null);
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        
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
                            Product product = productRepository.findById(item.getProductId()).orElse(null);
                            return OrderResponse.OrderItemInfo.builder()
                                    .id(item.getId())
                                    .name(product != null ? product.getName() : "Unknown Product")
                                    .quantity(item.getQuantity())
                                    .price(item.getPrice())
                                    .image(product != null ? product.getImage() : "")
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .total(order.getTotal())
                .status(order.getStatus().toString().toLowerCase())
                .paymentStatus("paid") // TODO: Add payment status logic
                .shippingAddress(address != null ? OrderResponse.ShippingAddressInfo.builder()
                        .street(address.getAddressLine1())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(address.getZipCode())
                        .country(address.getCountry())
                        .build() : null)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
