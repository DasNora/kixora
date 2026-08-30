package com.sneakershop.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ChatbotApplication is the entry point for the Spring Boot
 * AI Chatbot Microservice.
 *
 * <p>Runs as a standalone JAR on port 8082.
 * Called by Tomcat's ChatRelayServlet via localhost HTTP.
 * In production, this would connect to Google Gemini API or Ollama
 * via Spring AI.</p>
 *
 * <p>Java 8 compatible — Spring Boot 2.7.18.</p>
 */
@SpringBootApplication
public class ChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
    }
}