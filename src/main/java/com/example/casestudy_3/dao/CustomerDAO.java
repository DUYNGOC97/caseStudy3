package com.example.casestudy_3.dao;

import com.example.casestudy_3.connection.JDBCConnection;
import com.example.casestudy_3.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMERS(USER_ID, FULL_NAME, ADDRESS,PHONE_NUMBER,WALLET) VALUE(?,?,?,?,?);";
    private static final String SELECT_BY_ID = "SELECT * FROM CUSTOMERS WHERE ID = ?;";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM CUSTOMERS WHERE USER_ID = ?;";

    private static final String SELECT_ALL_CUSTOMER = "SELECT * FROM CUSTOMERS;";
    private static final String DELETE_BY_ID = "DELETE FROM CUSTOMERS WHERE ID = ?;";
    private static final String UPDATE_CUSTOMER = "UPDATE CUSTOMERS SET USER_ID = ?, FULL_NAME = ?, ADDRESS = ?, PHONE_NUMBER = ?, WALLET = ? WHERE ID = ?;";
    private static final String UPDATE_CUSTOMER_WALLET = "UPDATE PRODUCTS SET WALLET = WALLET - ? WHERE ID = ?;";
    private static final String SELECT_ID_BY_USERS_ID = "SELECT ID FROM CUSTOMERS WHERE USER_ID = ?;";

    public void insertCustomer(Customer customer) throws SQLException {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER);
            statement.setInt(1,customer.getUser_id());
            statement.setString(2,customer.getFullName());
            statement.setString(3,customer.getAddress());
            statement.setString(4,customer.getPhoneNumber());
            statement.setLong(5,customer.getWallet());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Customer selectCustomerById(int id) throws SQLException{
        Customer customer = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int user_id = resultSet.getInt("ID");
                String fullName = resultSet.getString("FULL_NAME");
                String address = resultSet.getString("ADDRESS");
                String phoneNumber = resultSet.getString("PHONE_NUMBER");
                long wallet = resultSet.getLong("WALLET");
                customer = Customer.builder()
                        .address(address)
                        .wallet(wallet)
                        .fullName(fullName)
                        .user_id(user_id)
                        .phoneNumber(phoneNumber)
                        .id(id)
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }
    public Customer selectCustomerByUserId(int userId) throws SQLException{
        Customer customer = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String fullName = resultSet.getString("FULL_NAME");
                String address = resultSet.getString("ADDRESS");
                String phoneNumber = resultSet.getString("PHONE_NUMBER");
                long wallet = resultSet.getLong("WALLET");
                customer = Customer.builder()
                        .address(address)
                        .wallet(wallet)
                        .fullName(fullName)
                        .user_id(userId)
                        .phoneNumber(phoneNumber)
                        .id(id)
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }
    private List<Customer> selectAllCustomers() throws SQLException{
        List<Customer> customerList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CUSTOMER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id =resultSet.getInt("ID");
                int user_id = resultSet.getInt("USER_ID");
                String fullName = resultSet.getString("FULL_NAME");
                String address = resultSet.getString("ADDRESS");
                String phoneNumber = resultSet.getString("PHONE_NUMBER");
                long wallet = resultSet.getLong("WALLET");
                Customer customer = Customer.builder()
                        .id(id)
                        .phoneNumber(phoneNumber)
                        .user_id(user_id)
                        .fullName(fullName)
                        .address(address)
                        .wallet(wallet)
                        .build();
                customerList.add(customer);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerList;
    }
    private boolean deleteCustomerById(int id) throws SQLException{
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1,id);
            rowUpdate = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
    private boolean updateCustomer(Customer customer) throws SQLException{
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setInt(1,customer.getUser_id());
            statement.setString(2,customer.getFullName());
            statement.setString(3,customer.getAddress());
            statement.setString(4,customer.getPhoneNumber());
            statement.setLong(5,customer.getWallet());
            statement.setInt(6,customer.getId());
            rowUpdate = statement.executeUpdate()>0;
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
    private void updateCustomerWallet(int id,long price) throws SQLException{
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_WALLET);
            statement.setLong(1,price);
            statement.setInt(2,id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int selectIdUserByUserId(int userId) {
        int customerId = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_USERS_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customerId = resultSet.getInt("ID");
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerId;
    }
}
