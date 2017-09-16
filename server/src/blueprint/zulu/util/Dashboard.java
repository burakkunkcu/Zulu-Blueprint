package blueprint.zulu.util;

public class Dashboard {
	private String senderID;
	private String dashBody;
	
	// Constructor
	
	public Dashboard( String senderID, String dashBody)
	{
		this.senderID = senderID;
		this.dashBody = dashBody;
	}
	
	// Method to get sender's ID
	public String getSenderID()
	{
		return senderID;
	}
	
	// Method to get message body
	public String getDashBody()
	{
		return dashBody;
	}

	
	// String representation of the Message class
	public String toString()
	{
		return this.getSenderID() +  this.getDashBody();
	}
}