package com.sneakershop.servlet;

import com.sneakershop.dao.OrderDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/delete-order")
public class DeleteOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");

        if(user == null){

            response.sendRedirect("login");

            return;

        }

        int orderId =
                Integer.parseInt(request.getParameter("id"));

        OrderDAO dao = new OrderDAO();

        dao.deleteOrder(orderId, user.getId());

        response.sendRedirect("my-orders");

    }

}