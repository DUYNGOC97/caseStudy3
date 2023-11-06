package com.example.casestudy_3.dao;

import com.example.casestudy_3.connection.JDBCConnection;
import com.example.casestudy_3.entity.OrderItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAO {
    private static final String INSERT_ORDER_ITEMS = "INSERT INTO ORDER_ITEMS (ORDER_ID,PRODUCT_ID,QUANTITY,PRICE,SUBTOTAL_PRICE,BOOK_TITLE) values (?,?,?,?,?,?);";
    private static final String SELECT_ALL_ORDER_ITEMS = "SELECT * FROM ORDER_ITEMS;";
    private static final String SELECT_ALL_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM ORDER_ITEMS WHERE  ORDER_ID = ?;";
    private static final String DELETE_ORDER_ITEM_BY_ID = "DELETE FROM ORDER_ITEMS  WHERE ID = ?;";
    private static final String UPDATE_ORDER_ITEM = "UPDATE ORDER_ITEMS SET  QUANTITY = ?,SUBTOTAL_PRICE = ? WHERE PRODUCT_ID = ?;";
    public boolean updateOrderItem(OrderItems orderItems){
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_ITEM);
            statement.setInt(1,orderItems.getQuantity());
            statement.setLong(2,orderItems.getSubTotalPrice());
            statement.setInt(3,orderItems.getBookId());
            rowUpdate = statement.executeUpdate() >0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
    public void insertOrderItems(OrderItems orderItems) throws SQLException {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_ITEMS);
            statement.setInt(1,orderItems.getOrderId());
            statement.setInt(2,orderItems.getBookId());
            statement.setInt(3,orderItems.getQuantity());
            statement.setLong(4,orderItems.getPrice());
            statement.setLong(5,orderItems.getSubTotalPrice());
            statement.setString(6,orderItems.getBookTitle());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<OrderItems> selectAllOrderItems() throws SQLException{
        List<OrderItems> orderItemsList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDER_ITEMS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                int orderId = resultSet.getInt("ORDER_ID");
                int bookId = resultSet.getInt("BOOK_ID");
                int quantity = resultSet.getInt("QUANTITY");
                long price = resultSet.getLong("PRICE");
                String bookTitle = resultSet.getString("BOOK_TITLE");
                OrderItems orderItems = OrderItems.builder()
                        .orderId(orderId)
                        .bookId(bookId)
                        .id(id)
                        .price(price)
                        .quantity(quantity)
                        .bookTitle(bookTitle)
                        .build();
                orderItemsList.add(orderItems);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderItemsList;
    }
    public List<OrderItems> selectAllOrderItemByOrderId(int orderId) throws SQLException{
        List<OrderItems> orderItemsList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDER_ITEMS_BY_ORDER_ID);
            statement.setInt(1,orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                int bookId = resultSet.getInt("PRODUCT_ID");
                int quantity = resultSet.getInt("QUANTITY");
                long price = resultSet.getLong("PRICE");
                long totalPrice = resultSet.getLong("SUBTOTAL_PRICE");
                String bookTitle = resultSet.getString("BOOK_TITLE");

                OrderItems orderItems = OrderItems.builder()
                        .orderId(orderId)
                        .bookId(bookId)
                        .id(id)
                        .subTotalPrice(totalPrice)
                        .price(price)
                        .quantity(quantity)
                        .bookTitle(bookTitle)
                        .build();
                orderItemsList.add(orderItems);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderItemsList;
    }
    public boolean deleteOrderItemById(int id) throws SQLException{
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_ITEM_BY_ID);
            statement.setInt(1,id);
            rowUpdate = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
}
