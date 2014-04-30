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



public class AboutFriend {
	public boolean processAddFriend(String user_name,String friend_name,String group_name) throws ClientProtocolException, IOException
	{
		String uriAPI = IPAddress.IP+"/ElectronicServer/addfriend.jsp";
	    String strRet = "";
	    DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpPost httpost = new HttpPost(uriAPI);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
	    nvps.add(new BasicNameValuePair("user_name", user_name)); 
	    nvps.add(new BasicNameValuePair("friend_name", friend_name)); 
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
			
			if(strRet.equals("true"))
		    {
		       System.out.print("添加好友成功");
		       return true;
		    }
			else
			{
			   System.out.print("添加好友失败");
    		   return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	public String processListAllFriends(String user_name,int group_id) throws ClientProtocolException, IOException
	{String uriAPI = IPAddress.IP+"/ElectronicServer/getfriends.jsp";
    String strRet = "";
    DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpResponse response;
	HttpPost httpost = new HttpPost(uriAPI);
	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    nvps.add(new BasicNameValuePair("user_name", user_name)); 
    nvps.add(new BasicNameValuePair("group_id", String.valueOf(group_id))); 
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
		
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	return strRet;
	}
}
