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

public class Project implements Serializable
{
	private static final long serialVersionUID = -8232433606561571026L;

	// Properties
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

	// Method to get calendar attached to this project
	public String getCalendar() { return this.calendar; }

	
	// String representation of the Project class
	public String toString()
	{
		String result = "ID: " + id + "\n";
		result = result + "Name: " + name + "\n";
		result = result + "Desc: " + desc + "\n";
		result = result + "LeaderID: " + leaderID + "\n";
		result = result + "Users: " + users + "\n";
		result = result + "Calendar: ";
		
		return result;
	}

}