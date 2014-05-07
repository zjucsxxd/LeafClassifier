package com.electronic.communicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendFile {
	private String filePath;

	public SendFile(String filePath) {
		super();
		this.filePath = filePath;
	}

	public boolean send() throws UnknownHostException, IOException
	{
		try {
			Socket socket=new Socket("10.0.2.2",2001);
			InputStream in =new FileInputStream(filePath);
			OutputStream out=socket.getOutputStream();
			byte buffer[]=new byte[4*1024];
			int temp=0;
			while((temp=in.read(buffer))!=-1)
			{
				out.write(buffer,0,temp);
			}
		    out.flush();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	
		return false;
	}
	
	

}
