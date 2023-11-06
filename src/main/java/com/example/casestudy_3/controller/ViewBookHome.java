package com.example.casestudy_3.controller;

import com.example.casestudy_3.dao.BookDAO;
import com.example.casestudy_3.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "bookViewHome", value = "/bookViewHome")
public class ViewBookHome extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BookDAO bookDAO = new BookDAO();
        try {
            Book book = bookDAO.selectBook(id);
            request.setAttribute("book",book);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/productView.jsp");
            requestDispatcher.forward(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }
}

