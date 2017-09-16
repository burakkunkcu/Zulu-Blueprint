package blueprint.zulu.command;

import blueprint.zulu.database.Mongo;
import blueprint.zulu.util.Task;
import blueprint.zulu.util.Calendar;
import blueprint.zulu.util.Dashboard;
import blueprint.zulu.util.Project;
/*
 * TaskCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class TaskCommand extends ZuluCommand
{

	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception 
	{
		Object result = null;

		if (identifier.equals( "create"))
		{
			// args[0] - Task desc
			// args[1] - Task date
			// args[2] - CalendarID
			String tmp = "New task has added: " + args[0] + " due to " + args[1] + ".";
			Dashboard d = new Dashboard("" , tmp);
			Mongo.insertDash(args[2], d);
			
			String task = Task.createNewTask( args[0], args[1], args[2]);
			Calendar.getByID( args[2]).addTask( task);
			result = task;
		}
		
		else if (identifier.equals( "get"))
			// args[0] - TaskID
			result = Task.getByID( args[0]);
		
		else if (identifier.equals( "getprojectid"))
			// args[0] - TaskID
			result = Task.getByID( args[0]).getProjectID();
		
		else if (identifier.equals( "getdesc"))
			// args[0] - TaskID
			result = Task.getByID( args[0]).getDesc();
		
		else if (identifier.equals( "getprogress"))
			// args[0] - TaskID
			result = Task.getByID( args[0]).getProgress();
		
		else if (identifier.equals( "getdate"))
			// args[0] - TaskID
			result = Task.getByID( args[0]).getDate();
		
		else if (identifier.equals( "setdesc"))
			// args[0] - TaskID
			// args[1] - New desc
			Task.getByID( args[0]).setDesc( args[1]);
		
		else if (identifier.equals( "setdate"))
			// args[0] - TaskID
			// args[1] - New date
			Task.getByID( args[0]).setDate( args[1]);
	    
		else if (identifier.equals( "setactive"))
		{
			// args[0] - TaskID
			Task task = Task.getByID( args[0]);
			task.setActive( !task.isActive());
		}
		
		else if (identifier.equals( "setprogress"))
			// args[0] - TaskID
			// args[1] - New progress status
			Task.getByID( args[0]).setProgress( Integer.parseInt(args[1]));
		
		else if (identifier.equals( "isactive"))
			// args[0] - TaskID
			// args[1] - New active status
			result = Task.getByID( args[0]).isActive();
		
		else if (identifier.equals( "tostring"))
			// args[0] - TaskID
			result = Task.getByID( args[0]).toString();
		
		return result;
	}
}
