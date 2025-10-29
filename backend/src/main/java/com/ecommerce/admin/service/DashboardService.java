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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime startOfLastMonth = startOfMonth.minusMonths(1);
        
        // Current period stats
        BigDecimal totalRevenue = orderRepository.getTotalRevenueSince(startOfMonth);
        if (totalRevenue == null) totalRevenue = BigDecimal.ZERO;
        
        Long totalOrders = orderRepository.countOrdersSince(startOfMonth);
        if (totalOrders == null) totalOrders = 0L;
        
        Long totalCustomers = userRepository.countCustomers();
        if (totalCustomers == null) totalCustomers = 0L;
        
        // Calculate average order value
        BigDecimal avgOrderValue = BigDecimal.ZERO;
        if (totalOrders > 0) {
            avgOrderValue = totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP);
        }
        
        // Previous period stats for comparison
        BigDecimal lastMonthRevenue = orderRepository.getTotalRevenueSince(startOfLastMonth);
        if (lastMonthRevenue == null) lastMonthRevenue = BigDecimal.ZERO;
        
        Long lastMonthOrders = orderRepository.countOrdersSince(startOfLastMonth);
        if (lastMonthOrders == null) lastMonthOrders = 0L;
        
        // Calculate changes
        double revenueChange = calculatePercentageChange(lastMonthRevenue, totalRevenue);
        double ordersChange = calculatePercentageChange(lastMonthOrders.doubleValue(), totalOrders.doubleValue());
        double customersChange = 0.0; // Can be calculated if we track new customers per period
        
        return DashboardStatsResponse.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .totalCustomers(totalCustomers)
                .averageOrderValue(avgOrderValue)
                .revenueChange(revenueChange)
                .ordersChange(ordersChange)
                .customersChange(customersChange)
                .build();
    }
    
    public List<SalesDataPoint> getSalesData(int days) {
        List<SalesDataPoint> salesData = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        // Generate daily data points for the time series chart
        for (int i = days - 1; i >= 0; i--) {
            LocalDateTime dayStart = now.minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime dayEnd = dayStart.plusDays(1).minusSeconds(1);
            
            BigDecimal dayRevenue = orderRepository.getTotalRevenueSince(dayStart);
            Long dayOrders = orderRepository.countOrdersSince(dayStart);
            
            // Subtract previous day's totals to get only this day's data
            if (i < days - 1) {
                LocalDateTime prevDayStart = now.minusDays(i + 1).withHour(0).withMinute(0).withSecond(0);
                BigDecimal prevRevenue = orderRepository.getTotalRevenueSince(prevDayStart);
                Long prevOrders = orderRepository.countOrdersSince(prevDayStart);
                
                dayRevenue = (dayRevenue != null ? dayRevenue : BigDecimal.ZERO)
                        .subtract(prevRevenue != null ? prevRevenue : BigDecimal.ZERO);
                dayOrders = (dayOrders != null ? dayOrders : 0L) 
                        - (prevOrders != null ? prevOrders : 0L);
            }
            
            salesData.add(new SalesDataPoint(
                    dayStart.toLocalDate().toString(),
                    dayRevenue != null ? dayRevenue : BigDecimal.ZERO,
                    dayOrders != null ? dayOrders : 0L
            ));
        }
        
        return salesData;
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
