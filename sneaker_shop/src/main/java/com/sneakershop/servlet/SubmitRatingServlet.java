package com.sneakershop.servlet;
import javax.servlet.http.HttpSession;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * SubmitRatingServlet is the relay bridge for rating submissions.
 *
 * <p>Flow: Browser POSTs rating to /submit-rating → this servlet builds
 * a JSON payload → forwards it via POST to Spring Boot Rating Service
 * (port 8081) → returns success/error JSON back to the browser.</p>
 *
 * <p>This is server-to-server communication — no CORS, no exposed ports.
 * Java 8 compatible — uses HttpURLConnection with manual resource cleanup.</p>
 *
 * <p>Based on ReadMeAI blueprint: Section 2, Feature B</p>
 */
@WebServlet("/submit-rating")
public class SubmitRatingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String productId = request.getParameter("productId");
    	String rating = request.getParameter("rating");
    	String review = request.getParameter("review");

    	if(review == null){
    	    review = "";
    	}

    	review = review.trim();
    	
    	HttpSession session = request.getSession(false);

    	if (session == null) {
    	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	    return;
    	}
    	

    	User user = (User) session.getAttribute("user");

    	if (user == null) {
    	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	    return;
    	}

        // Build JSON payload manually (no Gson/Jackson dependency needed)
    	String jsonPayload = String.format(
    	        "{"
    	        + "\"productId\":%s,"
    	        + "\"userId\":%d,"
    	        + "\"rating\":%s,"
    	        + "\"review\":\"%s\""
    	        + "}",
    	        productId,
    	        user.getId(),
    	        rating,
    	        escapeJson(review)
    	);

        // Forward to Spring Boot microservice
        String springBootUrl = "http://localhost:8081/api/v1/ratings";
        int responseCode = 500;

        HttpURLConnection conn = null;
        OutputStream os = null;
        System.out.println("Rating becomes------");

        try {
        	System.out.println("========== JSON ==========");
        	System.out.println(jsonPayload);
        	System.out.println("==========================");
            URL url = new URL(springBootUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);                      // Needed to send a request body
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            System.out.println("Rating becomes------");
            // Write the JSON payload to the output stream
            os = conn.getOutputStream();
            byte[] input = jsonPayload.getBytes("UTF-8");
            os.write(input, 0, input.length);
            os.flush();

            responseCode = conn.getResponseCode();
            System.out.println("Spring Boot Response Code = " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
            responseCode = 500;
        } finally {
            closeQuietly(os);
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Return clean JSON status back to the browser
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (responseCode == 200 || responseCode == 201) {
            out.print("{\"status\":\"success\"}");
        } else {
            out.print("{\"status\":\"error\"}");
        }
        out.flush();
    }

    /**
     * Escapes a string for safe inclusion in a JSON value.
     * Handles backslashes and double-quotes.
     */
    private String escapeJson(String value) {

        if(value == null){
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

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