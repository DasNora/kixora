package com.sneakershop.servlet;

import com.sneakershop.dao.ProductDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException,IOException{

        HttpSession session=request.getSession(false);

        User user=(User)session.getAttribute("user");

        if(user==null || !"ADMIN".equals(user.getRole())){

            response.sendRedirect("login");

            return;

        }

        int id=Integer.parseInt(request.getParameter("id"));

        ProductDAO dao=new ProductDAO();

        dao.deleteProduct(id);

        response.sendRedirect("admin-products");

    }

}