package blueprint.zulu.command;

import blueprint.zulu.util.Project;
import blueprint.zulu.util.User;
/*
 * UserCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class UserCommand extends ZuluCommand
{
	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception
	{
		Object result = null;
		
	    if (identifier.equals( "getbyid"))
	    	// args[0] - UserID
			result = User.getByID( args[0]);
	    else if(identifier.equals("getbyemail"))
	    	// args[0] - UserMail
	    	result = User.getByEmail( args[0]);
	    
	    else if (identifier.equals( "getname"))
	    	// args[0] - UserID
			result = User.getByID( args[0]).getName();
	    
	    else if (identifier.equals( "getemail"))
	    	// args[0] - UserID
	    	result = User.getByID( args[0]).getEmail();
	    
	    else if (identifier.equals( "getdesc"))
	    	// args[0] - UserID
	    	result = User.getByID( args[0]).getDesc();
		
		else if (identifier.equals( "setname"))
			// args[0] - UserID
			// args[1] - New name
			User.getByID( args[0]).setName( args[1]);
		
		else if (identifier.equals( "setdesc"))
			// args[0] - UserID
			// args10] - New desc
			User.getByID( args[0]).setDesc( args[1]);
	    
		else if (identifier.equals( "hasperm"))
			// args[0] - UserID
			// args[1] - Permission
			result = User.getByID( args[0]).hasPermission( args[1]);
	    
		else if (identifier.equals( "addperm"))
			// args[0] - UserID
			// args[1] - Permission
			User.getByID( args[0]).addPermission( args[1]);
	    
		else if (identifier.equals( "removeperm"))
			// args[0] - UserID
			// args[1] - Permission
			User.getByID( args[0]).removePermission( args[1]);
	    
		else if (identifier.equals( "getCalendar"))
			// args[0] - UserID
			result = User.getByID( args[0]).getCalendar();
	    
		else if (identifier.equals( "getprojects"))
			// args[0] - UserID
			result = User.getByID( args[0]).getProjects();
	    
		else if (identifier.equals( "getcalendar"))
			// args[0] - UserID
			result = User.getByID( args[0]).getCalendar();
	    
		else if (identifier.equals( "addproject"))
		{
			// args[0] - UserID
			// args[1] - ProjectID
			User user = User.getByID( args[0]);
			Project project = Project.getByID( args[1]);
			
			project.addUser( user.getID());
			user.addProject( project.getID());
		}
	    
		else if (identifier.equals( "removeproject"))
		{
			// args[0] - UserID
			// args[1] - ProjectID
			User user = User.getByID( args[0]);
			Project project = Project.getByID( args[1]);
			
			project.removeUser( user.getID());
			user.removeProject( project.getID());
		}
	    
		else if (identifier.equals( "tostring"))
			// args[0] - UserID
			result = User.getByID( args[0]).toString();
		
		return result;
	}
}
