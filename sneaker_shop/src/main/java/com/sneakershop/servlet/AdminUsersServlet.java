package com.sneakershop.servlet;

import com.sneakershop.dao.UserDAO;
import com.sneakershop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-users")
public class AdminUsersServlet extends HttpServlet {

    private UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        User admin = (User) session.getAttribute("user");

        if(admin == null || !"ADMIN".equals(admin.getRole())){
            response.sendRedirect("login");
            return;
        }

        List<User> users = dao.getAllUsers();

        request.setAttribute("users", users);

        request.getRequestDispatcher("admin-users.jsp")
               .forward(request,response);
    }

}