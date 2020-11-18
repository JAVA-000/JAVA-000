package com.example.demo.dao;

import com.example.demo.util.JDBCUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SchoolJDBCDao {

    private void addSchool() {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into student(id, name, age) values(?, ?, ?)";
        PreparedStatement preparedStatement = JDBCUtils.getPreparedStatement(connection, sql);
        try {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "zlh2");
            preparedStatement.setInt(3, 24);

            preparedStatement.execute();
            connection.commit();
            System.out.println("插入成功");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, null, preparedStatement);
        }
    }

    private void selectSchool() {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select id, name, age from student where name = ?";
        PreparedStatement preparedStatement = JDBCUtils.getPreparedStatement(connection, sql);
        try {
            preparedStatement.setString(1, "zlh");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, null, preparedStatement);
        }
    }

    public static void main(String[] args) {
        SchoolJDBCDao dao = new SchoolJDBCDao();
        dao.addSchool();
        //dao.selectSchool();
    }

}
