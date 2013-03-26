package gwclient;

import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ResponseXccRegister 
{
	private MsgHeader msgHeader;
	private String providerStatus;
	
	public ResponseXccRegister(String soapMsg) throws IOException, SOAPException, TransformerException
	{
		String str = SoapManager.soapToXML(soapMsg);
		
	    //Deserialize XML string
	    XStream xstream = new XStream(new DomDriver());
		xstream.alias("ResponseXccRegister",ResponseXccRegister.class);
		xstream.alias("msgHeader", MsgHeader.class);
		
		ResponseXccRegister r = (ResponseXccRegister)xstream.fromXML(str);
	
		this.msgHeader = r.msgHeader;
		this.providerStatus = r.providerStatus;

	}
}
