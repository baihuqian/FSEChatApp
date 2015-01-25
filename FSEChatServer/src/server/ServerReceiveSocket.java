package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import socket.DefaultSocket;

public class ServerReceiveSocket extends DefaultServerSocket {
	private List<DefaultSocket> sockets; 
	public ServerReceiveSocket(int port) {
		super(port);
		sockets = Collections.synchronizedList(new ArrayList<DefaultSocket>());
		if(DEBUG) {
			System.out.println("Server socket created.");
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(initializeServerSocket()) {
			Thread incoming = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					DefaultSocket incoming;
					try {
						while(true) {
							incoming = new DefaultSocket(serversocket.accept());
							//setSock(incoming);
							incoming.openConnection();
							
							synchronized(sockets) {
								sockets.add(incoming);
								if(DEBUG) {
									System.out.println("Incoming connection established. Current connection number: " + sockets.size());
								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
			incoming.start();
		}
		handleServerSession();

	}

	@Override
	public void closeServerSession() {
		// TODO Auto-generated method stub
		for(DefaultSocket s : sockets) {
			s.closeSession();
		}
		super.closeServerSession();
	}




	public void handleServerSession() {
		// TODO Auto-generated method stub
		try {
			String message = null;
			while(true) {
				synchronized(sockets) {
					for(DefaultSocket s : sockets) {
						if(s.isClosed) {
							sockets.remove(s);
							break;
						}

						if(s.getReader().ready()) {
							message = s.getReader().readLine();
							if(DEBUG) {
								System.out.println("Message received: " + message);
							}
							for(DefaultSocket ss : sockets) {
								ss.getWriter().println(message);
								ss.getWriter().flush();
							}
							if(DEBUG) {
								System.out.println("Message sent.");
							}
						}
					}
				}
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
