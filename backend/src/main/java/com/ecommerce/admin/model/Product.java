package com.ecommerce.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;
    
    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;
    
    private String category;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "category_name")
    private String categoryName;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(nullable = false)
    private String image = "";
    
    private String thumbnail;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    @Column(name = "stock_count", nullable = false)
    private Integer stockCount = 0;
    
    @Column(name = "in_stock", nullable = false)
    private Boolean inStock = true;
    
    @Column(unique = true)
    private String sku;
    
    private Double rating = 0.0;
    
    private Integer reviews = 0;
    
    private String badge;
    
    @Column(name = "meta_description")
    private String metaDescription;
    
    @Column(name = "meta_keywords")
    private String metaKeywords;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore  // Ignore during direct JSON serialization
    private List<ProductImage> images = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductFeature> features = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductSize> sizes = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}