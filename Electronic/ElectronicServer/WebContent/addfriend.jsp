<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
    int friendtable_id=0;
	String user_name=null;
    String friend_name=null;
    String group_name=null;
	try
	{
		user_name=request.getParameter("user_name").toString();
		user_name =new String(user_name.getBytes("ISO8859_1"),"UTF-8");
		
		friend_name=request.getParameter("friend_name").toString();
		friend_name =new String(friend_name.getBytes("ISO8859_1"),"UTF-8");
		
		group_name=request.getParameter("group_name").toString();
		group_name =new String(group_name.getBytes("ISO8859_1"),"UTF-8");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	GroupDao groupDao=new GroupDao();
	Groups group=new Groups();
	group=groupDao.getByUserAndName(user_name, group_name);
	int group_id=group.getGroup_id();
	FriendTableDao friendTableDao=new FriendTableDao();
	FriendTable friendTable=new FriendTable(friendtable_id,user_name,group_id,friend_name);
	boolean isAdd=friendTableDao.add(friendTable);
	if(!isAdd)
	{
		out.println("true");
	}
	else 
	{
		out.println("false");
	}
	
 %>
