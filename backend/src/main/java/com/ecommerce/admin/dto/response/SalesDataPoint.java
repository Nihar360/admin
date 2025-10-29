package com.ecommerce.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDataPoint {
    private String date;
    private BigDecimal revenue;
    private Long orders;
}
