package com.electronic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.electronic.communicate.Position;

public class FileServerThread extends Thread {
	private Socket socket;
	private List<Socket> others;
	public FileServerThread(Socket socket) {
		super();
		this.socket = socket;
	}
	public void setOthers(List<Socket> others) {
		this.others = others;
	}
	@Override
	public void run() {
		
		while(true)
		{
			try {
				InputStream inputStream=socket.getInputStream();
				byte buffer[]=new byte[4*1024];
				int temp=0;
				while((temp=inputStream.read(buffer))!=-1)
				{
					System.out.println(new String(buffer,0,temp));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
