package com.arktech.toppickr.service;

import com.arktech.toppickr.dto.ProductDTO;
import com.arktech.toppickr.entity.Product;
import com.arktech.toppickr.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private static final BigDecimal MIN_RATING = new BigDecimal("4.0");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AmazonAPIService amazonAPIService;

    /**
     * Get top-rated products with pagination
     */
    public Page<ProductDTO> getTopRatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findTopRatedProducts(MIN_RATING, pageable);
        return products.map(this::convertToDTO);
    }

    /**
     * Get products by category
     */
    public Page<ProductDTO> getProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByCategoryAndRating(category, MIN_RATING, pageable);
        return products.map(this::convertToDTO);
    }

    /**
     * Search products
     */
    public Page<ProductDTO> searchProducts(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.searchProducts(searchTerm, MIN_RATING, pageable);
        return products.map(this::convertToDTO);
    }

    /**
     * Get most reviewed products
     */
    public Page<ProductDTO> getMostReviewedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findMostReviewedProducts(MIN_RATING, pageable);
        return products.map(this::convertToDTO);
    }

    /**
     * Get all categories
     */
    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    /**
     * Get product by ID
     */
    public Optional<ProductDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO);
    }

    /**
     * Update product prices from Amazon API
     */
    public void updateProductPrices() {
        logger.info("Starting product price update...");

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            try {
                AmazonAPIService.ProductPriceData priceData = amazonAPIService.fetchProductPrice(product.getAsin());
                if (priceData != null) {
                    product.setPrice(priceData.getPrice());
                    product.setOriginalPrice(priceData.getOriginalPrice());
                    product.setInStock(priceData.getInStock());
                    product.setRating(priceData.getRating());
                    product.setReviewCount(priceData.getReviewCount());
                    productRepository.save(product);
                }

                // Rate limiting - wait between API calls
                Thread.sleep(1000);

            } catch (Exception e) {
                logger.error("Error updating product price for ASIN: {}", product.getAsin(), e);
            }
        }

        logger.info("Product price update completed");
    }

    /**
     * Add new product
     */
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setAffiliateLink(amazonAPIService.generateAffiliateLink(product.getAsin()));
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    /**
     * Update existing product
     */
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            updateProductFromDTO(product, productDTO);
            Product savedProduct = productRepository.save(product);
            return Optional.of(convertToDTO(savedProduct));
        }
        return Optional.empty();
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setIsActive(false);
            productRepository.save(product.get());
            return true;
        }
        return false;
    }

    /**
     * Convert Product entity to DTO
     */
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getAsin(),
                product.getPrice(),
                product.getOriginalPrice(),
                product.getRating(),
                product.getReviewCount(),
                product.getCategory(),
                product.getDescription(),
                product.getImageUrl(),
                product.getAffiliateLink(),
                product.getInStock(),
                product.getUpdatedAt()
        );
    }

    /**
     * Convert DTO to Product entity
     */
    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setAsin(dto.getAsin());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setRating(dto.getRating());
        product.setReviewCount(dto.getReviewCount());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setInStock(dto.getInStock());
        return product;
    }

    /**
     * Update Product entity from DTO
     */
    private void updateProductFromDTO(Product product, ProductDTO dto) {
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setRating(dto.getRating());
        product.setReviewCount(dto.getReviewCount());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setInStock(dto.getInStock());
    }
}
