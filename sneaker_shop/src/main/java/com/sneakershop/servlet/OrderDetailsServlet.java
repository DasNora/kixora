package com.sneakershop.servlet;

import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.Order;
import com.sneakershop.model.OrderItem;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("user") == null){

            response.sendRedirect("login");
            return;

        }

        User user = (User) session.getAttribute("user");

        int orderId =
                Integer.parseInt(request.getParameter("orderId"));

        OrderDAO dao = new OrderDAO();

        Order order = dao.getOrderById(orderId);

        // Security check
        if(order == null || order.getUserId() != user.getId()){

            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;

        }

        List<OrderItem> items =
                dao.getOrderItems(orderId);

        request.setAttribute("order", order);
        request.setAttribute("items", items);

        request.getRequestDispatcher("order-details.jsp")
               .forward(request,response);

    }

}