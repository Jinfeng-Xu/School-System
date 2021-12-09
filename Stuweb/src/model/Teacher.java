package model;

import java.io.InputStream;

/**
 * 
 * @author xujinfengxu
 * Teacher class
 */
public class Teacher {
	private int id;
	private String sn;//教师工号
	private String name;
	private String password;
	private int clazzId;
	private InputStream photo;
	private int status = 1;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public int getClazzId() {
		return clazzId;
	}
	public void setClazzId(int clazzId) {
		this.clazzId = clazzId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public InputStream getPhoto() {
		return photo;
	}
	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
	
}