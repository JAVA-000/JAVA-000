package test.gjz.jdbc;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * <pre>
 * 研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
 * 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JdbcDemoApplication {

	public static void main(String[] args) {

		User user = new User();
		user.setUserId(UUID.randomUUID().toString().replace("-", ""));
		user.setName("张三");
		user.setAge(50);
		user.setAddress("中国广东");

		Connection connection = DbUtil.connectDataBase();

		try {
			// 新增记录
			create(connection, user);

			List<User> users = select(connection);
			System.out.println("查询结果结果：" + users);

			if (!CollectionUtils.isEmpty(users)) {
				User firstUser = users.get(0);
				firstUser.setName("李四");
				update(connection, firstUser);
			}

			delete(connection, user.getUserId());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			DbUtil.closeConnect(connection);
		}


		// 测试开启事务
		try {
			createTransaction(connection, user);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}




		DbUtil.closeConnect(connection);

	}


	/**
	 * 增
	 */
	public static void create(Connection connection, User user) throws SQLException{

		if (checkConnectionIsClose(connection)) {
			return;
		}

		String sql = "INSERT INTO user (user_id, name, age, address) VALUE(?, ?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, user.getUserId());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setInt(3, user.getAge());
		preparedStatement.setString(4, user.getAddress());

		// 执行语句
		preparedStatement.execute();
	}


	/**
	 * 增
	 */
	public static void createTransaction(Connection connection, User user) throws SQLException{

		if (checkConnectionIsClose(connection)) {
			return;
		}
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
	}

	/**
	 * 批量创建记录
	 * @param connection
	 * @param users
	 */
	public static void batchCreate(Connection connection, List<User> users){
		if (checkConnectionIsClose(connection)) {
			return;
		}

		if (CollectionUtils.isEmpty(users)) {
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
		}

	}

	/**
	 * 删
	 */
	public static void delete(Connection connection, String userId) throws SQLException{
		if (checkConnectionIsClose(connection) || StringUtils.isEmpty(userId)) {
			return;
		}

		String sql = "DELETE FROM user WHERE user_id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, userId);

		preparedStatement.execute();
	}

	/**
	 * 改
	 */
	public static void update(Connection connection, User user) throws SQLException{
		if (checkConnectionIsClose(connection)) {
			return;
		}

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
	}

	/**
	 * 查
	 */
	public static List<User> select(Connection connection) throws SQLException{
		if (checkConnectionIsClose(connection)) {
			return new ArrayList<>();
		}

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
