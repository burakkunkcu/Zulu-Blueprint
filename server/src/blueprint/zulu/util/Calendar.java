package blueprint.zulu.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import blueprint.zulu.database.Mongo;
/*
 * Calendar - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Calendar 
{
	
	// Properties
	private String id;
	private ArrayList<String> tasks;

	// Constructor
	private Calendar( String id, ArrayList<String> tasks) 
	{
		this.id = id;
		this.tasks = tasks;
	}

	// Methods
	// Method to get CalendarID
	public String getID()
	{
		return this.id;
	}
	
	// Method to get tasks in a certain day
	@SuppressWarnings("deprecation")
	public ArrayList<Date> getTasksByDate( Date checkDate)
	{
		ArrayList<Date> catched = new ArrayList<Date>();
		ArrayList<Task> actives = getActiveTasks();
		
		for ( int j = 0; j < actives.size(); j++ ) 
		{
			if (actives.get(j).getDate().getDay() == checkDate.getDay()) 
				catched.add(actives.get(j).getDate());
		}

		if (catched.size() == 0)
			return null;
		
		return catched;
	}
	
	// Method to get all tasks
	public ArrayList<Task> getTasks()
	{
		ArrayList<Task> result = new ArrayList<Task>();
		
		for (String task : tasks)
			result.add( Mongo.getTask( task));
		
		return result;
	}
	
	public ArrayList<Task> getTasksOfProject( String projectID)
	{
		ArrayList<Task> result = new ArrayList<Task>();
		
		for (String task : tasks)
		{
			Task parsedTask = Mongo.getTask( task);
			
			if (parsedTask.getProjectID().equals( projectID))
				result.add( Mongo.getTask( task));
		}
		
		return result;
	}

	// Method to get only active tasks
	public ArrayList<Task> getActiveTasks() 
	{
		ArrayList<Task> actives;
		Iterator<Task> it;

		it = getTasks().iterator();
		actives = new ArrayList<Task>();

		while (it.hasNext()) 
		{
			Task check = (Task) it.next();
			
			if (check.isActive())
				actives.add(check);
		}

		return actives;
	}
	
	public ArrayList<Task> getActiveTasksOfProject( String projectID) 
	{
		ArrayList<Task> actives;
		Iterator<Task> it;

		it = getTasks().iterator();
		actives = new ArrayList<Task>();

		while (it.hasNext()) 
		{
			Task check = (Task) it.next();
			
			if (check.isActive() && check.getProjectID().equals( projectID))
				actives.add(check);
		}

		return actives;
	}
	
	// Method to get only passive tasks
	public ArrayList<Task> getPassiveTasks()
	{
		ArrayList<Task> passives;
		Iterator<Task> it;

		it = getTasks().iterator();
		passives = new ArrayList<Task>();

		while (it.hasNext()) 
		{
			Task check = (Task) it.next();
			
			if (!check.isActive())
				passives.add(check);
		}

		return passives;
	}
	
	public ArrayList<Task> getPassiveTasksOfProject( String projectID)
	{
		ArrayList<Task> passives;
		Iterator<Task> it;

		it = getTasks().iterator();
		passives = new ArrayList<Task>();

		while (it.hasNext()) 
		{
			Task check = (Task) it.next();
			
			if (!check.isActive() && check.getProjectID().equals( projectID))
				passives.add(check);
		}

		return passives;
	}


	// It calculates the average progress of the tasks in the ArrayList
	public int getProgress() 
	{
		int size;
		int sum;
		Iterator<Task> it;

		size = tasks.size();
		sum = 0;
		it = getTasks().iterator();

		while (it.hasNext()) 
			sum += ((Task) it.next()).getProgress();
		if(size == 0)
			return 0;
		else
			return sum/size;
	}

	// It calculates only the average progress of the active tasks
	public int getActiveProgress() 
	{

		int size;
		int sum;
		Iterator<Task> it;
		ArrayList<Task> actives;

		actives = getActiveTasks();
		size = actives.size();
		sum = 0;
		it = actives.iterator();

		while (it.hasNext())
			sum += ((Task) it.next()).getProgress();
		
		if(size == 0)
			return sum;
		else
			return sum / size;

	}
	
	// Method to add a task
	public void addTask( String task)
	{
		tasks.add( task);
		Mongo.updateCalendar(id, "tasks", tasks);
	}
	
	// Method to remove a task
	public void removeTask( String task)
	{
		tasks.remove( task);
		Mongo.updateCalendar(id, "tasks", tasks);
	}
	
	// It adds the parameter calendar's tasks to this.
	public void merge( Calendar calendar)
	{
		Iterator<Task> it;
		
		it = calendar.getTasks().iterator();
		
		while(it.hasNext())
			addTask(((Task)it.next()).getID());
	}
	
	// It prints the active , passive tasks separately along with the progresses
	public String toString()
	{
		String progress = "Progress is "+getProgress();
		String activeProgress="Progress of active tasks is "+getActiveProgress();
		String active ="";
		String passive="";
		
		Iterator<Task> actives;
		Iterator<Task> passives;
		
		actives = this.getActiveTasks().iterator();
		passives = this.getPassiveTasks().iterator();
		
		while(actives.hasNext())
			active+=actives.next();
		
		while(passives.hasNext())
			passive+=passives.next();
		
		return progress+"\n"+activeProgress+"\n"+active+passive;
	}
	
	
	// Method to get a calendar by it's ID
	public static Calendar getByID( String id)
	{
		return Mongo.getCalendar( id);
	}
	
	// Method to create a new calendar
	public static Calendar createNewCalendar()
	{
		Calendar calendar = new Calendar(UUID.randomUUID().toString(), new ArrayList<String>());
		Mongo.createCalendar( calendar);
		
		return calendar;
	}
}