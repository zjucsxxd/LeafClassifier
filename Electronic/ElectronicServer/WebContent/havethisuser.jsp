<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String name=null;
	try
	{
		name=request.getParameter("name").toString();
		name =new String(name.getBytes("ISO8859_1"),"UTF-8");
	}
	catch(Exception e)
	{
		out.print("");
		e.printStackTrace();
	}
	UserDao userDao=new UserDao();
	User user=new User();
	user=userDao.get(name);
	if(user!=null)
	{
		out.print("true");
	}
	else
	{
		out.print("false");
	}
	
 %>
