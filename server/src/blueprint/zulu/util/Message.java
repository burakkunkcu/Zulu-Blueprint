package blueprint.zulu.util;

import java.io.Serializable;

/*
 * Message - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Message implements Serializable {
	
	
	// Properties
	private static final long serialVersionUID = -997786490581655662L;
	private String senderID;
	private String messageBody;
	
	// Constructor
	
	public Message( String senderID, String messageBody)
	{
		this.senderID = senderID;
		this.messageBody = messageBody;
	}
	
	// Method to get sender's ID
	public String getSenderID()
	{
		return senderID;
	}
	
	// Method to get message body
	public String getMessageBody()
	{
		return messageBody;
	}
	
	
	// String representation of the Message class
	public String toString()
	{
		return "User: " + this.getSenderID() + " Message: " +  this.getMessageBody();
	}
}
