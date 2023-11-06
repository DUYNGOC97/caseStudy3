package com.example.casestudy_3.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/honya";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
    public static Connection getConnection(){
        Connection connection;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
