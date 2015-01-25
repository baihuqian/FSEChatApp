package client;

import java.io.IOException;
import java.net.Socket;

import socket.DefaultSocket;

public class DefaultClientSocket extends DefaultSocket {
	private String strHost;
	private int iPort;
	
	public DefaultClientSocket(String strHost, int iPort) {
		super();
		setHost(strHost);
		setPort(iPort);
	}
	public void setHost(String strHost) {
		this.strHost = strHost;
	}
	public void setPort(int iPort) {
		this.iPort = iPort;
	}
	public boolean openConnection() {
		// TODO Auto-generated method stub
		try {
			//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			sock = new Socket(strHost, iPort);
			if(DEBUG) {
				System.out.println("client is on;");
			}
		} catch (IOException e) {
			return false;
		}
		return super.openConnection();
	}
	

}
