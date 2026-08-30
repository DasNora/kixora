package com.sneakershop.servlet;
import com.sneakershop.dao.CartDAO;
import com.sneakershop.model.User;
import javax.servlet.http.HttpSession;
import com.sneakershop.dao.ProductDAO;
import com.sneakershop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ProductDetailServlet bridges the Tomcat JSP frontend to the Spring Boot
 * Rating Microservice running on port 8081.
 *
 * <p>Flow: GET /product-detail?id=5 → fetches product from local ProductDAO →
 * calls http://localhost:8081/api/v1/ratings/5 (with 2s timeout) →
 * passes both product and ratingDataJson to ProductDetail.jsp for rendering.</p>
 *
 * <p>This is server-to-server communication — no CORS headers needed.
 * Java 8 compatible — uses HttpURLConnection with manual resource cleanup.</p>
 *
 * <p>Based on ReadMeAI blueprint: Section 2, Feature A</p>
 */
@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("id");

        // 1. Fetch the sneaker from our own MySQL database
        if (productId != null && !productId.isEmpty()) {
            try {
                int id = Integer.parseInt(productId);
                Product product = productDAO.getProductById(id);

                if (product != null) {
                	request.setAttribute("product", product);

                	// Send cart count to JSP
                	HttpSession session = request.getSession(false);

                	if (session != null) {

                	    User user = (User) session.getAttribute("user");

                	    if (user != null) {

                	        request.setAttribute(
                	                "cartCount",
                	                cartDAO.getCartCount(user.getId()));

                	    }
                	}

                	// Fetch ratings
                	String ratingJson = fetchRatings(productId);

                	request.setAttribute("ratingDataJson", ratingJson);
                	String reviewsJson = fetchReviews(productId);

                	request.setAttribute("reviewsJson",reviewsJson);
                	System.out.println("Rating JSON  : " + ratingJson);
                	System.out.println("Reviews JSON : " + reviewsJson);
                	request.getRequestDispatcher("ProductDetail.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Sneaker #" + id + " not found.");
                    request.getRequestDispatcher("Products.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid product ID.");
                request.getRequestDispatcher("Products.jsp").forward(request, response);
            }
        } else {
            // No ID provided — redirect to product gallery
            response.sendRedirect("products");
        }
    }

    /**
     * Calls the Spring Boot Rating Microservice to fetch rating data for a product.
     *
     * <p>Uses HttpURLConnection with 2-second connect and read timeouts.
     * If the microservice is offline or times out, a fallback JSON
     * with zero values is returned so the page doesn't break.</p>
     *
     * @param productId the product ID to look up
     * @return JSON string like {"averageRating":4.7,"totalReviews":128}
     */
    private String fetchRatings(String productId) {
        String springBootUrl = "http://localhost:8081/api/v1/ratings/" + productId;
        StringBuilder jsonResponse = new StringBuilder();

        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(springBootUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(2000);   // 2-second timeout — prevents freezing
            conn.setReadTimeout(2000);

            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResponse.append(line);
                }
            }

        } catch (Exception e) {
            // Spring Boot service is down — use fallback values
            jsonResponse.append("{\"averageRating\": 0.0, \"totalReviews\": 0}");
        } finally {
            closeQuietly(reader);
            if (conn != null) {
                conn.disconnect();
            }
        }

        // If no data was read (empty response), provide fallback
        if (jsonResponse.length() == 0) {
            jsonResponse.append("{\"averageRating\": 0.0, \"totalReviews\": 0}");
        }

        return jsonResponse.toString();
    }
    private String fetchReviews(String productId) {

        String springBootUrl =
                "http://localhost:8081/api/v1/ratings/"
                + productId
                + "/reviews";

        StringBuilder jsonResponse = new StringBuilder();

        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(springBootUrl);

            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept","application/json");

            if(conn.getResponseCode()==200){

                reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        conn.getInputStream(),
                                        "UTF-8"));

                String line;

                while((line=reader.readLine())!=null){

                    jsonResponse.append(line);

                }

            }

        }catch(Exception e){

            jsonResponse.append("[]");

        }finally{

            closeQuietly(reader);

            if(conn!=null){

                conn.disconnect();

            }

        }

        return jsonResponse.toString();

    }

    /**
     * Safely closes a resource, ignoring any exceptions.
     */
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