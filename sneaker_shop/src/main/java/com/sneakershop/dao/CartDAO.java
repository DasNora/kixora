package com.sneakershop.dao;

import com.sneakershop.model.Cart;
import com.sneakershop.model.Product;
import com.sneakershop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CartDAO {
	
	private Cart getCartItem(int userId, int productId) {

	    String sql = "SELECT * FROM cart WHERE user_id=? AND product_id=?";

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {

	        conn = DBConnection.getConnection();

	        stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, userId);
	        stmt.setInt(2, productId);

	        rs = stmt.executeQuery();

	        if(rs.next()){

	            Cart cart = new Cart();

	            cart.setCartId(rs.getInt("cart_id"));
	            cart.setUserId(rs.getInt("user_id"));
	            cart.setProductId(rs.getInt("product_id"));
	            cart.setQuantity(rs.getInt("quantity"));

	            return cart;
	        }

	    } catch(SQLException e){

	        e.printStackTrace();

	    } finally{

	        closeQuietly(rs);
	        closeQuietly(stmt);
	        closeQuietly(conn);

	    }

	    return null;
	}
	public boolean addToCart(int userId, int productId){

	    Cart existing = getCartItem(userId, productId);

	    if(existing != null){

	        return increaseQuantity(existing.getCartId());

	    }

	    String sql =
	            "INSERT INTO cart(user_id,product_id,quantity) VALUES(?,?,1)";

	    Connection conn=null;
	    PreparedStatement stmt=null;

	    try{

	        conn=DBConnection.getConnection();

	        stmt=conn.prepareStatement(sql);

	        stmt.setInt(1,userId);
	        stmt.setInt(2,productId);

	        return stmt.executeUpdate()>0;

	    }catch(SQLException e){

	        e.printStackTrace();

	    }finally{

	        closeQuietly(stmt);
	        closeQuietly(conn);

	    }

	    return false;
	}
	public double getCartTotal(int userId) {

	    String sql =
	        "SELECT SUM(c.quantity * p.price) " +
	        "FROM cart c " +
	        "JOIN products p ON c.product_id = p.id " +
	        "WHERE c.user_id = ?";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, userId);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {

	            return rs.getDouble(1);

	        }

	    } catch (SQLException e) {

	        e.printStackTrace();

	    }

	    return 0;

	}

	private void closeQuietly(AutoCloseable resource){

	    if(resource!=null){

	        try{

	            resource.close();

	        }catch(Exception e){

	        }

	    }
	}
	public List<Cart> getCartItems(int userId){

	    List<Cart> cartList = new ArrayList<Cart>();

	    String sql =
	        "SELECT c.cart_id, c.user_id, c.product_id, c.quantity, " +
	        "p.id, p.name, p.price, p.image_url " +
	        "FROM cart c " +
	        "JOIN products p ON c.product_id = p.id " +
	        "WHERE c.user_id = ?";

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try{

	        conn = DBConnection.getConnection();

	        stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, userId);

	        rs = stmt.executeQuery();

	        while(rs.next()){

	            Product product = new Product();

	            product.setId(rs.getInt("product_id"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getDouble("price"));
	            product.setImageUrl(rs.getString("image_url"));

	            Cart cart = new Cart();

	            cart.setCartId(rs.getInt("cart_id"));
	            cart.setUserId(rs.getInt("user_id"));
	            cart.setProductId(rs.getInt("product_id"));
	            cart.setQuantity(rs.getInt("quantity"));
	            cart.setProduct(product);

	            cartList.add(cart);

	        }

	    }catch(SQLException e){

	        e.printStackTrace();

	    }finally{

	        closeQuietly(rs);
	        closeQuietly(stmt);
	        closeQuietly(conn);

	    }

	    return cartList;
	}
	public boolean increaseQuantity(int cartId) {

	    String sql =
	        "UPDATE cart SET quantity = quantity + 1 WHERE cart_id = ?";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, cartId);

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();

	    }

	    return false;
	}
	public boolean decreaseQuantity(int cartId) {

	    String sql =
	        "UPDATE cart SET quantity = quantity - 1 " +
	        "WHERE cart_id = ? AND quantity > 1";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, cartId);

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();

	    }

	    return false;
	}
	public boolean removeItem(int cartId) {

	    String sql =
	        "DELETE FROM cart WHERE cart_id=?";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, cartId);

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();

	    }

	    return false;
	}
	public int getCartCount(int userId) {

	    String sql =
	        "SELECT COALESCE(SUM(quantity),0) FROM cart WHERE user_id=?";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, userId);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	public boolean clearCart(int userId) {

	    String sql = "DELETE FROM cart WHERE user_id = ?";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, userId);

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();

	    }

	    return false;
	}

}