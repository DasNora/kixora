package com.sneakershop.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ChatRelayServlet is the AI Chatbot bridge between the JSP frontend
 * and the Spring Boot AI Chatbot Microservice running on port 8082.
 *
 * <p>Flow: Browser sends chat message + productId → this servlet builds
 * a JSON payload → forwards via POST to http://localhost:8082/api/v1/chat
 * (which calls Gemini/Ollama) → returns the AI reply JSON to the browser.</p>
 *
 * <p>Server-to-server communication — no CORS, no exposed AI port.
 * Java 8 compatible — HttpURLConnection with 2s timeout windows.</p>
 *
 * <p>Based on ReadMeAI blueprint: Section 2, Feature C</p>
 */
@WebServlet("/chat-gateway")
public class ChatRelayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userMessage = request.getParameter("msg");
        String productId = request.getParameter("productId");

        // Build JSON payload: {"message":"...","productId":"999"}
        String jsonPayload = String.format(
                "{\"message\":\"%s\",\"productId\":\"%s\"}",
                escapeJson(userMessage),
                escapeJson(productId)
        );

        // Forward to Spring Boot AI service
        String springBootAiUrl = "http://localhost:8082/api/v1/chat";
        StringBuilder aiReplyJson = new StringBuilder();

        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(springBootAiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(10000);   // AI responses may take longer

            // Send user message to Spring Boot
            os = conn.getOutputStream();
            byte[] input = jsonPayload.getBytes("UTF-8");
            os.write(input, 0, input.length);
            os.flush();
            int code = conn.getResponseCode();
            System.out.println("Response Code = " + code);
            // Read the AI reply JSON
            if (code == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    aiReplyJson.append(line);
                }
                System.out.println("Gateway received:");
                System.out.println(aiReplyJson.toString());
            }

        } catch (Exception e) {

            System.out.println("=========== CHAT GATEWAY ERROR ===========");
            e.printStackTrace();
            System.out.println("==========================================");

            aiReplyJson.append(
                "{\"reply\":\"Sorry, my AI core is currently offline. Please try again later.\"}"
            );
        }finally {
            closeQuietly(reader);
            closeQuietly(os);
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Ensure we have at least a fallback response
        if (aiReplyJson.length() == 0) {
            aiReplyJson.append("{\"reply\":\"I didn't receive a response. Please try again.\"}");
        }

        // Return the AI reply back to the browser
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(aiReplyJson.toString());
    }

    /**
     * Escapes a string for safe inclusion in a JSON value.
     */
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                // Suppress
            }
        }
    }
}