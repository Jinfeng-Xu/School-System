package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author xujinfengxu
 * link to mysqlUtil
 */
public class DbUtil {
	
	private static String dbUrl = null;
	private static String dbUserName = null;
	private static String dbPassword = null;
	private static String jdbcName = null;
	private Connection connection = null;
	
	static{
        try {
            InputStream instream = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();

            properties.load(instream);

            jdbcName = properties.getProperty("jdbcName");
            dbUrl = properties.getProperty("dbUrl");
            dbUserName = properties.getProperty("dbUserName");
            dbPassword = properties.getProperty("dbPassword");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Connection getConnection() {
		try {
			Class.forName(jdbcName);
			connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("连接失败");
			e.printStackTrace();
		}
		return connection;
	}
	public void closeCon() {
		if(connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbUtil dbUtil = new DbUtil();
		dbUtil.getConnection();
	}

}
