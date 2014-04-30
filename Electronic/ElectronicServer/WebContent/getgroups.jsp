<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String user_name=null;
	try
	{
		user_name=request.getParameter("user_name").toString();
		user_name =new String(user_name.getBytes("ISO8859_1"),"UTF-8");
//		System.out.println("user_name");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	GroupDao groupDao=new GroupDao();
	List<Groups> groups=groupDao.getAllByUser(user_name);
	Groups group;
	for (int i=0;i<groups.size();i++)
	{
		group=groups.get(i);
		out.print(""+group.getGroup_id()+","+group.getUser_name()+","+group.getGroup_name()+","+group.getNumber()+";");
	System.out.println(""+group.getGroup_id()+","+group.getUser_name()+","+group.getGroup_name()+","+group.getNumber()+";");
	System.out.println("........");
	}
	
	
 %>