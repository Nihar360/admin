package com.ecommerce.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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
    
    // Use @ManyToOne relationship instead of separate fields
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private String image = "";
    
    private String thumbnail;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    @Column(name = "stock_count", nullable = false)
    @Builder.Default
    private Integer stockCount = 0;
    
    @Column(name = "in_stock", nullable = false)
    @Builder.Default
    private Boolean inStock = true;
    
    @Column(unique = true)
    private String sku;
    
    @Builder.Default
    private Double rating = 0.0;
    
    @Builder.Default
    private Integer reviews = 0;
    
    private String badge;
    
    @Column(name = "meta_description")
    private String metaDescription;
    
    @Column(name = "meta_keywords")
    private String metaKeywords;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ProductFeature> features = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ProductSize> sizes = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Helper method to ensure backward compatibility
    public Boolean getInStock() {
        return this.inStock;
    }
}