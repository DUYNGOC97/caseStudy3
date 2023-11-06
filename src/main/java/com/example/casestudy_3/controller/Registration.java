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

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void goGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registration.jsp");
        requestDispatcher.forward(request,response);
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

        try {
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String fullName = request.getParameter("fullname");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phonenumber");
            UserDAO userDAO = new UserDAO();
            User user = User.builder()
                    .userName(userName)
                    .password(password)
                    .role(role)
                    .email(email)
                    .build();
            userDAO.insertUser(user);
            int userId = userDAO.selectIdUserByEmail(email);
            if(user.getRole().equals("CUSTOMER")){
                CustomerDAO customerDAO = new CustomerDAO();
                OrderDAO orderDAO = new OrderDAO();
                Customer customer = new Customer(userId,fullName,phoneNumber,address);
                customerDAO.insertCustomer(customer);
                int customerId = customerDAO.selectIdUserByUserId(userId);
                Order order = new Order(customerId);
                orderDAO.insertOrder(order);
                HttpSession session = request.getSession();
                session.setAttribute("order",order);
            }
            response.sendRedirect("home");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
