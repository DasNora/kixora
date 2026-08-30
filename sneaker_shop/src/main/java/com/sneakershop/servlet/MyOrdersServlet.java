package com.sneakershop.servlet;

import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.Order;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();

        List<Order> orders = orderDAO.getOrdersByUser(user.getId());

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("my-orders.jsp")
               .forward(request, response);
    }
}