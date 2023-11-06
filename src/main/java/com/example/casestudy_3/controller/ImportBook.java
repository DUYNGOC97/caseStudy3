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
import java.util.Date;
import java.util.List;

@WebServlet(name = "ImportServlet", value = "/importBook")
public class ImportBook extends HttpServlet {
    private static List<Book> bookList;
    private static BookDAO bookDAO = new BookDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            bookList = bookDAO.selectAllBook();
            request.setAttribute("bookList",bookList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/importProduct.jsp");
            requestDispatcher.forward(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO();
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        Long price = Long.valueOf(request.getParameter("price"));
        String description = request.getParameter("description");
        String published_date = request.getParameter("published_date");
        String publisher = request.getParameter("publisher");
        String category = request.getParameter("category");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageURL = request.getParameter("imageURL");
        Book book = new Book(title,author,price,description,published_date,publisher,category,quantity,imageURL);
        try {
            bookDAO.insertBooks(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/importProduct");
    }
}
