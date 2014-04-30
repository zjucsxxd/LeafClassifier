<%@ page language="java" import="java.util.*,com.electronic.dao.*,com.electronic.util.*,com.electronic.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String user_name=null;
    String group_id=null;
    int real_group_id=-1;
	try
	{
		user_name=request.getParameter("user_name").toString();
		user_name =new String(user_name.getBytes("ISO8859_1"),"UTF-8");
		
		group_id=request.getParameter("group_id").toString();
		group_id =new String(group_id.getBytes("ISO8859_1"),"UTF-8");
		real_group_id=Integer.valueOf(group_id);

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	FriendTableDao friendTableDao=new FriendTableDao();
	List<FriendTable> friendTables=friendTableDao.getAllByUserAndGroupId(user_name, real_group_id);
	FriendTable friendTable;
	for(int i=0;i<friendTables.size();i++)
	{
		friendTable=friendTables.get(i);
		out.print(""+friendTable.getFriendtable_id()+","+friendTable.getUser_name()+","+
		friendTable.getGroup_id()+","+friendTable.getFriend_id()+";");
	}
	
	
	
 %>