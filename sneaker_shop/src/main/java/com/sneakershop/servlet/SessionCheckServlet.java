package com.sneakershop.servlet;

import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SessionCheckServlet
 *
 * Returns:
 *   true  -> user is still logged in
 *   false -> session expired or user not logged in
 */
@WebServlet("/session-check")
public class SessionCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.getWriter().print("false");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.getWriter().print("false");
        } else {
            response.getWriter().print("true");
        }
    }
}