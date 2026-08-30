package com.sneakershop.servlet;

import com.sneakershop.dao.ProductDAO;
import com.sneakershop.model.Product;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-products")
public class AdminProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if(user == null || !"ADMIN".equals(user.getRole())){
            response.sendRedirect("login");
            return;
        }

        ProductDAO dao = new ProductDAO();

        List<Product> products = dao.getAllProducts();

        request.setAttribute("products", products);

        request.getRequestDispatcher("admin-products.jsp")
               .forward(request, response);
    }
}