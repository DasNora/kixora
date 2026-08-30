package com.sneakershop.ratings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RatingsApplication is the entry point for the Spring Boot
 * Ratings Microservice.
 *
 * <p>Runs as a standalone JAR on port 8081.
 * Called by Tomcat's ProductDetailServlet and SubmitRatingServlet
 * via localhost HTTP on the same machine.</p>
 *
 * <p>Java 8 compatible — Spring Boot 2.7.18 (last version supporting Java 8).</p>
 */
@SpringBootApplication
public class RatingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingsApplication.class, args);
    }
}