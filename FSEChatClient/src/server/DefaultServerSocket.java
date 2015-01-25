package server;

import java.io.IOException;
import java.net.ServerSocket;

import socket.DefaultSocket;

public class DefaultServerSocket extends DefaultSocket implements
		ServerSocketConstants, ServerSocketInterface {

	public ServerSocket serversocket;
	private int port;


	public DefaultServerSocket(int port) {
		super();
		this.port = port;
	}
	
	public void run() {
		if(initializeServerSocket()) {
			while(true) {
				try{
					setSock(serversocket.accept());
					openConnection();
					handleServerSession();
					//closeSession();
				} catch (IOException e) {
					closeServerSession();
				}
			}
			
		}
	}
	@Override
	public boolean initializeServerSocket() {
		// TODO Auto-generated method stub
		try {
			serversocket = new ServerSocket(port);
			if(DEBUG) {
				System.out.println("Server is on;");
			}
		} catch(Exception e) {
			if(DEBUG) {
				System.out.println("Server error;");
				e.printStackTrace();
			}
			System.exit(1);
			return false;
		}
		return true;

	}



	@Override
	public void handleServerSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeServerSession() {
		// TODO Auto-generated method stub
		try {
			serversocket.close();
			if(DEBUG) {
				System.out.println("Server is off;");
			}
		} catch (Exception e) {
			
		}

	}
	

}
