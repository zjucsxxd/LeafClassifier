<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String username=null;
	String pwd=null;
	String sex=null;
	String strold=null;
	int old=-1;
	
	try
	{
		username=request.getParameter("username").toString();
		username =new String(username.getBytes("ISO8859_1"),"UTF-8");
		
		pwd=request.getParameter("pwd").toString();
		pwd =new String(pwd.getBytes("ISO8859_1"),"UTF-8");
		
		sex=request.getParameter("sex").toString();
		sex =new String(sex.getBytes("ISO8859_1"),"UTF-8");
		
		strold =request.getParameter("old").toString();
		strold =new String(strold.getBytes("ISO8859_1"),"UTF-8");
		old=Integer.valueOf(strold);
		
		//System.out.println(username+" "+pwd+" "+sex+" "+old);
	}
	
	catch(Exception e)
	{
		
		e.printStackTrace();
	}
	
	User user=new User(username,pwd,sex,old);
	UserDao userDao=new UserDao();
	boolean isRegister=userDao.add(user);
	if(!isRegister)
	{
		out.println("true");
	}
	else 
	{
		out.println("false");
	}
	
 %>
