package com.ecommerce.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequest {
    
    @NotBlank(message = "Coupon code is required")
    private String code;
    
    @NotBlank(message = "Type is required (percentage or fixed)")
    private String type;
    
    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private BigDecimal value;
    
    private BigDecimal minPurchase;
    private BigDecimal maxDiscount;
    
    @NotNull(message = "Usage limit is required")
    private Integer usageLimit;
    
    @NotNull(message = "Expiration date is required")
    private LocalDateTime expiresAt;
    
    private Boolean isActive;
}
