package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Clazz;
import model.Page;
import util.StringUtil;

/**
 * 
 * @author xujinfengxu
 * 专业信息数据库操作
 */
public class ClazzDao extends BaseDao {
	public List<Clazz> getClazzList(Clazz clazz, Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "SELECT * FROM s_clazz ";
		if(!StringUtil.isEmpty(clazz.getName())) {
			sql += "WHERE name like '%" + clazz.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql);
		try {
			while(resultSet.next()) {
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setName(resultSet.getString("name"));
				cl.setInfo(resultSet.getString("info"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public int getClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "SELECT count(*)as total FROM s_clazz ";
		if(!StringUtil.isEmpty(clazz.getName())) {
			sql += "WHERE name like '%" + clazz.getName() + "%'";
		}
		ResultSet resultSet = query(sql);
		try {
			while(resultSet.next()) {
				total = (resultSet.getInt("total"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	public boolean addClazz(Clazz clazz){
		String sql = "INSERT into s_clazz values(null, '" + clazz.getName() + "','" + clazz.getInfo() + "') ";
		return update(sql);
	}
	public boolean deleteClazz(int id){
		String sql = "DELETE from s_clazz WHERE id = " + id;
		return update(sql);
	}
	public boolean editClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		String sql = "update s_clazz set name = '"+clazz.getName()+"',info = '"+clazz.getInfo()+"' where id = " + clazz.getId();
		return update(sql);
	}
	
}
