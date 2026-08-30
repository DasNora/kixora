package com.sneakershop.servlet;

import com.sneakershop.dao.UserDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegisterServlet handles new user registration.
 *
 * <p>Flow: reads form fields from Registration.jsp → builds a User object →
 * calls UserDAO.registerUser() (which hashes the password with SHA-256 + salt)
 * → redirects to Login.jsp with success message or back to form with error.</p>
 *
 * <p>Java 8 compatible — uses traditional javax.servlet APIs.</p>
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Displays the registration form page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Registration.jsp").forward(request, response);
    }

    /**
     * Processes registration form submission.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read all form fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pinCode = request.getParameter("pinCode");
        String role = request.getParameter("role");

        // Basic validation
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            request.getRequestDispatcher("Registration.jsp").forward(request, response);
            return;
        }

        // Default role to USER if not specified
        if (role == null || role.trim().isEmpty()) {
            role = "USER";
        }

        // Build the User object (plain-text password will be hashed by DAO)
        User user = new User(username, password, firstName, lastName,
                address, city, pinCode, role);

        boolean success = userDAO.registerUser(user);

        if (success) {
            request.setAttribute("success", "Registration successful! Please log in.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Username may already exist.");
            request.getRequestDispatcher("Registration.jsp").forward(request, response);
        }
    }
}