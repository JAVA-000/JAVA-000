package test.gjz.jdbc;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * <pre>
 * Hikari
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 21:07
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HikariPoolDemo {

    public static void main(String[] args) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
        user.setName("张三");
        user.setAge(50);
        user.setAddress("中国广东");

        Connection connection = DbUtil.connectDataBase();

        try {
            // 新增记录
            create(user);

            List<User> users = select();
            System.out.println("查询结果结果：" + users);

            if (users != null && users.size() > 0) {
                User firstUser = users.get(0);
                firstUser.setName("李四");
                update(firstUser);
            }

            delete(user.getUserId());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            DbUtil.closeConnect(connection);
        }


        // 测试开启事务
        try {
            createTransaction(user);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        //批量插入数据
        List<User> users = new ArrayList<>();
        User user1 ;
        for (int i = 0; i < 5; i++) {
            user1= new User();
            user1.setUserId(UUID.randomUUID().toString().replace("-", ""));
            user1.setName("user" + i);
            user1.setAge(i + 20);
            user1.setAddress("address" + i);
            users.add(user1);
        }

        batchCreate(users);
    }


    /**
     * 增
     */
    public static void create(User user) throws SQLException{

        Connection connection = HikariDbUtil.getConnection();

        String sql = "INSERT INTO user (user_id, name, age, address) VALUE(?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getAddress());

        // 执行语句
        preparedStatement.execute();
        // 释放连接
        HikariDbUtil.closeConnection(connection);
    }


    /**
     * 增
     */
    public static void createTransaction( User user) throws SQLException{
        // 从线程池中获取连接
        Connection connection = HikariDbUtil.getConnection();
        // 开始事务
        connection.setAutoCommit(false);

        String sql = "INSERT INTO user (user_id, name, age, address) VALUE(?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getAddress());
        preparedStatement.execute();

        // 随机数被2整除， 测试回滚
        int testNumber = new Random().nextInt();
        System.out.println("testNumber:" + testNumber);
        if (testNumber % 2 == 0) {
            System.out.println("被2整除，回滚");
            // 回滚事务
            connection.rollback();
        }else{
            // 提交事务
            connection.commit();
        }

        // 关闭连接
        HikariDbUtil.closeConnection(connection);
    }

    /**
     * 批量创建记录
     * @param users
     */
    public static void batchCreate(List<User> users){

        Connection connection = HikariDbUtil.getConnection();

        if (users == null || users.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO user (user_id, name, age, address) VALUE(?, ?, ?, ?)";
        try {
            // 开启事务
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (User user : users) {
                preparedStatement.setString(1, user.getUserId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setString(4, user.getAddress());
                // 批量处理
                preparedStatement.addBatch();
            }

            //批量处理
            preparedStatement.executeBatch();
            //提交事务
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
            try {
                //回滚事务
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }finally {
            HikariDbUtil.closeConnection(connection);
        }

    }

    /**
     * 删
     */
    public static void delete(String userId) throws SQLException{

        Connection connection = HikariDbUtil.getConnection();

        if (StringUtils.isEmpty(userId)) {
            return;
        }

        String sql = "DELETE FROM user WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);

        preparedStatement.execute();

        HikariDbUtil.closeConnection(connection);
    }

    /**
     * 改
     */
    public static void update(User user) throws SQLException{

        Connection connection = HikariDbUtil.getConnection();

        if (user == null || StringUtils.isEmpty(user.getUserId())) {
            return;
        }

        String sql = "UPDATE user SET name = ?, age = ?, address = ? where user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setInt(2, user.getAge());
        preparedStatement.setString(3, user.getAddress());
        preparedStatement.setString(4, user.getUserId());

        preparedStatement.execute();

        HikariDbUtil.closeConnection(connection);
    }

    /**
     * 查
     */
    public static List<User> select() throws SQLException{

        Connection connection = HikariDbUtil.getConnection();

        String sql = "SELECT * FROM user";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<User> users = new ArrayList<>();
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setUserId(resultSet.getString("user_id"));
            user.setAddress(resultSet.getString("address"));
            users.add(user);
        }

        HikariDbUtil.closeConnection(connection);

        return users;
    }


    /**
     * 检查连接是否已经关闭
     * @param connection
     * @return
     */
    private static boolean checkConnectionIsClose(Connection connection){
        if (connection == null) {
            return true;
        }

        try {
            return connection.isClosed();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }

}

