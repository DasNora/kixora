package com.sneakershop.servlet;

import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.Order;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-orders")
public class AdminOrdersServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException,IOException{

        HttpSession session=request.getSession(false);

        User user=(User)session.getAttribute("user");

        if(user==null || !"ADMIN".equals(user.getRole())){

            response.sendRedirect("login");
            return;

        }

        OrderDAO dao=new OrderDAO();

        List<Order> orders=dao.getAllOrders();

        request.setAttribute("orders",orders);

        request.getRequestDispatcher("admin-orders.jsp")
                .forward(request,response);

    }

}