package com.sneakershop.servlet;

import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException,IOException{

        HttpSession session=request.getSession(false);

        User user=(User)session.getAttribute("user");

        if(user==null || !"ADMIN".equals(user.getRole())){

            response.sendRedirect("login");
            return;

        }

        int orderId=Integer.parseInt(request.getParameter("orderId"));

        String status=request.getParameter("status");

        OrderDAO dao=new OrderDAO();

        dao.updateOrderStatus(orderId,status);

        response.sendRedirect("admin-orders");

    }

}