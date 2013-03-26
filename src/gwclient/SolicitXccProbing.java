package gwclient;

import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SolicitXccProbing 
{
	private MsgHeader msgHeader;
	private int sequence;
	private int interval;
	private int failureCount;
	private String registered;
	private String providerStatus;
	
	public SolicitXccProbing(String soapMsg) throws IOException, SOAPException, TransformerException
	{
		String str = SoapManager.soapToXML(soapMsg);
		
	    //Deserialize XML string
	    XStream xstream = new XStream(new DomDriver());
		xstream.alias("SolicitXccProbing",SolicitXccProbing.class);
		xstream.alias("msgHeader", MsgHeader.class);
		
		SolicitXccProbing r = (SolicitXccProbing)xstream.fromXML(str);
	
		this.msgHeader = r.msgHeader;
		this.sequence = r.sequence;
		this.interval = r.interval;
		this.failureCount = r.failureCount;
		this.registered = r.registered;
		this.providerStatus = r.providerStatus;

	}

}
