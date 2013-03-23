package gwclient;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RequestXccRegister {
	
	private ApplicationData applicationData;
	private int blockingEventTimeoutSec;
	private String blockingTimeoutHandle;
	private String connectionEventsFilter;
	private String mediaEventsFilter;
	private MsgHeader msgHeader;
	private ProviderData providerData;
	private final String xmlns = "http://www.cisco.com/schema/cisco_xcc/v1_0";
	
	public RequestXccRegister(String appName, String appUrl, int blockingEventTimeoutSec, String blockingTimeoutHandle, 
			String connectionEventsFilter, String mediaEventsFilter, String transactionId, String providerUrl)
	{
		this.applicationData = new ApplicationData(appName, appUrl);
		this.blockingEventTimeoutSec = blockingEventTimeoutSec;
		this.blockingTimeoutHandle = blockingTimeoutHandle;
		this.connectionEventsFilter = connectionEventsFilter;
		this.mediaEventsFilter = mediaEventsFilter;
		this.msgHeader = new MsgHeader(transactionId);
		this.providerData = new ProviderData(providerUrl);
	}
	
	public RequestXccRegister(String soapMsg) throws IOException, SOAPException, TransformerException
	{
		String str = SoapManager.soapToXML(soapMsg);
		
	    //Deserialize XML string
	    XStream xstream = new XStream(new DomDriver());
		xstream.alias("RequestXccRegister", RequestXccRegister.class);
		xstream.alias("applicationData", ApplicationData.class);
		xstream.alias("msgHeader", MsgHeader.class);
		xstream.alias("providerData", ProviderData.class);
		
		RequestXccRegister r = (RequestXccRegister)xstream.fromXML(str);
		
		this.applicationData = r.applicationData;
		this.blockingEventTimeoutSec = r.blockingEventTimeoutSec;
		this.blockingTimeoutHandle = r.blockingTimeoutHandle;
		this.connectionEventsFilter = r.connectionEventsFilter;
		this.mediaEventsFilter = r.mediaEventsFilter;
		this.msgHeader = r.msgHeader;
		this.providerData = r.providerData;
	}
	
	public String createSOAPMsg() throws SOAPException, IOException, SAXException, ParserConfigurationException, TransformerException
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.useAttributeFor(RequestXccRegister.class, "xmlns");
		
		xstream.alias("RequestXccRegister", RequestXccRegister.class);
		xstream.alias("applicationData", ApplicationData.class);
		xstream.alias("msgHeader", MsgHeader.class);
		xstream.alias("providerData", ProviderData.class);
		
		//Serialize to XML
		String xml = xstream.toXML(this);
		
		String soapMsg = SoapManager.XMLToSoap(xml);

		return soapMsg;
	}

}
