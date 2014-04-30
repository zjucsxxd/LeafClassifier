package com.electronic.util;

import java.util.ArrayList;

import com.electronic.model.FriendTable;

public class PaserToFriendTable {
	public ArrayList<FriendTable> getAllFriends(String strRet)
	{
		ArrayList<FriendTable> friendTables=null;
		friendTables=new ArrayList<FriendTable>();
		
		String []a=strRet.split(";");
		for(int i=0;i<a.length;i++){
			String b[]=a[i].split(",");
			FriendTable m=new FriendTable();
			m.setFriendtable_id(Integer.valueOf(b[0]));
			m.setUser_name(b[1]);
			m.setGroup_id(Integer.valueOf(b[2]));
			m.setFriend_id(b[3]);
			friendTables.add(m);
		}
		return friendTables;
	}

}
