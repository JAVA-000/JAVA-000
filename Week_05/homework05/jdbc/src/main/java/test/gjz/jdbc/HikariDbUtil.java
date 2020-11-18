package test.gjz.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <pre>
 * HikariPool
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 21:20
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HikariDbUtil {

    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig("/hikari.properties");
        ds = new HikariDataSource(config);
    }

    /**
     * 获取连接
     */
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭连接
     * @param connection
     */
    public static void closeConnection(Connection connection){
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

