<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String user_name=null;
    String group_name=null;
	try
	{
		user_name=request.getParameter("user_name").toString();
		user_name =new String(user_name.getBytes("ISO8859_1"),"UTF-8");
		group_name=request.getParameter("group_name").toString();
		group_name =new String(group_name.getBytes("ISO8859_1"),"UTF-8");
	}
	catch(Exception e)
	{
		out.print("");
		e.printStackTrace();
	}
	GroupDao groupDao=new GroupDao();
	Groups group=new Groups();
	group=groupDao.getByUserAndName(user_name, group_name);
//	System.out.println(user_name+" "+group_name+" "+group.getGroup_id()+" "+group.getGroup_name());
	if((group!=null)&&(group.getGroup_id()!=0))
	{
		out.print("true");
	//	System.out.println("true");
	}
	else
	{
		out.print("false");
		System.out.println("false");
	}
	
 %>
