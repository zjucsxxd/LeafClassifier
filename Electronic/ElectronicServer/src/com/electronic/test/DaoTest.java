package com.electronic.test;

import java.util.List;

import com.electronic.dao.FriendTableDao;
import com.electronic.dao.GroupDao;
import com.electronic.dao.UserDao;
import com.electronic.model.FriendTable;
import com.electronic.model.Groups;
import com.electronic.model.User;

public class DaoTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FriendTableDao friendTableDao=new FriendTableDao();
		List<FriendTable> friendTables=friendTableDao.getAllByUserAndGroupId("cfl", 2);
		FriendTable friendTable;
//		for(int i=0;i<friendTables.size();i++)
//		{
//			friendTable=friendTables.get(i);
//			System.out.print(""+friendTable.getFriendtable_id()+","+friendTable.getUser_name()+","+
//			friendTable.getGroup_id()+","+friendTable.getFriend_id()+";");
//		}
	
		
		

	}

}
