package com.electronic.model;

public class Groups {
	int group_id;
	String user_name;
	String group_name;
	int number;
	
	public Groups() {
		super();
	}
	
	public Groups(int group_id, String user_name, String group_name, int number) {
		super();
		this.group_id = group_id;
		this.user_name = user_name;
		this.group_name = group_name;
		this.number = number;
	}

	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	

}
