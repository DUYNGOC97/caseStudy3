package com.example.casestudy_3.dao;

import com.example.casestudy_3.connection.JDBCConnection;
import com.example.casestudy_3.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String INSERT_USER = "insert into users(USERNAME,PASSWORD,EMAIL,ROLE) VALUE (?, ?, ?, ?);";
    private static final String SELECT_BY_ID = "SELECT * FROM USERS WHERE ID = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM USERS;";
    private static final String DELETE_BY_ID = "DELETE FROM USERS WHERE ID = ?;";
    private static final String UPDATE_USER = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, EMAIL = ?, ROLE = ? WHERE ID = ?;";
    private static final String LOGIN = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?;";
    private static final String CHECK_ROLE_ADMIN = "SELECT * FROM USERS WHERE ID=? AND ROLE = 'ADMIN';";
    private static final String CHECK_ROLE_CUSTOMER = "SELECT * FROM USERS WHERE ID=? AND ROLE = 'CUSTOMER';";
    private static final String SELECT_ID_BY_EMAIL = "select id from users where email = ?;";

    public UserDAO(){}
    public void insertUser(User user) throws SQLException {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1,user.getUserName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getEmail());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public User selectUserById(int id)throws SQLException{
        User user = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String userName = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                String email = resultSet.getString("EMAIL");
                String role = resultSet.getString("ROLE");
                user = User.builder()
                        .userName(userName)
                        .email(email)
                        .id(id)
                        .password(password)
                        .role(role)
                        .build();
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public List<User> selectAllUser(){
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String userName = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                String email = resultSet.getString("EMAIL");
                String role = resultSet.getString("ROLE");
                User user = User.builder()
                        .userName(userName)
                        .email(email)
                        .id(id)
                        .password(password)
                        .role(role)
                        .build();
                userList.add(user);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return userList;
    }
    public boolean deleteUserById(int id) throws SQLException{
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
    public boolean updateUserById(User user){
        boolean rowUpdate = false;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1,user.getUserName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getEmail());
            statement.setString(4, String.valueOf(user.getRole()));
            statement.setInt(5,user.getId());
            rowUpdate = statement.executeUpdate()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowUpdate;
    }
    public User login(String email,String password){
        User user = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(LOGIN);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String userName = resultSet.getString("USERNAME");
                String password1 = resultSet.getString("PASSWORD");
                String email1 = resultSet.getString("EMAIL");
                String role = resultSet.getString("ROLE");
                user = User.builder()
                        .role(role)
                        .password(password1)
                        .id(id)
                        .userName(userName)
                        .email(email1)
                        .build();
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public User checkRoleAdmin(int id){
        User user = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_ROLE_ADMIN);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            String email = resultSet.getString("EMAIL");
            String userName = resultSet.getString("USERNAME");
            String password = resultSet.getString("PASSWORD");
            String role = resultSet.getString("ROLE");
            user = User.builder()
                    .email(email)
                    .userName(userName)
                    .id(id)
                    .role(role)
                    .password(password)
                    .build();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    private User checkRoleCustomer(int id){
        User user = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_ROLE_CUSTOMER);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            String email = resultSet.getString("EMAIL");
            String userName = resultSet.getString("USERNAME");
            String password = resultSet.getString("PASSWORD");
            String role = resultSet.getString("CUSTOMER");
            user = User.builder()
                    .email(email)
                    .userName(userName)
                    .id(id)
                    .role(role)
                    .password(password)
                    .build();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public int selectIdUserByEmail(String email){
        int id = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_EMAIL);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
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
