package gwclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class SoapManager 
{
	public static String soapToXML(String soapStr) throws IOException,
			SOAPException, TransformerException {
		// Convert to SOAPMessage and extract body as DOM Document
		InputStream is = new ByteArrayInputStream(soapStr.getBytes());
		
		SOAPMessage request = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is);

		SOAPBody body = request.getSOAPBody();
		Document doc = body.extractContentAsDocument();

		// Convert Document to XML String
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		String str = writer.toString();

		return str;
	}

	public static String XMLToSoap(String xmlStr) throws SAXException,
			IOException, ParserConfigurationException, SOAPException {
		// Convert to DOM Document
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		InputSource source = new InputSource(new StringReader(xmlStr));
		Document document = factory.newDocumentBuilder().parse(source);

		// Create SOAPMessage
		MessageFactory soapFactory = MessageFactory
				.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SOAPMessage message = soapFactory.createMessage();
		SOAPBody body = message.getSOAPBody();
		SOAPHeader header = message.getSOAPHeader();
		header.detachNode();

		// Add DOM Document to SOAP Body
		body.addDocument(document);

		// Convert envelope to SOAP String
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		message.writeTo(out);
		String soapStr = new String(out.toByteArray());
		soapStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + soapStr; 

		return soapStr;
	}
}
