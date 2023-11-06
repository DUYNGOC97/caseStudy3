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

@WebServlet(name = "viewBookServlet",value = "/viewBook")
public class ViewBookServlet extends HttpServlet {
   private static List<Book> bookList;
   private static BookDAO bookDAO = new BookDAO();
   protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
      try {
         bookList = bookDAO.selectAllBook();
         request.setAttribute("bookList",bookList);
         String action = request.getParameter("action");
         if(action == null){
            action = "";
         }
         switch (action){
            case "edit":
               try {
                  showEditForm(request,response);
               } catch (Exception e) {
                  e.printStackTrace();
               }
               break;
            case "delete":
               try {
                  deleteProduct(request,response);
               } catch (Exception e) {
                  e.printStackTrace();
               }
               break;
            default:
               RequestDispatcher dispatcher1 = request.getRequestDispatcher("viewProducts.jsp");
               dispatcher1.forward(request, response);
               break;
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action = request.getParameter("action");
      if (action == null) {
         action = "";
      }
      switch (action) {
         case "edit":
            try {
               updateProduct(request, response);
            } catch (Exception e) {
               e.printStackTrace();
            }
            break;
         case "delete":
            try {
               deleteProduct(request, response);
            } catch (Exception e) {
               e.printStackTrace();
            }
            break;
      }
   }

   private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//      AUTHOR = ?, PRICE = ?, DESCRIPTION = ?, PUBLISHED_DATE = ?, PUBLISHER = ?, CATEGORY = ?, QUANTITY = ? WHERE ID = ?; ";
      String title = request.getParameter("title");
      String author = request.getParameter("author");
      Long price = Long.valueOf(request.getParameter("price"));
      String description = request.getParameter("description");
      String publishedDate = request.getParameter("publishedDate");
      String publisher = request.getParameter("publisher");
      String category = request.getParameter("category");
      int quantity = Integer.parseInt(request.getParameter("quantity"));
      int id = Integer.parseInt(request.getParameter("id"));
      Book book = Book.builder()
              .quantity(quantity)
              .publishedDate(publishedDate)
              .price(price)
              .description(description)
              .category(category)
              .publisher(publisher)
              .author(author)
              .title(title)
              .id(id).
              build();
      bookDAO.updateBook(book);
      response.sendRedirect("/viewProducts");
   }

   private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
      int id = Integer.parseInt(request.getParameter("id"));
      bookDAO.deleteBook(id);
      bookList = bookDAO.selectAllBook();
      response.sendRedirect("/viewProducts");
   }

   private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
      int id = Integer.parseInt(request.getParameter("id"));
      Book book = bookDAO.selectBook(id);
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
      request.setAttribute("book",book);
      requestDispatcher.forward(request,response);
   }
}
