package com.electronic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.electronic.model.Groups;
import com.electronic.util.JDBCTemplate;
import com.electronic.util.Transaction;
import com.electronic.util.Query;

public class GroupDao {
	//--1:根据用户名的添加协同工作组，返回值布尔。
	public static boolean add(final Groups group) throws Exception{
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(){

			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("insert into groups values(?,?,?,?)");
				ps.setInt(1, group.getGroup_id());
				ps.setString(2, group.getUser_name());
				ps.setString(3,group.getGroup_name());
				ps.setInt(4,group.getNumber());
				
				return ps.execute();
			}

	};
	return t.execute();
	}
	
	
	//--2：根据用户名查找协同工作组，返回协同工作组List。
	public static List<Groups> getAllByUser(final String user_name) throws Exception{
		JDBCTemplate<List<Groups>> q = new Query<List<Groups>>(){

		@Override
		protected List<Groups> doQuery(Connection conn) throws Exception {
			List<Groups> grouplist=new ArrayList<Groups>();
			PreparedStatement ps = conn
					.prepareStatement("select * from groups where user_name=?");
			ps.setString(1, user_name);
			ps.execute();
			ResultSet rs=ps.getResultSet();
			
			while(rs.next())
			{
				Groups group=new Groups();
				group.setGroup_id(rs.getInt("group_id"));
				group.setUser_name(rs.getString("user_name"));
				group.setGroup_name(rs.getString("group_name"));
				group.setNumber(rs.getInt("number"));
				grouplist.add(group);
				
			}
			return grouplist;
		}};
		return q.execute();
	}
	//--3:根据用户名和协同工作组名查找协同工作组ID，返回协同工作组ID
	public static Groups getByUserAndName(final String user_name,final String group_name) throws Exception{
		JDBCTemplate<Groups> q=new Query<Groups>(){

			protected Groups doQuery(Connection conn) throws Exception {
				
				PreparedStatement ps=conn
						.prepareStatement("select * from groups where user_name=? and group_name=? ");
				ps.setString(1, user_name);
				ps.setString(2, group_name);
				ps.execute();
				ResultSet rs=ps.getResultSet();
				Groups group=new Groups();
				if(rs.next())
				{
					
					group.setGroup_id(rs.getInt("group_id"));
					group.setUser_name(user_name);
					group.setGroup_name(group_name);
					group.setNumber(rs.getInt("number"));
				}
				return group;
			}
			
		};
		
		return q.execute();
	}	

	
	
	//--4:根据用户名和协同工作组ID删除删除协同工作组，返回布尔（因为有外键的关系，所以要先删除Friendtable里面工作组Id为此ID的所有好友。）
	public static Boolean DeleteGroup(final String user_name, final String group_name) throws Exception{
		
		GroupDao groupDao=new GroupDao();
		Groups group=groupDao.getByUserAndName(user_name, group_name);
		final int group_id=group.getGroup_id();
		FriendTableDao friendtableDao=new FriendTableDao();
		friendtableDao.deleteByGroupId(group_id);
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("delete from groups where group_id=?");
				ps.setInt(1, group_id);
				return ps.execute();
			}
		};
		return t.execute();
	}
	
	
	//--5:根据协同工作组用户名和和工作组ID的修改人数，返回布尔。
	

}
