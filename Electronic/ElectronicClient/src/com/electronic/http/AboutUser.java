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


public class AboutUser {
	 public boolean processInternetLogin(String username, String pwd) throws ClientProtocolException, IOException
	 {
		  String uriAPI = IPAddress.IP+"/ElectronicServer/login.jsp";
		  String strRet = "";
	      try {
	    	DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			HttpPost httpost = new HttpPost(uriAPI);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username)); 
		    nvps.add(new BasicNameValuePair("pwd", pwd)); 
		//    System.out.println(username+"..."+pwd);
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			strRet = EntityUtils.toString(entity);
			strRet = strRet.trim().toLowerCase();
		//	System.out.println(strRet+"*****");
			if(strRet.equals("1"))
		      {
		        System.out.print("可登录成功");
		        return true;
		      }
			else
			{
				 System.out.print("密码错误");
				return false;
			}
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return false;
		 
	 }
	 public boolean processInternetRegister(String username, String pwd,String sex,int old) throws ClientProtocolException, IOException
	 {
		 String uriAPI = IPAddress.IP+"/ElectronicServer/register.jsp";
		 String strRet = "";
		
	     try {
	    	DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response;
		    HttpPost httpost = new HttpPost(uriAPI);
		    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		    nvps.add(new BasicNameValuePair("username", username)); 
		    nvps.add(new BasicNameValuePair("pwd", pwd));
		    nvps.add(new BasicNameValuePair("sex", sex));
		    nvps.add(new BasicNameValuePair("old", String.valueOf(old)));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
		    HttpEntity entity = response.getEntity();
		    strRet = EntityUtils.toString(entity);
		    strRet = strRet.trim().toLowerCase();
		    System.out.println(strRet+".....");
		    if (entity != null)
		      {
		        entity.consumeContent();
		      }
		    if(strRet.equals("true"))
		      {
		        
		        return true;
		      }
		    else
		      {
		        return false;
		      }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return false;
	 }

		public boolean processIsHaveThisUser(String name ) throws ClientProtocolException, IOException
		{String uriAPI = IPAddress.IP+"/ElectronicServer/havethisuser.jsp";
	    String strRet = "";
	    DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpPost httpost = new HttpPost(uriAPI);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("name", name)); 
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
		       System.out.println("此用户存在");
		       return true;
		    }
			else
			{
			   System.out.println("此用户不存在");
			   return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
			
		}


}
