package chat;

import server.ServerReceiveSocket;

public class FSEChatServer {
	private static String host = "localhost";
	private static int rPort = 4444;
	private static int sPort = 4445;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerReceiveSocket receiveSock = new ServerReceiveSocket(rPort);
		receiveSock.start();
		
	}

}
