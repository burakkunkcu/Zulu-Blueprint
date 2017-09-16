package blueprint.zulu.command;

import java.util.Date;

import com.google.gson.Gson;

import blueprint.zulu.util.Project;
import blueprint.zulu.util.User;
/*
 * ProjectCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class ProjectCommand extends ZuluCommand 
{
	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception
	{
		Object result = null;
		Gson gson = new Gson();
		
		if (identifier.equals( "create"))
			// args[0] - Project name
			// args[1] - Project desc
			// args[2] - LeaderID
			result = createProject( args[0], args[1], args[2]);
		
		else if (identifier.equals( "get"))
			// args[0] - ProjectID
			result = Project.getByID( args[0]);
		
		else if (identifier.equals( "setname"))
			// args[0] - ProjectID
			// args[1] - New name
			Project.getByID( args[0]).setName( args[1]);
		
		else if (identifier.equals( "setdesc"))
			// args[0] - ProjectID
			// args[1] - New desc
			Project.getByID( args[0]).setDesc( args[1]);
		
		else if (identifier.equals( "changeleader"))
			// args[0] - ProjectID
			// args[0] - New LeaderID
			Project.getByID( args[0]).changeLeader( args[1]);
		
		else if (identifier.equals( "adduserbyid"))
		{
			// args[0] - ProjectID
			// args[1] - UserID
			User user = User.getByID( args[1]);
			Project project = Project.getByID( args[0]);

			project.addUser( user.getID());
			user.addProject( project.getID());
		}
		
		else if (identifier.equals( "adduserbyemail"))
		{
			// args[0] - ProjectID
			// args[1] - User email
			User user = User.getByEmail( args[1]);
			Project project = Project.getByID( args[0]);

			project.addUser( user.getID());
			user.addProject( project.getID());
		}
		
		else if (identifier.equals( "removeuser"))
		{
			// args[0] - ProjectID
			// args[1] - UserID
			User user = User.getByID( args[1]);
			Project project = Project.getByID( args[0]);
			
			project.removeUser( user.getID());
			user.removeProject( project.getID());
		}
		
		else if (identifier.equals( "getcalendar"))
			// args[0] - ProjectID
			result = Project.getByID( args[0]).getCalendar();
		
		else if (identifier.equals( "getusercalendars"))
			// args[0] - ProjectID
			result = Project.getByID( args[0]).getUserCalendars();
		
		else if (identifier.equals( "meetup"))
		{
			// args[0] - ProjectID
			// args[0] - Date format
			Date meetDate = gson.fromJson(args[1], Date.class);
			result = Project.getByID( args[0]).meetUp( meetDate);
		}
		
		else if (identifier.equals( "tostring"))
			// args[0] - ProjectID
			result = Project.getByID( args[0]).toString();
			
		return result;
	}
	
	// Method to create a project
	private boolean createProject( String name, String desc, String leaderID)
	{
		Project project = Project.createNewProject( name, desc, leaderID);
		boolean result = false;
		
		if (project != null)
		{
			User.getByID( leaderID).addProject( project.getID());
			result = true;
		}
		
		return result;
	}
}
