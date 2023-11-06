package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.dao.OrderItemsDAO;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="processPaymentServlet",value = "/processPayment")
public class ProcessPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        int customerId = order.getCustomerId();
        Date orderDate = order.getOrderDate();
        long totalAmount = order.getTotalAmount();
        String shippingAddress = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        Order order1 = new Order(customerId,orderDate,totalAmount,shippingAddress,phoneNumber);
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateOrder(order1);
        session.setAttribute("order",order1);
        request.setAttribute("order",order1);
        response.sendRedirect("/paymentSuccess.jsp");

        List<OrderItems> orderItemsList = (List<OrderItems>) session.getAttribute("orderItemsListOfCustomer");
        for (OrderItems orderItems:orderItemsList){
            OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
            try {
                orderItemsDAO.deleteOrderItemById(orderItems.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/processPayment.jsp");
        requestDispatcher.forward(request,response);
    }
}
