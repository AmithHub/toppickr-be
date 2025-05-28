package com.arktech.toppickr.repository;

import com.arktech.toppickr.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// Find active products with rating above threshold
	@Query("SELECT p FROM Product p WHERE p.isActive = true AND p.rating >= :minRating ORDER BY p.rating DESC, p.reviewCount DESC")
	Page<Product> findTopRatedProducts(@Param("minRating") BigDecimal minRating, Pageable pageable);

	// Find products by category
	@Query("SELECT p FROM Product p WHERE p.isActive = true AND p.category = :category AND p.rating >= :minRating ORDER BY p.rating DESC, p.reviewCount DESC")
	Page<Product> findByCategoryAndRating(@Param("category") String category, @Param("minRating") BigDecimal minRating, Pageable pageable);

	// Search products by title or description
	@Query("SELECT p FROM Product p WHERE p.isActive = true AND p.rating >= :minRating AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) ORDER BY p.rating DESC, p.reviewCount DESC")
	Page<Product> searchProducts(@Param("searchTerm") String searchTerm, @Param("minRating") BigDecimal minRating, Pageable pageable);

	// Find all distinct categories
	@Query("SELECT DISTINCT p.category FROM Product p WHERE p.isActive = true ORDER BY p.category")
	List<String> findDistinctCategories();

	// Find by ASIN
	Product findByAsin(String asin);

	// Find most reviewed products
	@Query("SELECT p FROM Product p WHERE p.isActive = true AND p.rating >= :minRating ORDER BY p.reviewCount DESC, p.rating DESC")
	Page<Product> findMostReviewedProducts(@Param("minRating") BigDecimal minRating, Pageable pageable);
}
