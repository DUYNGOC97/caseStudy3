package com.example.casestudy_3.dao;

import com.example.casestudy_3.connection.JDBCConnection;
import com.example.casestudy_3.entity.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String INSERT_ORDER = "INSERT INTO ORDERS(CUSTOMER_ID,ORDER_DATE,TOLTAL_AMOUNT,STATUS,PHONE_NUMBER) VALUES(?,?,?,?,?);";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM ORDERS;";
    private static final String SELECT_ALL_ORDERS_BY_CUSTOMER_ID = "SELECT * FROM ORDERS where CUSTOMER_ID = ?;";
    private static final String UPDATE_ORDER = "UPDATE ORDERS SET ORDER_DATE = ?, SHIPPING_ADDRESS = ?, TOLTAL_AMOUNT = ?, PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
    private static final String SELECT_ORDER_BY_CUSTOMER_ID = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?;";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM ORDERS WHERE ID = ?;";

    public void insertOrder(Order order){
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);
            statement.setInt(1,order.getCustomerId());
            statement.setDate(2, order.getOrderDate());
            statement.setLong(3,order.getTotalAmount());
            statement.setString(4,order.getStatus());
            statement.setString(5,order.getPhoneNumber());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Order> selectAllOrders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                int customerId = resultSet.getInt("CUSTOMER_ID");
                Date orderDate = resultSet.getDate("ORDER_DATE");
                long totalAmount = resultSet.getLong("TOTAL_AMOUNT");
                String status = resultSet.getString("STATUS");
                String shippingAddress = resultSet.getString("SHIPPING_ADDRESS");
                Order order = Order.builder()
                        .orderDate(orderDate)
                        .customerId(customerId)
                        .id(id)
                        .shippingAddress(shippingAddress)
                        .status(status)
                        .totalAmount(totalAmount)
                        .build();
                orderList.add(order);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderList;
    }
    public List<Order> selectAllOrderByCustomerId(int customerId) throws SQLException{
        List<Order> orderList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_BY_CUSTOMER_ID);
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                Date orderDate = resultSet.getDate("ORDER_DATE");
                long totalAmount = resultSet.getLong("TOTAL_AMOUNT");
                String status = resultSet.getString("STATUS");
                Order order = Order.builder()
                        .totalAmount(totalAmount)
                        .status(status)
                        .orderDate(orderDate)
                        .customerId(customerId)
                        .id(id)
                        .build();
                orderList.add(order);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderList;
    }
    public boolean updateOrder(Order order){
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setDate(1,order.getOrderDate() );
            statement.setString(2, order.getShippingAddress());
            statement.setLong(3,order.getTotalAmount());
            statement.setString(4, order.getPhoneNumber());
            statement.setInt(5,order.getCustomerId());
            rowUpdate = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
    public Order selectOrderByCustomerId(int customerId) throws SQLException{
        Order order = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_CUSTOMER_ID);
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                Date orderDate = resultSet.getDate("ORDER_DATE");
                long totalAmount = resultSet.getLong("TOlTAL_AMOUNT");
                String status = resultSet.getString("STATUS");
                order = Order.builder()
                        .id(id)
                        .customerId(customerId)
                        .orderDate(orderDate)
                        .status(status)
                        .totalAmount(totalAmount)
                        .build();
                statement.close();
                connection.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }
    public Order selectOrderById(int id) throws SQLException{
        Order order = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_CUSTOMER_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            int customerId = resultSet.getInt("CUSTOMER_ID");
            Date orderDate = resultSet.getDate("ORDER_DATE");
            long totalAmount = resultSet.getLong("TOTAL_AMOUNT");
            String status = resultSet.getString("STATUS");
            order = Order.builder()
                    .id(id)
                    .customerId(customerId)
                    .orderDate(orderDate)
                    .status(status)
                    .totalAmount(totalAmount)
                    .build();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }
}
