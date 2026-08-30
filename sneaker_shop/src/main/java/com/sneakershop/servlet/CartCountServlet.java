package com.sneakershop.servlet;

import com.sneakershop.dao.CartDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart-count")
public class CartCountServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int count = 0;

        if (session != null) {

            User user = (User) session.getAttribute("user");

            if (user != null) {

                count = cartDAO.getCartCount(user.getId());

            }

        }

        response.setContentType("text/plain");
        response.getWriter().print(count);
    }
}