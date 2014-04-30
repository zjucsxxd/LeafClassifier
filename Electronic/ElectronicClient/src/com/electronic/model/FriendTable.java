package com.electronic.model;

public class FriendTable {
	int friendtable_id;
	String user_name;
	int group_id;
	String friend_id;
	public int getFriendtable_id() {
		return friendtable_id;
	}
	public void setFriendtable_id(int friendtable_id) {
		this.friendtable_id = friendtable_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public FriendTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FriendTable(int friendtable_id, String user_name, int group_id,
			String friend_id) {
		super();
		this.friendtable_id = friendtable_id;
		this.user_name = user_name;
		this.group_id = group_id;
		this.friend_id = friend_id;
	}
	
}
