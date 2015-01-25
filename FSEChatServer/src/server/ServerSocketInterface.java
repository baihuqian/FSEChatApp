package server;


public interface ServerSocketInterface {
	public boolean initializeServerSocket();
	public void handleServerSession();
	public void closeServerSession();
}
