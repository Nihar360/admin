package com.ecommerce.admin.controller;

import com.ecommerce.admin.dto.request.CouponCreateRequest;
import com.ecommerce.admin.dto.response.ApiResponse;
import com.ecommerce.admin.dto.response.CouponResponse;
import com.ecommerce.admin.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CouponController {
    
    private final CouponService couponService;
    
    @GetMapping
    public ApiResponse<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> coupons = couponService.getAllCoupons();
        return ApiResponse.success(coupons);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<CouponResponse> getCouponById(@PathVariable Long id) {
        CouponResponse coupon = couponService.getCouponById(id);
        return ApiResponse.success(coupon);
    }
    
    @PostMapping
    public ApiResponse<CouponResponse> createCoupon(@Valid @RequestBody CouponCreateRequest request) {
        CouponResponse coupon = couponService.createCoupon(request);
        return ApiResponse.success("Coupon created successfully", coupon);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<CouponResponse> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponCreateRequest request) {
        CouponResponse coupon = couponService.updateCoupon(id, request);
        return ApiResponse.success("Coupon updated successfully", coupon);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ApiResponse.success("Coupon deleted successfully", null);
    }
}
