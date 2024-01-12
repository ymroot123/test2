package com.example.test;

import java.sql.*;

public class JDBCUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/technologyachievement";
    private static final String USER = "root";
    private static final String PASSWORD = "136793";

    // 获取数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库连接
    public static void closeConnection(Statement stat, ResultSet rs, Connection conn) {
        try {
            if (stat != null) {
                stat.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
