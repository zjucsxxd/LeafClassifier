package com.electronic.socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.electronic.communicate.Position;



public class ServerThread extends Thread {
	private Socket socket;
	private List<Socket> others;
	public ServerThread(Socket socket) {
		super();
		this.socket = socket;
	}
	public void setOthers(List<Socket> others) {
		this.others = others;
	}
	@Override
	public void run() {

		try {
			while (true) {
				try {
					
					ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
					
					//Object object=objIn.readObject();
					String message=(String)objIn.readObject();
					System.out.println(message);
//					if(object instanceof Position)
//					{
//						
//					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			((Throwable) e).printStackTrace();
        }
	
	}
	
	

}
