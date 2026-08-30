package com.sneakershop.chatbot.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.sneakershop.chatbot.entity.Product;
import com.sneakershop.chatbot.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
	@Autowired
	private ProductRepository productRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askGemini(String question) {

        try {

            Client client = Client.builder()
                    .apiKey(apiKey)
                    .build();
            List<Product> products =
                    productRepository.findAll();

            StringBuilder catalog =
                    new StringBuilder();

            for(Product p : products){

                catalog.append("Product: ")
                       .append(p.getName())
                       .append("\n");

                catalog.append("Category: ")
                       .append(p.getCategory())
                       .append("\n");

                catalog.append("Price: ₹")
                       .append(p.getPrice())
                       .append("\n");

                catalog.append("Description: ")
                       .append(p.getDescription())
                       .append("\n\n");

            }
            String prompt =
            		"You are Kix, the AI shopping assistant for Kixora, an online sneaker store.\n\n"

            		+ "The products currently available are:\n\n"

            		+ catalog.toString()

            		+ "Rules:\n"
            		+ "- Recommend ONLY products from the catalog above.\n"
            		+ "- Never mention or recommend brands or products that are not in the catalog.\n"
            		+ "- If the customer asks for the best shoe for running, casual wear, training, etc., choose from the catalog and explain why.\n"
            		+ "- If the customer asks about sizing, shoe care, payments, shipping or returns, answer normally.\n"
            		+ "- If you don't know the answer, politely say so.\n"
            		+ "- Keep replies friendly and under 80 words.\n"
            		+"- If a customer asks about a shoe that is not sold by Kixora,olitely explain that it isn't available in our store and recommend the closest product from the catalog instead.\n"
            		+ "Customer Question:\n"

            		+ question;

            GenerateContentResponse response =client.models.generateContent("gemini-3.5-flash-lite",prompt,null);

            return response.text();

        } catch (Exception e) {

            System.out.println("============== GEMINI ERROR ==============");
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("==========================================");

            return "⚠ Sorry! I'm having trouble reaching Kix right now. Please try again in a moment.";
        }

    }

}