package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.Order;
import com.ecommerce.admin.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    
    @Query("SELECT o FROM Order o WHERE o.orderNumber LIKE %:search% OR o.user.fullName LIKE %:search%")
    Page<Order> searchOrders(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status AND (o.orderNumber LIKE %:search% OR o.user.fullName LIKE %:search%)")
    Page<Order> searchOrdersByStatus(@Param("status") OrderStatus status, @Param("search") String search, Pageable pageable);
    
    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC")
    List<Order> findRecentOrders(Pageable pageable);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.createdAt >= :startDate")
    BigDecimal getTotalRevenueSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :startDate")
    Long countOrdersSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(o) FROM Order o")
    Long countAllOrders();
}