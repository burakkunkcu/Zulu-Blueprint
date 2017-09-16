package blueprint.zulu.command;

import blueprint.zulu.database.Mongo;
/*
 * LoginCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class LoginCommand extends ZuluCommand 
{
	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception
	{
		if( identifier.equals("login"))
		{
			//args[0] - E-mail
			//args[1] - Password
			System.out.println(args[0]);
			return Mongo.login( args[0], args[1]);
		}
		else if( identifier.equals("salt"))
		{
			return Mongo.returnSalt(args[0]);
		} 
		return null;
	}
}
