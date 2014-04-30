package com.electronic.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.electronic.model.User;
import com.electronic.util.JDBCTemplate;
import com.electronic.util.Transaction;
import com.electronic.util.Query;

/*
 * 1  2  3 ����ͨ��
 */

public class UserDao {
    //--1:����û�������ֵ����
	public static boolean add(final User user) throws Exception{
		JDBCTemplate<Boolean> t = new Transaction<Boolean>(){

			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("insert into user values(?,?,?,?)");
				ps.setString(1, user.getUser_name());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getSex());
				ps.setInt(4, user.getOld());
				
				return ps.execute();
			}

	};
	return t.execute();
	}
	
	
   //--2�������û��������û��������û���
	public static User get(final String user_name) throws Exception{
		JDBCTemplate<User> q = new Query<User>(){

		@Override
		protected User doQuery(Connection conn) throws Exception {
			PreparedStatement ps = conn
					.prepareStatement("select * from user where user_name=?");
			ps.setString(1, user_name);
			ps.execute();
			ResultSet rs=ps.getResultSet();
			User user=null;
			if(rs.next())
			{
				user=new User();
				user.setUser_name(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setOld(rs.getInt("old"));
			}
			return user;
		}};
		return q.execute();
	}
	
	
	
//--3: �����û��������û������޸����롣����ֵ������
	public static boolean alterPassword(final User user,final String password) throws Exception
	{
		
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("update user set user_name=?,password=?,sex=?,old=? where user_name=?");
				ps.setString(1, user.getUser_name());
				ps.setString(2, password);
				ps.setString(3, user.getSex());
				ps.setInt(4, user.getOld());
				ps.setString(5, user.getUser_name());
				return ps.execute();
			}
		};
		return t.execute();
		
	}
	
}
