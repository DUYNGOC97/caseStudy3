package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.dao.OrderItemsDAO;
import com.example.casestudy_3.entity.Book;
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

@WebServlet(name = "addToOrderServlet", value = "/addToOrder")
public class AddToOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customerHome");
        requestDispatcher.forward(request,response);
    }
//    public OrderItems(int orderId, int bookId, int quantity, long price, long subTotalPrice) {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        BookDAO bookDAO = new BookDAO();
        OrderDAO orderDAO = new OrderDAO();
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int orderId = order.getId();
        List<OrderItems> orderItemsList = null;
        try {
            orderItemsList = orderItemsDAO.selectAllOrderItemByOrderId(orderId);
            Book book = bookDAO.selectBook(bookId);
            long price = bookDAO.selectPriceBook(bookId);
            int quantity = 1;
            String bookTitle = book.getTitle();
            if (orderItemsList == null || orderItemsList.isEmpty()) {
                OrderItems orderItems = new OrderItems(bookTitle, orderId, bookId, quantity, price, price);
                orderItemsDAO.insertOrderItems(orderItems);
                response.sendRedirect("/customerHome");

            } else {
                boolean found = false;
                for (OrderItems orderItems : orderItemsList) {
                    if (orderItems.getBookId() == bookId) {
                        int newQuantity = quantity + orderItems.getQuantity();
                        long subTotalPrice = newQuantity * price;
                        OrderItems updatedOrderItems = new OrderItems(bookTitle, orderId, bookId, newQuantity, price, subTotalPrice);
                        orderItemsDAO.updateOrderItem(updatedOrderItems);
                        response.sendRedirect("/customerHome");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    OrderItems newOrderItems = new OrderItems(bookTitle, orderId, bookId, quantity, price, price);
                    orderItemsDAO.insertOrderItems(newOrderItems);
                    response.sendRedirect("/customerHome");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
