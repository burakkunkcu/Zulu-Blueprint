package blueprint.zulu.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import blueprint.zulu.util.Calendar;
/*
 * CalendarCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class CalendarCommand extends ZuluCommand
{
	@Override
	public Object execute(String client, String identifier, String[] args) throws Exception
	{
		Object result = null;
		
		if (identifier.equals( "create"))
			result = Calendar.createNewCalendar();
		
		else if (identifier.equals( "get"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]);
		
		else if (identifier.equals( "gettasksbydate"))
		{
			try 
			{
				// args[0] - CalendarID
				// args[1] - Date format
				result = Calendar.getByID( args[0]).getTasksByDate( new SimpleDateFormat( "MM/dd/yyyy hh").parse( args[1]));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		else if (identifier.equals( "gettasks"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).getTasks();
		
		else if (identifier.equals( "gettasksofproject"))
			// args[0] - CalendarID
			// args[1] - ProjectID
			result = Calendar.getByID( args[0]).getTasksOfProject( args[1]);
		
		else if (identifier.equals( "getactivetasks"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).getActiveTasks();
		
		else if (identifier.equals( "getactivetasksofproject"))
			// args[0] - CalendarID
			// args[1] - ProjectID
			result = Calendar.getByID( args[0]).getActiveTasksOfProject( args[1]);
		
		else if (identifier.equals( "getpassivetasks"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).getPassiveTasks();
		
		else if (identifier.equals( "getpassivetasksofproject"))
			// args[0] - CalendarID
			// args[1] - ProjectID
			result = Calendar.getByID( args[0]).getPassiveTasksOfProject( args[1]);
		
		else if (identifier.equals( "getprogress"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).getProgress();
		
		else if (identifier.equals( "getactiveprogress"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).getActiveProgress();
		
		else if (identifier.equals( "merge"))
			// args[0] - CalendarID
			// args[1] - Merged CalendarID
			Calendar.getByID( args[0]).merge( Calendar.getByID( args[1]));
		
		else if (identifier.equals( "addtask"))
			// args[0] - CalendarID
			// args[1] - TaskID
			Calendar.getByID( args[0]).addTask( args[1]);
		
		else if (identifier.equals( "removetask"))
			// args[0] - CalendarID
			// args[1] - TaskID
			Calendar.getByID( args[0]).removeTask( args[1]);
		
		else if (identifier.equals( "tostring"))
			// args[0] - CalendarID
			result = Calendar.getByID( args[0]).toString();
		
		return result;
	}
}
