package com.sneakershop.dao;

import com.sneakershop.model.Product;
import com.sneakershop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO handles all database operations for the 'products' table.
 *
 * <p>Three operations: fetch all sneakers for the gallery, fetch one sneaker
 * by ID for the detail page, and insert a new sneaker (employee-only).</p>
 *
 * <p>Java 8 compatible — manual resource cleanup with try-finally.</p>
 */
public class ProductDAO {

    /**
     * Fetches all products from the database, ordered by ID.
     * Used by Products.jsp to build the JSTL gallery.
     *
     * @return a List of all Product objects (empty list if none)
     */
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        String sql = "SELECT * FROM products ORDER BY id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }

        return productList;
    }

    /**
     * Fetches a single product by its ID.
     * Used by ProductDetail.jsp to show one sneaker in full detail.
     *
     * @param id  the product ID to look up
     * @return the Product object, or null if not found
     */
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }

        return null; // Product not found or error occurred
    }

    /**
     * Inserts a new product into the database (employee-only operation).
     *
     * @param product  the Product to insert (id will be auto-generated)
     * @return true if the insert succeeded
     */
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, description, price, image_url) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getImageUrl());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }
    
    public boolean deleteProduct(int id){

        String sql="DELETE FROM products WHERE id=?";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setInt(1,id);

            return ps.executeUpdate()>0;

        }catch(Exception e){

            e.printStackTrace();

        }

        return false;
    }
    
    public boolean updateProduct(Product product) {

        String sql = "UPDATE products SET name=?, category=?, description=?, price=?, image_url=? WHERE id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getImageUrl());
            stmt.setInt(6, product.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            closeQuietly(stmt);
            closeQuietly(conn);

        }

        return false;
    }
    /**
     * Fetches products by category.
     *
     * @param category the category name (Running, Lifestyle, Retro, etc.)
     * @return List of products in that category
     */
    public List<Product> getProductsByCategory(String category) {

        List<Product> productList = new ArrayList<Product>();

        String sql = "SELECT * FROM products WHERE category = ? ORDER BY id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, category);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Product product = new Product();

                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));

                productList.add(product);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);

        }

        return productList;
    }

    // ===== Helper: Close resources without throwing exceptions =====

    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                // Suppress — nothing we can do at this point
            }
        }
    }
    
}