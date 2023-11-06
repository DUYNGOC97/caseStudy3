package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.dao.OrderItemsDAO;
import com.example.casestudy_3.entity.Book;
import com.example.casestudy_3.entity.Order;
import com.example.casestudy_3.entity.OrderItems;
import com.example.casestudy_3.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddToOrderServlet", value = "/showOrder")
public class ShowOrderServlet extends HttpServlet {
    private static OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
    private static BookDAO bookDAO = new BookDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    public static List<Book> bookList;

    public static List<OrderItems> orderItemsList;
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        int bookId = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String bookTile = request.getParameter("bookTitle");
        BookDAO bookDAO = new BookDAO();
        try {
            Book book = bookDAO.selectBook(bookId);
            long price = book.getPrice();
            long subTotalPrice = price * quantity;
            OrderItems orderItems = new OrderItems(bookTile,orderId,bookId,quantity,price,subTotalPrice);
            HttpSession session = request.getSession();
            List<OrderItems> order = (List<OrderItems>) session.getAttribute("order");
            if(order == null){
                order = new ArrayList<>();
                order.add(orderItems);
            }else {
                boolean found = false;
                for(OrderItems item : order){
                    if(item.getBookId() == bookId){
                        item.setQuantity(item.getQuantity() + quantity);
                        item.setSubTotalPrice(item.getQuantity() * item.getPrice());
                        orderItemsDAO.insertOrderItems(item);
                        found = true;
                        break;
                    }
                }
                if(!found){
                    order.add(orderItems);
                    orderItemsDAO.insertOrderItems(orderItems);
                }
            }
            request.setAttribute("order",order);
            response.sendRedirect("/product");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("customer");
        Order order = (Order) request.getAttribute("order");
        int orderId = order.getId();

        List<OrderItems> orderItemsList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();

        try {
            orderItemsList = orderItemsDAO.selectAllOrderItemByOrderId(user.getId());
            bookList = bookDAO.selectAllBook();
            request.setAttribute("orderItemsList", orderItemsList);
            request.setAttribute("bookList", bookList);
            request.setAttribute("orderId", orderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
