package server;

import java.io.IOException;

public class ClientReceiveSocket extends DefaultServerSocket {

	public ClientReceiveSocket(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleServerSession() {
		// TODO Auto-generated method stub
		try {
			System.out.println(getReader().readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
