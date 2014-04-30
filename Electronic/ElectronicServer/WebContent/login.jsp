<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String username=null;
	String pwd=null;
	
	try
	{
		
		username = request.getParameter("username").toString();
		username =new String(username.getBytes("ISO8859_1"),"UTF-8");
		
		pwd = request.getParameter("pwd").toString();
		pwd =new String(pwd.getBytes("ISO8859_1"),"UTF-8");
	}
	
	catch(Exception e)
	{
		username="";
		pwd="";
		e.printStackTrace();
	}
	
	
	UserDao userDao=new UserDao();
	User user=userDao.get(username);
	if(user.getPassword().equals(pwd))
	{
		out.println(1);
	}
	else
	{
		out.println(0);
	}
	
 %>
