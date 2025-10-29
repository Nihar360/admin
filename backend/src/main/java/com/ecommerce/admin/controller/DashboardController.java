package com.ecommerce.admin.controller;

import com.ecommerce.admin.dto.response.ApiResponse;
import com.ecommerce.admin.dto.response.DashboardStatsResponse;
import com.ecommerce.admin.dto.response.SalesDataPoint;
import com.ecommerce.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ApiResponse<DashboardStatsResponse> getDashboardStats() {
        DashboardStatsResponse stats = dashboardService.getDashboardStats();
        return ApiResponse.success(stats);
    }
    
    @GetMapping("/sales")
    public ApiResponse<List<SalesDataPoint>> getSalesData(@RequestParam(defaultValue = "30") int days) {
        List<SalesDataPoint> salesData = dashboardService.getSalesData(days);
        return ApiResponse.success(salesData);
    }
}
