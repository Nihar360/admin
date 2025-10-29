package com.ecommerce.admin.service;

import com.ecommerce.admin.dto.request.ProductCreateRequest;
import com.ecommerce.admin.dto.response.ProductResponse;
import com.ecommerce.admin.model.Product;
import com.ecommerce.admin.model.ProductImage;
import com.ecommerce.admin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<ProductResponse> getAllProducts(String category, String search, Boolean inStock) {
        List<Product> products;
        
        if (category != null && !category.equals("all") && search != null && !search.isEmpty()) {
            products = productRepository.searchProductsByCategory(category, search);
        } else if (category != null && !category.equals("all")) {
            products = productRepository.findByCategory(category);
        } else if (search != null && !search.isEmpty()) {
            products = productRepository.searchProducts(search);
        } else if (inStock != null) {
            products = productRepository.findByInStock(inStock);
        } else {
            products = productRepository.findAll();
        }
        
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }
    
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToProductResponse(product);
    }
    
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStock());
        product.setStockCount(request.getStock());
        product.setInStock(request.getStock() > 0);
        product.setSku(request.getSku());
        product.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        product.setImage(request.getImages() != null && !request.getImages().isEmpty() 
                ? request.getImages().get(0) : "");
        
        Product savedProduct = productRepository.save(product);
        return convertToProductResponse(savedProduct);
    }
    
    @Transactional
    public ProductResponse updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStock());
        product.setStockCount(request.getStock());
        product.setInStock(request.getStock() > 0);
        
        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }
        if (request.getIsActive() != null) {
            product.setIsActive(request.getIsActive());
        }
        
        Product updatedProduct = productRepository.save(product);
        return convertToProductResponse(updatedProduct);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    private ProductResponse convertToProductResponse(Product product) {
        List<String> images = new ArrayList<>();
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            images = product.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
        } else if (product.getImage() != null) {
            images.add(product.getImage());
        }
        
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stock(product.getStockCount())
                .sku(product.getSku())
                .images(images)
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
