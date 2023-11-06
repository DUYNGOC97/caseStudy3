package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.UserDAO;
import com.example.casestudy_3.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet
public class UserServiceServlet extends HttpServlet {
    private static List<User> userList;
    private static UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, ServletException, IOException {
        userList = userDAO.selectAllUser();
        request.setAttribute("userList", userList);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "edit":
                try {
                    showEditForm(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    deleteUser(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("userService.jsp");
                requestDispatcher.forward(request, response);
                break;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "edit":
                try {
                    updateUser(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    deleteUser(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        int id = request.getIntHeader("id");
        userDAO.deleteUserById(id);
        userList = userDAO.selectAllUser();
        response.sendRedirect("/userService");
    }

    //    UPDATE USERS SET USERNAME = ?, PASSWORD = ?, EMAIL = ?, ROLE = ? WHERE ID
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        int id = request.getIntHeader("id");
        User user = User.builder()
                .email(email)
                .userName(userName)
                .password(password)
                .id(id)
                .role(role)
                .build();
        userDAO.updateUserById(user);
        response.sendRedirect("/userService");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.selectUserById(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("editUser.jsp");
        request.setAttribute("user", user);
        requestDispatcher.forward(request, response);
    }
}
