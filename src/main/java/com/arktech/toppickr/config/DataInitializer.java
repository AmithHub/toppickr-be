package com.arktech.toppickr.config;

import com.arktech.toppickr.entity.Product;
import com.arktech.toppickr.repository.ProductRepository;
import com.arktech.toppickr.service.AmazonAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AmazonAPIService amazonAPIService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize with sample data if database is empty
        if (productRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        // Sample products for demonstration
        Product[] products = {
                new Product(
                        "Apple AirPods Pro (2nd Generation)",
                        "B0BDHWDR12",
                        new BigDecimal("249.99"),
                        new BigDecimal("4.8"),
                        45234,
                        "Electronics",
                        "https://m.media-amazon.com/images/W/MEDIAX_1215821-T2/images/I/71Q9odukIZL._SL1500_.jpg",
                        amazonAPIService.generateAffiliateLink("B0BDHWDR12")
                ),
                new Product(
                        "Nike Air Max 270 Running Shoes",
                        "B07XJBQZPX",
                        new BigDecimal("89.99"),
                        new BigDecimal("4.6"),
                        12890,
                        "Fashion",
                        "https://m.media-amazon.com/images/W/MEDIAX_1215821-T2/images/I/613fjQtsd2L._SY695_.jpg",
                        amazonAPIService.generateAffiliateLink("B07XJBQZPX")
                ),
                new Product(
                        "Instant Pot Duo 7-in-1 Electric Pressure Cooker",
                        "B00FLYWNYQ",
                        new BigDecimal("79.95"),
                        new BigDecimal("4.7"),
                        89456,
                        "Home & Kitchen",
                        "https://m.media-amazon.com/images/W/MEDIAX_1215821-T2/images/I/710KoJMG2lL._SL1500_.jpg",
                        amazonAPIService.generateAffiliateLink("B00FLYWNYQ")
                ),
                new Product(
                        "The Midnight Library - Kindle Edition",
                        "B08FF8Z1WD",
                        new BigDecimal("9.99"),
                        new BigDecimal("4.5"),
                        34567,
                        "Books",
                        "https://m.media-amazon.com/images/I/81eB+7+CkUL.jpg",
                        amazonAPIService.generateAffiliateLink("B08FF8Z1WD")
                ),
                new Product(
                        "LEGO Creator 3-in-1 Deep Sea Creatures",
                        "B08HWJKZ9N",
                        new BigDecimal("55.99"),
                        new BigDecimal("4.9"),
                        5678,
                        "Toys & Games",
                        "https://m.media-amazon.com/images/I/71sGZ9y6vXL._AC_SL1500_.jpg",
                        amazonAPIService.generateAffiliateLink("B08HWJKZ9N")
                ),
                new Product(
                        "Fitbit Charge 5 Advanced Fitness Tracker",
                        "B09932QY5D",
                        new BigDecimal("149.95"),
                        new BigDecimal("4.4"),
                        23456,
                        "Sports & Outdoors",
                        "https://m.media-amazon.com/images/I/71smqRr0pmL._AC_SL1500_.jpg",
                        amazonAPIService.generateAffiliateLink("B09932QY5D")
                )
        };

        for (Product product : products) {
            product.setOriginalPrice(product.getPrice().add(new BigDecimal("20.00")));
            product.setDescription("High-quality product with excellent reviews and ratings.");
            productRepository.save(product);
        }
    }
}

