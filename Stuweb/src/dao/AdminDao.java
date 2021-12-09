package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;

/**
 * 
 * @author xujinfengxu
 * Admin_db operation
 */
public class AdminDao extends BaseDao {
	
	public Admin login(String name, String password) {
		String sql = "SELECT * FROM s_admin WHERE name = '" + name + "' and password = '" + password + "'";
		ResultSet resultSet = query(sql);
		try {
			if(resultSet.next()) {
				Admin admin = new Admin();
				admin.setId(resultSet.getInt("id"));
				admin.setName(resultSet.getString("name"));
				admin.setPassword(resultSet.getString("password"));
				admin.setStatus(resultSet.getInt("status"));
				return admin;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean editPassword(Admin admin, String newPassword) {
		String sql = "update s_admin set password = '" + newPassword + "' WHERE id = " + admin.getId();
		return update(sql);
	}
}
