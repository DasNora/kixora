package com.sneakershop.dao;

import com.sneakershop.model.User;
import com.sneakershop.util.DBConnection;
import com.sneakershop.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO handles all database operations for the 'users' table.
 *
 * <p>Two main jobs: register a new user (hashes password before storing)
 * and validate login credentials (compares hash against stored value).</p>
 *
 * <p>Java 8 compatible — manual resource cleanup with try-finally.</p>
 */
public class UserDAO {

    /**
     * Registers a new user in the database.
     * The password is hashed with SHA-256 + salt BEFORE storing — plain text
     * passwords never touch the database.
     *
     * @param user  the User object with plain-text password (will be hashed)
     * @return true if registration succeeded, false if username already exists
     */
    public boolean registerUser(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

        String sql = "INSERT INTO users (username, password, first_name, last_name, "
                + "address, city, pin_code, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getCity());
            stmt.setString(7, user.getPinCode());
            stmt.setString(8, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Duplicate username will throw SQLException with error code 1062
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Validates a user's login credentials.
     * Fetches the stored "salt:hash" from the database, then uses PasswordUtil
     * to re-compute the hash with the same salt and compare.
     *
     * @param username  the login username
     * @param plainPassword  the plain-text password to verify
     * @return the User object (with password cleared) if valid, null if invalid
     */
    public User validateUser(String username, String plainPassword) {
        String sql = "SELECT * FROM users WHERE username = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                if (PasswordUtil.verifyPassword(plainPassword, storedHash)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(null); // Never send the hash back!
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setAddress(rs.getString("address"));
                    user.setCity(rs.getString("city"));
                    user.setPinCode(rs.getString("pin_code"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
            return null; // Either username not found or password didn't match

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Fetches a user by their database ID.
     */
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(null);
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setPinCode(rs.getString("pin_code"));
                user.setRole(rs.getString("role"));
                return user;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
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
    
    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();

        String sql="SELECT * FROM users ORDER BY id";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){

                User user=new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCity(rs.getString("city"));
                user.setRole(rs.getString("role"));

                users.add(user);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return users;

    }
}