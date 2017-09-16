package blueprint.zulu.util;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/*
 * Project - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import blueprint.zulu.database.Mongo;

public class Project implements Serializable
{
	// Properties
	private static final long serialVersionUID = -8232433606561571026L;
	private String id;
	private String name;
	private String desc;
	private String leaderID;
	private ArrayList<String> users;
	private String calendar;
	
	// Constructor
	public Project( String id, String name, String desc, String leaderID, ArrayList<String> users, String calendar)
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.leaderID = leaderID;
		this.users = users;
		this.calendar = calendar;
	}
	
	// Method to get project ID
	public String getID()
	{
		return this.id;
	}
	
	// Method to get project name
	public String getName()
	{
		return this.name;
	}
	
	// Method to get project description
	public String getDesc()
	{
		return this.desc;
	}
	
	// Method to get users
	public ArrayList<String> getUsers()
	{
		return this.users;
	}
	
	// Method to get leader ID
	public String getleaderID()
	{
		return this.leaderID;
	}
	
	// Method to set project name
	public void setName( String name)
	{
		Mongo.updateProject( id, "name", name);
	}
	
	// Method to set project description
	public void setDesc( String desc)
	{
		Mongo.updateProject( id, "desc", desc);
	}
	
	// Method to change project leader
	public void changeLeader( String leaderID)
	{
		Mongo.updateProject( id, "leaderID", leaderID);
	}
	
	// Method to add an user 
	public void addUser( String userID)
	{
		this.users.add( userID);
		Mongo.updateProject( id, "users", users);
	}
	
	// Method to remove an user 
	public void removeUser( String userID)
	{
		users.remove( userID);
		Mongo.updateProject( id, "users", users);
	}
	
	// Method to get project calendar
	public Calendar getCalendar()
	{
		return Calendar.getByID( calendar);	
	}
	
	// Method to get user calendars
	public ArrayList<Calendar> getUserCalendars()
	{
		ArrayList<Calendar> userCalendars = new ArrayList<Calendar>();
		
		for (String userID : users)
		{
			User user = User.getByID( userID);
			
			userCalendars.add( user.getCalendar());
		}
		
		return userCalendars;
	}
	
	public ArrayList<Date> meetUp( Date date)
	{
		Date parsedDate = null;
		ArrayList<User> users = new ArrayList<User>();
		
		for ( String userID : getUsers() )
			users.add(User.getByID( userID));
		
			parsedDate = date;

		
		return MeetUp.perform(users, parsedDate);
	}
	
	// String representation of the Project class
	public String toString()
	{
		String result = "ID: " + id + "\n";
		result = result + "Name: " + name + "\n";
		result = result + "Desc: " + desc + "\n";
		result = result + "LeaderID: " + leaderID + "\n";
		result = result + "Users: " + users + "\n";
		result = result + "Calendar: " + getCalendar() + "\n";
		
		return result;
	}
	
	// Method to get project by it's ID
	public static Project getByID( String id)
	{
		return Mongo.getProject( id);
	}
	
	// Method to create a new project
	public static Project createNewProject( String name, String desc, String leaderID)
	{
		Calendar projectCalendar = Calendar.createNewCalendar();
		Project project = new Project( UUID.randomUUID().toString(), name, desc, leaderID, new ArrayList<String>(), projectCalendar.getID());
		Mongo.createProject(project);
		
		return project;
	}
}