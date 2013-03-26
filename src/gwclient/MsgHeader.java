package gwclient;

public class MsgHeader
{
	private String transactionID;
	private String registrationID;
	
	public MsgHeader(String tranID, String regID)
	{
		this.transactionID = tranID;
		this.registrationID = regID;
	}
	
	public MsgHeader(String tranID)
	{
		this.transactionID = tranID;
		this.registrationID = null;
	}
}
