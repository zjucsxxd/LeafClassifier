package com.electronic.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.electronic.model.User;
import com.electronic.util.JDBCTemplate;
import com.electronic.util.Transaction;
import com.electronic.util.Query;

/*
 * 1  2  3 测试通过
 */

public class UserDao {
    //--1:添加用户，返回值布尔
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
	
	
   //--2：根据用户名查找用户，返回用户。
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
	
	
	
//--3: 根据用户名查找用户，并修改密码。返回值布尔。
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
