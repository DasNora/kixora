package com.sneakershop.servlet;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.sneakershop.dao.CartDAO;
import com.sneakershop.model.User;
import com.sneakershop.util.RazorpayConfig;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("login");
            return;

        }

        User user =
                (User) session.getAttribute("user");

        if(user == null){

            response.sendRedirect("login");
            return;

        }

        try {

            CartDAO cartDAO = new CartDAO();

            // Fetch cart items
            request.setAttribute(
                    "cartItems",
                    cartDAO.getCartItems(user.getId()));

            // Calculate total amount
            double total = 0;

            for (com.sneakershop.model.Cart item : cartDAO.getCartItems(user.getId())) {

                total += item.getProduct().getPrice() * item.getQuantity();

            }

            RazorpayClient client =
                    new RazorpayClient(
                            RazorpayConfig.KEY_ID,
                            RazorpayConfig.KEY_SECRET);

            JSONObject options = new JSONObject();

            options.put(
                    "amount",
                    (int)(total * 100));      // Razorpay expects paise

            options.put(
                    "currency",
                    "INR");

            options.put(
                    "receipt",
                    "receipt_" + System.currentTimeMillis());

            Order order = client.orders.create(options);

            request.setAttribute("amount", total);
            request.setAttribute("order", order.toString());

            request.getRequestDispatcher("checkout.jsp")
                   .forward(request, response);

        }
        catch (Exception e) {

            throw new ServletException(e);

        }


    }

}