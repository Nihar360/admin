package com.ecommerce.admin.model;

import com.ecommerce.admin.model.enums.CouponType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    @Column(name = "min_purchase", precision = 10, scale = 2)
    private BigDecimal minPurchase = BigDecimal.ZERO;
    
    @Column(name = "max_discount", precision = 10, scale = 2)
    private BigDecimal maxDiscount;
    
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;
    
    @Column(name = "usage_count")
    private Integer usageCount = 0;
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
