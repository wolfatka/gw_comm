package gwclient;

import java.io.IOException;
import java.net.ServerSocket;

public class XccServer 
{
	private ServerSocket serverSocket;
	private boolean listening = false;
	
	public XccServer ()
	{
		try 
		{
			serverSocket = new ServerSocket(8090);
		} 
		catch (IOException e) 
		{
			System.out.println("ServerSocket error");
			e.printStackTrace();
		}
	}
	
	public void setListening(boolean value)
	{
		listening = value;
	}
	
	public void start() throws IOException
	{
		while (listening) new XccServerThread(serverSocket.accept()).start();
		
		serverSocket.close();
	}
}
