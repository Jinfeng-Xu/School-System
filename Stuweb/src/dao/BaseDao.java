package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DbUtil;

/**
 * 
 * @author xujinfengxu
 * basicDao
 */
public class BaseDao {
	private DbUtil dbUtil = new DbUtil();
	/**
	 * Close db connection
	 */
	public void closeCon() {
		dbUtil.closeCon();
	}
	
	/**
	 *  Basic query
	 */
	public ResultSet query(String sql){
		try {
			PreparedStatement prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Change sql
	 * @param sql
	 * @return
	 */
	public boolean update(String sql){
		try {
			return dbUtil.getConnection().prepareStatement(sql).executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Connection getConnection(){
		return dbUtil.getConnection();
	}
}
