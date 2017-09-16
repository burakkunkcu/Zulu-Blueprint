package blueprint.zulu.command;

import blueprint.zulu.util.User;
/*
 * RegisterCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class RegisterCommand extends ZuluCommand
{
	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception
	{
		
		// args[0] - E-mail
		// args[1] - Name
		// args[2] - Password
		return User.registerNewUser( args[0], args[1], args[2]);
		
	}
}
