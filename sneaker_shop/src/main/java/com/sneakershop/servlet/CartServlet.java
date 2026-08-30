package com.sneakershop.servlet;

import com.sneakershop.dao.CartDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {

            int productId = Integer.parseInt(request.getParameter("productId"));

            cartDAO.addToCart(user.getId(), productId);

        }
        else if ("increase".equals(action)) {

            int cartId = Integer.parseInt(request.getParameter("cartId"));

            cartDAO.increaseQuantity(cartId);

        }
        else if ("decrease".equals(action)) {

            int cartId = Integer.parseInt(request.getParameter("cartId"));

            cartDAO.decreaseQuantity(cartId);

        }
        else if ("remove".equals(action)) {

            int cartId = Integer.parseInt(request.getParameter("cartId"));

            cartDAO.removeItem(cartId);

        }

        response.sendRedirect("cart");
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("login");
            return;

        }

        User user = (User)session.getAttribute("user");

        if(user == null){

            response.sendRedirect("login");
            return;

        }

        request.setAttribute(
                "cartItems",
                cartDAO.getCartItems(user.getId()));

        request.setAttribute(
                "cartTotal",
                cartDAO.getCartTotal(user.getId()));

        request.getRequestDispatcher("cart.jsp")
                .forward(request,response);

    }

}