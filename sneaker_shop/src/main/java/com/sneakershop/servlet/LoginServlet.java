package com.sneakershop.servlet;

import com.sneakershop.dao.UserDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet handles user authentication.
 *
 * <p>Flow: reads username + password + role from Login.jsp form → calls
 * UserDAO.validateUser() → if valid, stores user in session and creates
 * a cookie → routes to User.jsp or Employee.jsp based on role.</p>
 *
 * <p>Java 8 compatible — uses traditional javax.servlet APIs.</p>
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Displays the login form page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    /**
     * Processes login form submission.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate credentials against the database
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            // Check for old session and then create a new session
        	// 1. Clear any existing session attached to this browser request
        	HttpSession oldSession = request.getSession(false);
        	if (oldSession != null) {
        	    oldSession.invalidate();
        	}
        	// 2. Create a completely fresh session for the new login
        	
            HttpSession session = request.getSession(true);
            
            
            
            //3. Save the user in the session
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("user", user);

            // Create a custom cookie for the browser
            String sessionId = session.getId();
            System.out.println("[LoginServlet] Session ID: " + sessionId);

            Cookie userCookie = new Cookie("SNEAKER_USER_SESSION", username + ":" + user.getRole());
            
            userCookie.setMaxAge(30 * 60);     // 30 minutes
            userCookie.setHttpOnly(true);       // JavaScript cannot read this cookie
            response.addCookie(userCookie);

            System.out.println("[LoginServlet] Cookie created: SNEAKER_USER_SESSION = "
                    + username + ":" + user.getRole());

            // Route based on role
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect("AdminDashboard.jsp");
            } else {
                response.sendRedirect("products");
            }
            

        } else {
            // Login failed — send back to login page with error message
            request.setAttribute("error", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}