package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DefaultSocket extends Thread implements
		SocketConstants, SocketInterface {

	private BufferedReader reader;
	private PrintWriter writer;
	public Socket sock;
	public boolean isClosed;

	public BufferedReader getReader() {
		return reader;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	
	public DefaultSocket() {
		
	}
	public DefaultSocket(Socket sock) {
		this.sock = sock;
		isClosed = false;
	}
	
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	public void run() {
		if(openConnection()) {
			handleSession();
			closeSession();
		}
	}
	@Override
	public boolean openConnection() {
		// TODO Auto-generated method stub
		if(sock == null) {
			return false;
		}
		else {
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				writer = new PrintWriter(sock.getOutputStream(), true);
			} catch(Exception e) {
				return false;
			}
			return true;
		}
	}

	@Override
	public void handleSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeSession() {
		// TODO Auto-generated method stub
		try {
			writer.close();
			reader.close();
			writer = null;
			reader = null;
			sock.close();
			isClosed = true;
		} catch(IOException e) {
			
		}
	}

}
