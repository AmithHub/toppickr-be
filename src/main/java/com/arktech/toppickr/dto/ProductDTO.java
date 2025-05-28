package com.arktech.toppickr.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {
	@Getter @Setter
	private Long id;

	@Getter @Setter
	private String title;

	@Getter @Setter
	private String asin;

	@Getter @Setter
	private BigDecimal price;

	@Getter @Setter
	private BigDecimal originalPrice;

	@Getter @Setter
	private BigDecimal rating;

	@Getter @Setter
	private Integer reviewCount;

	@Getter @Setter
	private String category;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private String imageUrl;

	@Getter @Setter
	private String affiliateLink;

	@Getter @Setter
	private Boolean inStock;

	@Getter @Setter
	private LocalDateTime updatedAt;

	// Constructors
	public ProductDTO() {
	}

	public ProductDTO(Long id, String title, String asin, BigDecimal price, BigDecimal originalPrice,
					  BigDecimal rating, Integer reviewCount, String category, String description,
					  String imageUrl, String affiliateLink, Boolean inStock, LocalDateTime updatedAt) {
		this.id = id;
		this.title = title;
		this.asin = asin;
		this.price = price;
		this.originalPrice = originalPrice;
		this.rating = rating;
		this.reviewCount = reviewCount;
		this.category = category;
		this.description = description;
		this.imageUrl = imageUrl;
		this.affiliateLink = affiliateLink;
		this.inStock = inStock;
		this.updatedAt = updatedAt;
	}
}
