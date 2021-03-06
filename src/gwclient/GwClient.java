package gwclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;



public class GwClient {

	public static void main(String[] args) throws SOAPException, IOException, SAXException, ParserConfigurationException, TransformerException 
	{
		
		RequestXccRegister req = new RequestXccRegister("gwclient", 
														"http://192.168.1.55:8090/gwclient", 
														1, 
														"CONTINUE_PROCESSING", 
														"CREATED AUTHORIZE_CALL ADDRESS_ANALYZE REDIRECTED ALERTING CONNECTED TRANSFERRED CALL_DELIVERY DISCONNECTED HANDOFFLEAVE HANDOFFJOIN",
														"MODE_CHANGE DTMF TONE_BUSY TONE_DIAL TONE_SECOND_DIAL TONE_RINGBACK TONE_OUT_OF_SERVICE MEDIA_ACTIVITY",
														"11111d",
														"http://192.168.1.250/cisco_xcc");
		
		String xml = req.createSOAPMsg();
		
	    Socket socket = new Socket(InetAddress.getByName("192.168.1.250"), 8090, InetAddress.getByName("192.168.1.55"), 8090);
	    
	    String path = "/cisco_xcc";
	    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
	    wr.write("POST " + path + " HTTP/1.1\r\n");
	    wr.write("Host: 192.168.1.250:8090\r\n");
	    wr.write("Content-Length: " + xml.length() + "\r\n");
	    wr.write("Content-Type: application/soap+xml; charset=utf-8\r\n");
	    wr.write("\r\n");

	    wr.write(xml);
	    wr.flush();
	    
	    //System.out.print(xml);

	    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    
	    String line;
	    char[] buf = new char[1000];
	    
	    int headerRowCount = 1;
	    String ctlLength = "";
	    while ((line = rd.readLine()) != null && !line.isEmpty()) 
	    {
	    	if(headerRowCount == 4)
	    	{
		      ctlLength = line.substring(16);
	    	}
	    	headerRowCount++;
		}
	    
	    rd.read(buf, 0, Integer.parseInt(ctlLength));
	    	    
	    String response = new String(buf);
	    
	    ResponseXccRegister r = new ResponseXccRegister(response);
	    
	    wr.close();
	    rd.close();
	    socket.close();
	    
	    XccServer server = new XccServer();
	    server.setListening(true);
	    server.start();
	}

}
