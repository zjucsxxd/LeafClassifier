<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	int group_id=0;
	String user_name=null;
	String group_name=null;
	int  number=0;	
	try
	{
		user_name=request.getParameter("user_name").toString();
		user_name =new String(user_name.getBytes("ISO8859_1"),"UTF-8");
		
		group_name=request.getParameter("group_name").toString();
		group_name =new String(group_name.getBytes("ISO8859_1"),"UTF-8");
		
	}
	
	catch(Exception e)
	{
		
		e.printStackTrace();
	}
	
	Groups group=new Groups(group_id,user_name,group_name,number);
	GroupDao groupDao=new GroupDao();
	boolean isAdd=groupDao.add(group);
	if(!isAdd)
	{
		out.println("true");
	}
	else 
	{
		out.println("false");
	}
	
 %>
