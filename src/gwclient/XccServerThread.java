package gwclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class XccServerThread extends Thread
{
	private Socket socket;
	
	public XccServerThread(Socket s)
	{
		super("XccServerThread");
		socket = s;
	}
	
	public void run()
	{
		try 
		{
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        String message = readFromSocket(socket);
	        System.out.println(message);
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
	}
	
	public String readFromSocket(Socket s) throws IOException
	{
		BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String line;
	    char[] buf = new char[1000];
	    String ctlLength = "";
	    while ((line = rd.readLine()) != null && !line.isEmpty()) 
	    {
	    	if(line.startsWith("Content-Length:"))
	    	{
		      ctlLength = line.substring(16);
	    	}
	    }
	    rd.read(buf, 0, Integer.parseInt(ctlLength)); 
	    
	    socket.close();
	    rd.close();
	    
	    String response = new String(buf);
	    response = response.trim();
	    return response;
	}
}
