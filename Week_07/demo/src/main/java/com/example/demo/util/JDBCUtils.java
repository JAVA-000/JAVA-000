package com.example.demo.util;

import java.sql.*;

public class JDBCUtils {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/test1", "root", "mysql");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    public static Statement getStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static void close(Connection connection, Statement statement, PreparedStatement preparedStatement) {
        try {
            if (null != connection) {
                connection.close();
            }
            if (null != statement) {
                statement.close();
            }
            if (null != preparedStatement) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
