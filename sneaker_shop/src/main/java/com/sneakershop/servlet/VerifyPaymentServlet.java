package com.sneakershop.servlet;

import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.sneakershop.dao.CartDAO;
import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.Cart;

import java.util.List;

import java.io.IOException;

@WebServlet("/verify-payment")
public class VerifyPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Payment details sent by Razorpay
        String paymentId = request.getParameter("razorpay_payment_id");
        String orderId = request.getParameter("razorpay_order_id");
        String signature = request.getParameter("razorpay_signature");
        CartDAO cartDAO = new CartDAO();

        List<Cart> cartItems = cartDAO.getCartItems(user.getId());

        double totalAmount = 0;

        for (Cart item : cartItems) {

            totalAmount += item.getProduct().getPrice() * item.getQuantity();

        }

        OrderDAO orderDAO = new OrderDAO();

        int generatedOrderId = orderDAO.saveOrder(
                user.getId(),
                totalAmount,
                "PAID",
                orderId
        );
        for (Cart item : cartItems) {

            orderDAO.saveOrderItem(
                    generatedOrderId,
                    item.getProduct().getId(),
                    item.getQuantity(),
                    item.getProduct().getPrice()
            );

        }
        cartDAO.clearCart(user.getId());

        System.out.println("Saved Order ID: " + generatedOrderId);
        // For testing
        System.out.println("===== PAYMENT SUCCESS =====");
        System.out.println("User ID : " + user.getId());
        System.out.println("Order ID : " + orderId);
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Signature : " + signature);
        System.out.println("===========================");

        // We'll save the order here in the next step

        request.setAttribute("orderId", generatedOrderId);
        request.setAttribute("amount", totalAmount);
        request.setAttribute("paymentStatus", "PAID");

        request.getRequestDispatcher("order-success.jsp")
               .forward(request, response);
    }
}