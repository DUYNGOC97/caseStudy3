package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.dao.OrderItemsDAO;
import com.example.casestudy_3.entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "orderBookServlet", value = "/orderBook")
public class OrderBookServlet extends HttpServlet {
    private static OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
    private static BookDAO bookDAO = new BookDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    public static List<OrderItems> orderItemsList;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<OrderItems> orderItemsListOfCustomer = new ArrayList<>();
        Customer customer = (Customer) session.getAttribute("customer");
        Order order = (Order) session.getAttribute("order");
        int orderId = order.getId();
        long totalAmount = 0;
        try {
            orderItemsList = orderItemsDAO.selectAllOrderItemByOrderId(orderId);
            if (orderItemsList == null || orderItemsList.isEmpty()) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/notFound.jsp");
                requestDispatcher.forward(request, response);
            } else {
                for (OrderItems orderItems : orderItemsList) {
                    if (orderItems.getOrderId() == orderId) {
                        orderItemsListOfCustomer.add(orderItems);
                        totalAmount += orderItems.getSubTotalPrice();
                        order.setTotalAmount(totalAmount);
                    }
                }
                if (orderItemsListOfCustomer.isEmpty()) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/notFound.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute("customer",customer);
                    request.setAttribute("order",order);
                    session.setAttribute("orderItemsListOfCustomer", orderItemsListOfCustomer);
                    request.setAttribute("orderItemsListOfCustomer", orderItemsListOfCustomer);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/orderBook.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }
}
