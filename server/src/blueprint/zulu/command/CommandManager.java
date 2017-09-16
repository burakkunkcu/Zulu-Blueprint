package blueprint.zulu.command;

import java.util.HashMap;
/*
 * CommandManager - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class CommandManager
{
	// Properties
	private HashMap<String, ZuluCommand> commands = new HashMap<String, ZuluCommand>();
	
	 // Default constructor
	public CommandManager()
	{
		// Register all commands
		registerCommand( "calendar", new CalendarCommand());
		registerCommand( "login", new LoginCommand());
		registerCommand( "message", new MessageCommand());
		registerCommand( "project", new ProjectCommand());
		registerCommand( "register", new RegisterCommand());
		registerCommand( "task", new TaskCommand());
		registerCommand( "user", new UserCommand());
		registerCommand( "dashboard", new DashboardCommand());

	}
	
	// Method to register a command
	public void registerCommand( String base, ZuluCommand command)
	{
		commands.put( base, command);
	}
	
	// Method to execute a command and return the feedback object
	public Object executeCommand( String client, String command, String identifier, String args[])
	{	
		Object result = null;
		
		// Check if command exists
		if (commands.containsKey( command))
			try 
			{
				// Try to execute the command
				result = commands.get( command).execute( client, identifier, args);
			} 
			catch (Exception e) 
			{
				// Print error if command couldn't be executed
				System.out.println( "Error occured while executing command...");
				e.printStackTrace();
			}
		else
			// Print error if command doesn't exist
			System.out.println( "Error: Command not found!");
		
		return result;
	}
}