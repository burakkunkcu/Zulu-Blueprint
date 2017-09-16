package blueprint.zulu.command;

import blueprint.zulu.database.Mongo;
import blueprint.zulu.util.Message;

/*
 * MessageCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class MessageCommand extends ZuluCommand 
{
	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception
	{
		Object result =  null;
		
		if (identifier.equals( "get"))
			// args[0] - ProjectID
			Mongo.getMessages( args[0]);
		
		else if (identifier.equals( "send"))
			// args[0] - SenderID
			// args[1] - ProjectID
			// args[2] - Message body
			// args[3] - Time stamp
			Mongo.insertMessage( args[1], new Message( args[0], args[2]));
		
		return result;
	}
}
