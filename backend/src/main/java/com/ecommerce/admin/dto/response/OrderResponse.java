package com.ecommerce.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private CustomerInfo customer;
    private List<OrderItemInfo> items;
    private BigDecimal total;
    private String status;
    private String paymentStatus;
    private ShippingAddressInfo shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CustomerInfo {
        private String name;
        private String email;
        private String phone;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemInfo {
        private Long id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
        private String image;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShippingAddressInfo {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }
}
