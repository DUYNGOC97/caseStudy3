package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.dao.CustomerDAO;
import com.example.casestudy_3.dao.OrderDAO;
import com.example.casestudy_3.entity.Book;
import com.example.casestudy_3.entity.Customer;
import com.example.casestudy_3.entity.Order;

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

@WebServlet(name = "CustomerHomeServlet", value = "/customerHome")
public class CustomerHomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("customer");
            Order order = (Order) session.getAttribute("order");
            session.setAttribute("order",order);
            List<Book> bookListHome = bookDAO.selectAllBook();
            request.setAttribute("bookListHome", bookListHome);
            request.setAttribute("customer", customer);
            request.setAttribute("order", order);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customerHome.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching books from the database.", e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        super.doPost(request,response);
    }
}

