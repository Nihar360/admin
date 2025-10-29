package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByInStock(Boolean inStock);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:search% OR p.description LIKE %:search%")
    List<Product> searchProducts(@Param("search") String search);
    
    @Query("SELECT p FROM Product p WHERE p.category = :category AND (p.name LIKE %:search% OR p.description LIKE %:search%)")
    List<Product> searchProductsByCategory(@Param("category") String category, @Param("search") String search);
    
    @Query("SELECT p FROM Product p WHERE p.stockCount < 10 AND p.inStock = true")
    List<Product> findLowStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.stockCount = 0 OR p.inStock = false")
    List<Product> findOutOfStockProducts();
}
