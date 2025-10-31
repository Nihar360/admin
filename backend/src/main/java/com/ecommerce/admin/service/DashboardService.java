package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.response.DashboardStatsResponse;
import com.ecommerce.admin.dto.response.SalesDataPoint;
import com.ecommerce.admin.repository.OrderRepository;
import com.ecommerce.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    
    public DashboardStatsResponse getDashboardStats() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime startOfLastMonth = startOfMonth.minusMonths(1);
            
            // Current period stats
            BigDecimal totalRevenue = BigDecimal.ZERO;
            Long totalOrders = 0L;
            Long totalCustomers = 0L;
            
            try {
                totalRevenue = orderRepository.getTotalRevenueSince(startOfMonth);
                if (totalRevenue == null) totalRevenue = BigDecimal.ZERO;
            } catch (Exception e) {
                System.err.println("Failed to get total revenue: " + e.getMessage());
            }
            
            try {
                totalOrders = orderRepository.countOrdersSince(startOfMonth);
                if (totalOrders == null) totalOrders = 0L;
            } catch (Exception e) {
                System.err.println("Failed to count orders: " + e.getMessage());
            }
            
            try {
                totalCustomers = userRepository.count();
            } catch (Exception e) {
                System.err.println("Failed to count customers: " + e.getMessage());
            }
            
            // Calculate average order value
            BigDecimal avgOrderValue = BigDecimal.ZERO;
            if (totalOrders > 0) {
                avgOrderValue = totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP);
            }
            
            // Previous period stats for comparison
            BigDecimal lastMonthRevenue = BigDecimal.ZERO;
            Long lastMonthOrders = 0L;
            
            try {
                lastMonthRevenue = orderRepository.getTotalRevenueSince(startOfLastMonth);
                if (lastMonthRevenue == null) lastMonthRevenue = BigDecimal.ZERO;
            } catch (Exception e) {
                System.err.println("Failed to get last month revenue: " + e.getMessage());
            }
            
            try {
                lastMonthOrders = orderRepository.countOrdersSince(startOfLastMonth);
                if (lastMonthOrders == null) lastMonthOrders = 0L;
            } catch (Exception e) {
                System.err.println("Failed to count last month orders: " + e.getMessage());
            }
            
            // Calculate changes
            double revenueChange = calculatePercentageChange(lastMonthRevenue, totalRevenue);
            double ordersChange = calculatePercentageChange(lastMonthOrders.doubleValue(), totalOrders.doubleValue());
            double customersChange = 0.0;
            
            return DashboardStatsResponse.builder()
                    .totalRevenue(totalRevenue)
                    .totalOrders(totalOrders)
                    .totalCustomers(totalCustomers)
                    .averageOrderValue(avgOrderValue)
                    .revenueChange(revenueChange)
                    .ordersChange(ordersChange)
                    .customersChange(customersChange)
                    .build();
        } catch (Exception e) {
            System.err.println("Error in getDashboardStats: " + e.getMessage());
            e.printStackTrace();
            
            // Return zero stats instead of failing
            return DashboardStatsResponse.builder()
                    .totalRevenue(BigDecimal.ZERO)
                    .totalOrders(0L)
                    .totalCustomers(0L)
                    .averageOrderValue(BigDecimal.ZERO)
                    .revenueChange(0.0)
                    .ordersChange(0.0)
                    .customersChange(0.0)
                    .build();
        }
    }
    
    public List<SalesDataPoint> getSalesData(int days) {
        try {
            List<SalesDataPoint> salesData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            
            for (int i = days - 1; i >= 0; i--) {
                LocalDateTime dayStart = now.minusDays(i).withHour(0).withMinute(0).withSecond(0);
                
                BigDecimal dayRevenue = BigDecimal.ZERO;
                Long dayOrders = 0L;
                
                try {
                    dayRevenue = orderRepository.getTotalRevenueSince(dayStart);
                    dayOrders = orderRepository.countOrdersSince(dayStart);
                    
                    if (dayRevenue == null) dayRevenue = BigDecimal.ZERO;
                    if (dayOrders == null) dayOrders = 0L;
                } catch (Exception e) {
                    System.err.println("Failed to get sales data for day " + i + ": " + e.getMessage());
                }
                
                salesData.add(new SalesDataPoint(
                        dayStart.toLocalDate().toString(),
                        dayRevenue,
                        dayOrders
                ));
            }
            
            return salesData;
        } catch (Exception e) {
            System.err.println("Error in getSalesData: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private double calculatePercentageChange(BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue.compareTo(BigDecimal.ZERO) == 0) {
            return newValue.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        }
        BigDecimal change = newValue.subtract(oldValue);
        return change.divide(oldValue, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
    
    private double calculatePercentageChange(double oldValue, double newValue) {
        if (oldValue == 0) {
            return newValue > 0 ? 100.0 : 0.0;
        }
        return ((newValue - oldValue) / oldValue) * 100;
    }
}