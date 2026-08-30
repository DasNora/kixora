package com.sneakershop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sneakershop.model.Order;
import com.sneakershop.model.OrderItem;
import com.sneakershop.model.Product;
import com.sneakershop.util.DBConnection;

public class OrderDAO {

    public int saveOrder(int userId,
                         double totalAmount,
                         String paymentStatus,
                         String razorpayOrderId) {

        int orderId = 0;

        String sql ="INSERT INTO orders(user_id,total_amount,payment_status,order_status,razorpay_order_id) VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        	ps.setInt(1,userId);
        	ps.setDouble(2,totalAmount);
        	ps.setString(3,paymentStatus);
        	ps.setString(4,"Processing");
        	ps.setString(5,razorpayOrderId);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                orderId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderId;
    }
    public void saveOrderItem(int orderId,
            int productId,
            int quantity,
            double price) {

String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";

try (Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement(sql)) {

ps.setInt(1, orderId);
ps.setInt(2, productId);
ps.setInt(3, quantity);
ps.setDouble(4, price);

ps.executeUpdate();

} catch (Exception e) {
e.printStackTrace();
}
}
    public List<Order> getOrdersByUser(int userId) {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Order order = new Order();
                
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setRazorpayOrderId(rs.getString("razorpay_order_id"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setOrderStatus(rs.getString("order_status"));
                orders.add(order);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    public List<OrderItem> getOrderItems(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        String sql =
            "SELECT oi.*, p.name, p.image_url " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.id " +
            "WHERE oi.order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setImageUrl(rs.getString("image_url"));

                OrderItem item = new OrderItem();

                item.setItemId(rs.getInt("item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setProduct(product);

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
    public Order getOrderById(int orderId) {

        Order order = null;

        String sql = "SELECT * FROM orders WHERE order_id=?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                order = new Order();

                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setRazorpayOrderId(rs.getString("razorpay_order_id"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setOrderStatus(rs.getString("order_status"));
            }

        } catch(Exception e){

            e.printStackTrace();

        }

        return order;
    }
    
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.*, u.first_name, u.last_name " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "ORDER BY o.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));

                order.setCustomerName(
                    rs.getString("first_name") + " " + rs.getString("last_name"));

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    
    public boolean updateOrderStatus(int orderId, String status) {

        String sql = "UPDATE orders SET order_status=? WHERE order_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteOrder(int orderId, int userId) {

        String deleteItems = "DELETE FROM order_items WHERE order_id=?";
        String deleteOrder = "DELETE FROM orders WHERE order_id=? AND user_id=?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(deleteItems);
                 PreparedStatement ps2 = conn.prepareStatement(deleteOrder)) {

                ps1.setInt(1, orderId);
                ps1.executeUpdate();

                ps2.setInt(1, orderId);
                ps2.setInt(2, userId);

                int rows = ps2.executeUpdate();

                conn.commit();

                return rows > 0;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}