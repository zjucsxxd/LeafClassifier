package com.electronic.util;

import java.util.ArrayList;

import com.electronic.model.Groups;

public class PaserToGroup {
	public ArrayList<Groups> getAllGroups(String strRet)
	{
		ArrayList<Groups> groups=null;
		groups=new ArrayList<Groups>();
		
		String []a=strRet.split(";");
		for(int i=0;i<a.length;i++){
			String b[]=a[i].split(",");
			Groups m=new Groups();
			m.setGroup_id(Integer.valueOf(b[0]));
			m.setUser_name(b[1]);
			m.setGroup_name(b[2]);
			m.setNumber(Integer.valueOf(b[3]));
			groups.add(m);
		}
		return groups;
	}

}
