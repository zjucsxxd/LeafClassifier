package com.electronic.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FileServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket fileServerSocket;
		
		try {
			fileServerSocket =new ServerSocket(2001);
			
			List<Socket> allClients=new ArrayList<Socket>();
			while (true)
			{
				Socket fileSocket=fileServerSocket.accept();
				
				System.out.println("....");
				allClients.add(fileSocket);
				FileServerThread fileThread=new FileServerThread(fileSocket);
				fileThread.setOthers(allClients);
				fileThread.start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
