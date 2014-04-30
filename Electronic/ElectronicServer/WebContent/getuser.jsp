<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*" pageEncoding="UTF-8"%>
<%@page import="com.electronic.model.User"
        import="com.elctronic.dao.UserDao"
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String userName=null;
	
	User user=null;
	
	user=new User();
	
	try
	{
		
		userName = request.getParameter("userName").toString();
		userName =new String(userName.getBytes("ISO8859_1"),"UTF-8");
	
	}
	
	catch(Exception e)
	{
		userName=null;
		e.printStackTrace();
	}
	
	if(userName==null){
		out.println("");
	}
	
	else{
	
	UserDao ud=new UserDao();
	user=ud.get(userName);
	
		//获取留言信息格式为用户名，用户密码，用户id
		out.println(user.getUser_name()+","+user.getPassword());
	}
 %>
