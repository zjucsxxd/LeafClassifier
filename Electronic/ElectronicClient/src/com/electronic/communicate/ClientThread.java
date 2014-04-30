package com.electronic.communicate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket socket;
	public ClientThread(Socket socket) {
		super();
		this.socket = socket;
	}
	@Override
	public void run() {
		super.run();
		while (true)
		{
			ObjectInputStream in;
			try {
				in = new ObjectInputStream(socket.getInputStream());
				Object object=in.readObject();
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	

}
