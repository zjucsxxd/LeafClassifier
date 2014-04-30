package com.electronic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.electronic.model.FriendTable;
import com.electronic.model.Groups;
import com.electronic.util.JDBCTemplate;
import com.electronic.util.Query;
import com.electronic.util.Transaction;


public class FriendTableDao {
	//--1:根据用户名和groupId，friend_id的添加，返回布尔。
	public static boolean add(final FriendTable friendTable) throws Exception{
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(){

			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("insert into friendtable values(?,?,?,?)");
				ps.setInt(1, friendTable.getGroup_id());
				ps.setString(2, friendTable.getUser_name());
				ps.setInt(3, friendTable.getGroup_id());
				ps.setString(4, friendTable.getFriend_id());
				return ps.execute();
			}

	};
	return t.execute();
	}
	//--2:删除group_id 为某一Id的所有好友
	public static Boolean deleteByGroupId(final int group_id) throws Exception {
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("delete from friendtable where group_id=?");
				ps.setInt(1, group_id);
				return ps.execute();
			}
		};
		return t.execute();
	}

	
	//--3：根据用户和组ID的好友查找，返回好友List。
	public static List<FriendTable> getAllByUserAndGroupId(final String user_name,final int group_id) throws Exception{
		JDBCTemplate<List<FriendTable>> q = new Query<List<FriendTable>>(){

		@Override
		protected List<FriendTable> doQuery(Connection conn) throws Exception {
			List<FriendTable> friendTables=new ArrayList<FriendTable>();
			PreparedStatement ps = conn
					.prepareStatement("select * from friendtable where user_name=? and group_id=?");
			ps.setString(1, user_name);
			ps.setInt(2, group_id);
			ps.execute();
			ResultSet rs=ps.getResultSet();
			
			while(rs.next())
			{
				FriendTable friendTable=new FriendTable();
				friendTable.setFriendtable_id(rs.getInt("friendtable_id"));
				friendTable.setUser_name(rs.getString("user_name"));
				friendTable.setGroup_id(rs.getInt("group_id"));
				friendTable.setFriend_id(rs.getString("friend_id"));
				friendTables.add(friendTable);
				
			}
			return friendTables;
		}};
		return q.execute();
	}

}
