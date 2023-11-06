package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.entity.Book;
import com.example.casestudy_3.entity.Customer;
import com.example.casestudy_3.entity.Order;
import com.example.casestudy_3.entity.OrderItems;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name ="paymentSuccessServlet", value = "/paymentSuccess")
public class PaymentSuccessServlet extends HttpServlet {
    protected void goGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        request.setAttribute("customer",customer);
        Order order = (Order) session.getAttribute("order");
        request.setAttribute("order",order);

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateOrder(order);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/paymentSuccess.jsp");
        requestDispatcher.forward(request,response);
    }
    protected void goPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }
}
