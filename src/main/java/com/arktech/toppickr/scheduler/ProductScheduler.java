package com.arktech.toppickr.scheduler;

import com.arktech.toppickr.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ProductScheduler.class);

    @Autowired
    private ProductService productService;

    /**
     * Update product prices every 6 hours
     */
    @Scheduled(fixedRate = 21600000) // 6 hours in milliseconds
    public void updateProductPrices() {
        logger.info("Scheduled price update started");
        productService.updateProductPrices();
        logger.info("Scheduled price update completed");
    }
}

