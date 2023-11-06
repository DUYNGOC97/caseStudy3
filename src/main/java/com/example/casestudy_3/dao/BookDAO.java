package com.example.casestudy_3.dao;

import com.example.casestudy_3.connection.JDBCConnection;
import com.example.casestudy_3.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String INSERT_BOOK = "INSERT INTO PRODUCTS(TITLE,AUTHOR,PRICE,DESCRIPTION,PUBLISHED_DATE,PUBLISHER,CATEGORY,QUANTITY,image_url) VALUES(?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_BOOK = "UPDATE PRODUCTS SET TITLE = ?, AUTHOR = ?, PRICE = ?, DESCRIPTION = ?, PUBLISHED_DATE = ?, PUBLISHER = ?, CATEGORY = ?, QUANTITY = ?, image_url = ? WHERE ID = ?; ";
    private static final String DELETE_BOOK = "DELETE FROM PRODUCTS WHERE ID = ? ";
    private static final String SELECT_ALL_BOOK = "SELECT * FROM PRODUCTS;";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM PRODUCTS WHERE ID = ?;";
    private static final String UPDATE_BOOK_QUANTITY = "UPDATE PRODUCTS SET QUANTITY = QUANTITY - ? WHERE ID = ?;";
    private static final String SELECT_PRICE = "SELECT PRICE FROM PRODUCTS WHERE ID = ?;";
    private static final String SELECT_TITLE_BY_ID_BOOK = "SELECT TITLE FROM PRODUCTS WHERE ID = ?;";
    private static final String SELECT_BOOK_SEARCH = "SELECT * FROM books WHERE title LIKE ? AND CATEGORY = ? ";



    public BookDAO(){

    }
    public List<Book> searchBooks(String keyword,String category) {
        List<Book> searchResults = new ArrayList<>();

        try  {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_SEARCH);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, category);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt("ID");
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                long price = resultSet.getLong("PRICE");
                String description = resultSet.getString("DESCRIPTION");
                String publishedDate = resultSet.getString("PUBLISHED_DATE");
                String publisher = resultSet.getString("PUBLISHER");
                String category1 = resultSet.getString("CATEGORY");
                int quantity = resultSet.getInt("QUANTITY");
                String imageURL = resultSet.getString("IMAGE_URL");
                Book book = Book.builder()
                        .id(bookId)
                        .title(title)
                        .author(author)
                        .quantity(quantity)
                        .publisher(publisher)
                        .category(category1)
                        .description(description)
                        .price(price)
                        .publishedDate(publishedDate)
                        .imageURL(imageURL)
                        .build();
                searchResults.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }
    public long selectPriceBook(int id){
        long price = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PRICE);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                price = resultSet.getLong("PRICE");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }
    public long selectTitleBook(int id){
        long price = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TITLE_BY_ID_BOOK);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                price = resultSet.getLong("TITLE");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }
    public void insertBooks(Book book) throws SQLException {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_BOOK);
            statement.setString(1, book.getTitle());
            statement.setString(2,book.getAuthor());
            statement.setLong(3,book.getPrice());
            statement.setString(4,book.getDescription());
            statement.setString(5,book.getPublishedDate());
            statement.setString(6,book.getPublisher());
            statement.setString(7,book.getCategory());
            statement.setInt(8,book.getQuantity());
            statement.setString(9,book.getImageURL());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Book selectBook(int id) throws SQLException{
        Book book = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int bookId = resultSet.getInt("ID");
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                long price = resultSet.getLong("PRICE");
                String description = resultSet.getString("DESCRIPTION");
                String publishedDate = resultSet.getString("PUBLISHED_DATE");
                String publisher = resultSet.getString("PUBLISHER");
                String category = resultSet.getString("CATEGORY");
                int quantity = resultSet.getInt("QUANTITY");
                String imageURL = resultSet.getString("IMAGE_URL");
                book = Book.builder()
                        .id(bookId)
                        .title(title)
                        .author(author)
                        .quantity(quantity)
                        .publisher(publisher)
                        .category(category)
                        .description(description)
                        .price(price)
                        .publishedDate(publishedDate)
                        .imageURL(imageURL)
                        .build();
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }
    public List<Book> selectAllBook()throws SQLException{
        List<Book> bookList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOK);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                long price = resultSet.getLong("PRICE");
                String description = resultSet.getString("DESCRIPTION");
                String publishedDate = resultSet.getString("PUBLISHED_DATE");
                String publisher = resultSet.getString("PUBLISHER");
                String category = resultSet.getString("CATEGORY");
                int quantity = resultSet.getInt("QUANTITY");
                String imageURL = resultSet.getString("IMAGE_URL");

                Book book = Book.builder()
                        .id(id)
                        .title(title)
                        .author(author)
                        .quantity(quantity)
                        .publisher(publisher)
                        .category(category)
                        .description(description)
                        .price(price)
                        .publishedDate(publishedDate)
                        .imageURL(imageURL)
                        .build();
                bookList.add(book);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookList;
    }
    public boolean deleteBook(int id) throws SQLException{
        boolean rowUpdated = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BOOK);
            statement.setInt(1,id);
            rowUpdated = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdated;
    }
    public boolean updateBook(Book book) throws SQLException{
        boolean rowUpdated = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK);
            statement.setString(1, book.getTitle());
            statement.setString(2,book.getAuthor());
            statement.setLong(3,book.getPrice());
            statement.setString(4,book.getDescription());
            statement.setString(5,book.getPublishedDate());
            statement.setString(6,book.getPublisher());
            statement.setString(7,book.getCategory());
            statement.setInt(8,book.getQuantity());
            statement.setString(9,book.getImageURL());
            statement.setInt(10,book.getId());
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdated;
    }
    public void updateBookQuantity(int id, int quantity) throws SQLException{
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUANTITY);
            statement.setInt(1,quantity);
            statement.setInt(2,id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
