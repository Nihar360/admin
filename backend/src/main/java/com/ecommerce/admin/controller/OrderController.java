package com.ecommerce.admin.controller;

import com.ecommerce.admin.dto.request.OrderStatusUpdateRequest;
import com.ecommerce.admin.dto.response.ApiResponse;
import com.ecommerce.admin.dto.response.OrderResponse;
import com.ecommerce.admin.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping
    public ApiResponse<Map<String, Object>> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Map<String, Object> orders = orderService.getOrders(status, search, page, limit);
        return ApiResponse.success(orders);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return ApiResponse.success(order);
    }
    
    @PatchMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest request) {
        OrderResponse order = orderService.updateOrderStatus(id, request);
        return ApiResponse.success("Order status updated successfully", order);
    }
}