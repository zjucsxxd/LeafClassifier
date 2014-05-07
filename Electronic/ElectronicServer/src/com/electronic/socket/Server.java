package com.electronic.socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket;
		
		try {
			serverSocket =new ServerSocket(2003);
			List<Socket> allClients=new ArrayList<Socket>();
			while (true)
			{
				Socket socket=serverSocket.accept();
				System.out.println("....");
				allClients.add(socket);
				ServerThread thread=new ServerThread(socket);
				thread.setOthers(allClients);
				thread.start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
