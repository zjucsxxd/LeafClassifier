package com.electronic.model;

public class User {
	String user_name;
	String password;
	String sex;
	int old;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getOld() {
		return old;
	}
	public void setOld(int old) {
		this.old = old;
	}
	public User() {
		super();
	}
	public User(String user_name, String password, String sex, int old) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.sex = sex;
		this.old = old;
	}
	
	
	
	
		

}
