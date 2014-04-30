package com.electronic.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class AboutGroup {
public boolean processAddGroup(String group_name,String user_name) throws ClientProtocolException, IOException
	{
		String uriAPI = IPAddress.IP+"/ElectronicServer/addgroup.jsp";
	    String strRet = "";
	    DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpPost httpost = new HttpPost(uriAPI);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("group_name", group_name)); 
	    nvps.add(new BasicNameValuePair("user_name", user_name)); 
	    try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			strRet = EntityUtils.toString(entity);
			strRet = strRet.trim();
			if (entity != null)
		    {
		       entity.consumeContent();
		    }
			
			if(strRet.equals("true"))
		    {
		       System.out.print("添加成功");
		       return true;
		    }
			else
			{
			   System.out.print("添加失败");
    		   return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
public String processListAllGroup(String user_name) throws ClientProtocolException, IOException
	{
		String uriAPI = IPAddress.IP+"/ElectronicServer/getgroups.jsp";
	    String strRet = "";
	    DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpPost httpost = new HttpPost(uriAPI);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("user_name", user_name)); 
	    try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			strRet = EntityUtils.toString(entity);
			strRet = strRet.trim();
			System.out.println(strRet+".......");
			if (entity != null)
		    {
		       entity.consumeContent();
		    }
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strRet;
	}
public boolean processIsHaveThisGroup(String user_name,String group_name ) throws ClientProtocolException, IOException
	{
	String uriAPI = IPAddress.IP+"/ElectronicServer/havethisgroup.jsp";
    String strRet = "";
    DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpResponse response;
	HttpPost httpost = new HttpPost(uriAPI);
	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	nvps.add(new BasicNameValuePair("user_name", user_name)); 
	nvps.add(new BasicNameValuePair("group_name", group_name)); 
    try {
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		strRet = EntityUtils.toString(entity);
		strRet = strRet.trim();
		if (entity != null)
	    {
	       entity.consumeContent();
	    }
		System.out.println(strRet);
		if(strRet.equals("true"))
	    {
	       System.out.println("该用户此组存在");
	       return true;
	    }
		else
		{
		   System.out.println("该用户此组不存在");
		   return false;
		}
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
		
	}


}
