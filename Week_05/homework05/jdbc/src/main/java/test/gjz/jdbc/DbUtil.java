package test.gjz.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <pre>
 * 原生JDBC使用
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/18 11:44
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DbUtil {

    private static String driverClassName;
    private static String url;
    private static String userName;
    private static String passWord;

    static {
        InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            driverClassName = prop.getProperty("driverClassName");
            url = prop.getProperty("url");
            userName = prop.getProperty("username");
            passWord = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 连接数据库
     */
    public static Connection connectDataBase(){

        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, userName, passWord);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void closeConnect(Connection connection){
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }


}

