package com.sneakershop.servlet;
import com.sneakershop.dao.CartDAO;
import com.google.gson.Gson;
import com.sneakershop.model.User;
import com.sneakershop.dao.ProductDAO;
import com.sneakershop.model.Product;
import  com.sneakershop.model.RatingSummary;
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
import java.util.List;

/**
 * ProductServlet handles all sneaker-related requests.
 *
 * <p>Three scenarios:
 * <ol>
 *   <li>GET /products          → fetch all 10 sneakers → Products.jsp gallery</li>
 *   <li>GET /products?id=5     → fetch sneaker #5     → ProductDetail.jsp</li>
 *   <li>POST /addProduct       → insert new sneaker   → AddProducts.jsp with message</li>
 * </ol>
 *
 * <p>Java 8 compatible — uses javax.servlet APIs and explicit types.</p>
 */
@WebServlet({"/products", "/addProduct"})
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final CartDAO cartDAO = new CartDAO();

    /**
     * Handles GET requests for /products and /addProduct.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/products".equals(path)) {
            handleViewProducts(request, response);
        } else if ("/addProduct".equals(path)) {
            request.getRequestDispatcher("AddProducts.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests — only /addProduct uses POST.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        handleAddProduct(request, response);
    }

    // ===== Private helper methods =====

    /**
     * Shows either ALL sneakers (gallery) or ONE sneaker (detail page)
     * depending on whether an "id" parameter is present in the URL.
     */
    private void handleViewProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            // Scenario: /products?id=5 → show ONE sneaker detail
            try {
                int id = Integer.parseInt(idParam);
                Product product = productDAO.getProductById(id);

                if (product != null) {
                    request.setAttribute("product", product);
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

            String category = request.getParameter("category");

            List<Product> productList;

            if (category != null && !category.trim().isEmpty()) {
                productList = productDAO.getProductsByCategory(category);
            } else {
                productList = productDAO.getAllProducts();
                for(Product product : productList){

                    RatingSummary summary =getRatingSummary(product.getId());

                    product.setAverageRating(
                            summary.getAverageRating());

                    product.setTotalReviews(
                            summary.getTotalReviews());

                }
            }

            request.setAttribute("productList", productList);

         // Get logged-in user
         User user = (User) request.getSession().getAttribute("user");

         if (user != null) {
             request.setAttribute("cartCount", cartDAO.getCartCount(user.getId()));
         } else {
             request.setAttribute("cartCount", 0);
         }

         request.getRequestDispatcher("Products.jsp").forward(request, response);
        }
    }

    /**
     * Processes the "Add Product" form submission from employees.
     */
    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        try {
            double price = Double.parseDouble(priceStr);

            Product product = new Product(name, category, description, price, imageUrl);
            boolean success = productDAO.addProduct(product);

            if (success) {
                request.setAttribute("success", "✅ Sneaker '" + name + "' added successfully!");
            } else {
                request.setAttribute("error", "Failed to add sneaker. Please try again.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid price (numbers only).");
        }

        request.getRequestDispatcher("AddProducts.jsp").forward(request, response);
    }
    private RatingSummary getRatingSummary(int productId) {

        try {

            URL url = new URL(
                    "http://localhost:8081/api/v1/ratings/" + productId);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));

            StringBuilder json = new StringBuilder();

            String line;

            while((line = reader.readLine()) != null){

                json.append(line);

            }

            reader.close();

            Gson gson = new Gson();

            return gson.fromJson(
                    json.toString(),
                    RatingSummary.class);

        }

        catch(Exception e){

            return new RatingSummary();

        }

    }
}