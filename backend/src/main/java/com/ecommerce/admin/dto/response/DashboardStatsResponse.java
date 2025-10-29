package com.ecommerce.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsResponse {
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalCustomers;
    private BigDecimal averageOrderValue;
    private Double revenueChange;
    private Double ordersChange;
    private Double customersChange;
}
