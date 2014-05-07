package com.electronic.model;

public class User {
	private String username;
	private String password;
	private String sex;
	private int old;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public User(String username, String password, String sex, int old) {
		super();
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.old = old;
	}
	public User() {
		super();
	}
	

}
