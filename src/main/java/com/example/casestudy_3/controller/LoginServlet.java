package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.CustomerDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.dao.UserDAO;
import com.example.casestudy_3.entity.Customer;
import com.example.casestudy_3.entity.Order;
import com.example.casestudy_3.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            User user = userDAO.login(email,password);
            if(user!= null){
                HttpSession session = request.getSession();
                OrderDAO orderDAO = new OrderDAO();
                session.setAttribute("user",user);
                int userId = user.getId();
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.selectCustomerByUserId(userId);
                int customerId = customer.getId();
                Order order = orderDAO.selectOrderByCustomerId(customerId);
                int orderId = order.getId();
                if(order != null){
                    session.setAttribute("order",order);
                }else {
                    order = new Order(orderId,customerId);
                    session.setAttribute("order",order);
                }
                if ("ADMIN".equals(user.getRole())){
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/home");
                    requestDispatcher.forward(request,response);
                }
                if ("CUSTOMER".equals(user.getRole())){
                    session.setAttribute("customer",customer);
                    response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                    response.setHeader("Location", "/customerHome");
                }
            }else {
                response.sendRedirect("/login");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

