package com.arktech.toppickr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String title;

    @Getter @Setter
    @Column(nullable = false)
    private String asin; // Amazon Standard Identification Number

    @Getter @Setter
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Getter @Setter
    @Column(precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Getter @Setter
    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @Getter @Setter
    @Column(nullable = false)
    private Integer reviewCount;

    @Getter @Setter
    @Column(nullable = false)
    private String category;

    @Getter @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Getter @Setter
    @Column(nullable = false)
    private String imageUrl;

    @Getter @Setter
    @Column(nullable = false)
    private String affiliateLink;

    @Getter @Setter
    @Column(nullable = false)
    private Boolean inStock = true;

    @Getter @Setter
    @Column(nullable = false)
    private Boolean isActive = true;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Product() {}

    public Product(String title, String asin, BigDecimal price, BigDecimal rating,
                   Integer reviewCount, String category, String imageUrl, String affiliateLink) {
        this.title = title;
        this.asin = asin;
        this.price = price;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.category = category;
        this.imageUrl = imageUrl;
        this.affiliateLink = affiliateLink;
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}