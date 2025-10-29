package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.request.CouponCreateRequest;
import com.ecommerce.admin.dto.response.CouponResponse;
import com.ecommerce.admin.model.Coupon;
import com.ecommerce.admin.model.enums.CouponType;
import com.ecommerce.admin.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    
    private final CouponRepository couponRepository;
    
    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::convertToCouponResponse)
                .collect(Collectors.toList());
    }
    
    public CouponResponse getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        return convertToCouponResponse(coupon);
    }
    
    @Transactional
    public CouponResponse createCoupon(CouponCreateRequest request) {
        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setType(CouponType.valueOf(request.getType().toUpperCase()));
        coupon.setValue(request.getValue());
        coupon.setMinPurchase(request.getMinPurchase());
        coupon.setMaxDiscount(request.getMaxDiscount());
        coupon.setUsageLimit(request.getUsageLimit());
        coupon.setUsageCount(0);
        coupon.setExpiresAt(request.getExpiresAt());
        coupon.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToCouponResponse(savedCoupon);
    }
    
    @Transactional
    public CouponResponse updateCoupon(Long id, CouponCreateRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        
        coupon.setCode(request.getCode());
        coupon.setType(CouponType.valueOf(request.getType().toUpperCase()));
        coupon.setValue(request.getValue());
        coupon.setMinPurchase(request.getMinPurchase());
        coupon.setMaxDiscount(request.getMaxDiscount());
        coupon.setUsageLimit(request.getUsageLimit());
        coupon.setExpiresAt(request.getExpiresAt());
        if (request.getIsActive() != null) {
            coupon.setIsActive(request.getIsActive());
        }
        
        Coupon updatedCoupon = couponRepository.save(coupon);
        return convertToCouponResponse(updatedCoupon);
    }
    
    @Transactional
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        couponRepository.delete(coupon);
    }
    
    private CouponResponse convertToCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .type(coupon.getType().toString().toLowerCase())
                .value(coupon.getValue())
                .minPurchase(coupon.getMinPurchase())
                .maxDiscount(coupon.getMaxDiscount())
                .usageLimit(coupon.getUsageLimit())
                .usageCount(coupon.getUsageCount())
                .expiresAt(coupon.getExpiresAt())
                .isActive(coupon.getIsActive())
                .build();
    }
}
