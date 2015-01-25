package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.ClientReceiveSocket;
import client.ClientSendSocket;

public class FSEChatClient {
	private static String host = "localhost";
	private static int sPort = 4444;
	private static int rPort = 4445;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Please enter your username:");
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final String userName = br.readLine();


		//ClientReceiveSocket receiveSock = new ClientReceiveSocket(rPort);
		//receiveSock.start();
		final ClientSendSocket sendSock = new ClientSendSocket(host, sPort);
		if(!sendSock.openConnection()) {
			System.out.println("Cannot open socket!");
			System.exit(2);
		}

		System.out.println("Connection created");
		Thread printT = new Thread(new Runnable() {

			@Override
			public void run() {

				String inMessage = null;
				try {
					while(true) {
						if(sendSock.getReader().ready()) {
							inMessage = sendSock.getReader().readLine();
							System.out.println(inMessage);
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		printT.start();

		Thread inputT = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String content = null;
				try {
					while(true) {
						System.out.println("Waiting for input... Enter end to exit.");
						content = br.readLine();
						if(content.equalsIgnoreCase("end")) {
							//sendSock.closeSession();
							//receiveSock.closeServerSession();
							System.exit(0);
						}
						String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

						sendSock.getWriter().println(userName + "\t" + time + "\t" + content);
						sendSock.getWriter().flush();
						Thread.sleep(1000);
					}
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					//thread.stop();
					sendSock.closeSession();
					//receiveSock.closeServerSession();
				}
			}

		});
		inputT.start();

	}

}
