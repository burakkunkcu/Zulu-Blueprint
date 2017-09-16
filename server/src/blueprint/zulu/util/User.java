package blueprint.zulu.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import blueprint.zulu.database.Mongo;
/*
 * User - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class User implements Serializable
{
	// Properties
	private static final long serialVersionUID = -6774293537897416619L;
	private String id;
	private String email;
	private String name;
	private String desc;
	private ArrayList<String> projects;
	private ArrayList<String> permissions;
	private String calendar;
	
	// Constructor
	public User( String id, String email, String name, String desc, ArrayList<String> projects, ArrayList<String> permissions, String calendar)
	{
		this.id = id;
		this.email = email;
		this.name = name;
		this.desc = desc;
		this.projects = projects;
		this.permissions = permissions;
		this.calendar = calendar;
	}
	
	// Method to get user ID
	public String getID()
	{
		return this.id;
	}
	
	// Method to get user name
	public String getName()
	{
		return this.name;
	}
	
	// Method to get user email
	public String getEmail()
	{
		return this.email;
	}
	
	// Method to get user description
	public String getDesc()
	{
		return this.desc;
	}
	
	// Method to set user name
	public void setName( String name)
	{
		Mongo.updateUser( id, "name", name);
	}
	
	// Method to set user description
	public void setDesc( String desc)
	{
		Mongo.updateUser( id, "desc", desc);
	}
	
	// Method to check user permissions
	public boolean hasPermission( String perm)
	{
		return permissions.contains( perm);
	}
	
	// Method to add a permission
	public void addPermission( String perm)
	{
		permissions.add( perm);
		Mongo.updateUser( id, "permissions", permissions);
	}
	
	// Method to remove a permission
	public void removePermission( String perm)
	{
		permissions.remove( perm);
		Mongo.updateUser( id, "permissions", permissions);
	}

	// Method to get user calendar
	public Calendar getCalendar()
	{
		return Calendar.getByID( calendar);
	}
	
	// Method to get user's projects
	public ArrayList<Project> getProjects()
	{
		ArrayList<Project> result = new ArrayList<Project>();
		
		for ( String project : projects )
			result.add( Project.getByID( project));
		
		return result;
	}
	
	// Method to add a project to user
	public void addProject( String project)
	{
		projects.add(project);
		Mongo.updateUser( id, "projects", projects);
	}
	
	// Method to remove a project from user
	public void removeProject( String project)
	{
		projects.remove(project);
		Mongo.updateUser( id, "projects", projects);
	}
	
	// String representation of the User class
	public String toString()
	{
		String result = "ID: " + id + "\n";
		result = result + "Name: " + name + "\n";
		result = result + "Desc: " + desc + "\n";
		result = result + "Projects: " + projects + "\n"; // TO DO
		result = result + "Permissions: " + permissions + "\n";
		result = result + "Calendar: " + getCalendar() + "\n";
		
		return result;
	}
	
	// Method to get user by it's ID
	public static User getByID( String id)
	{
		return Mongo.getUser( id);
	}
	
	// Method to get user by it's email
	public static User getByEmail( String email)
	{
		return Mongo.getUserByEmail( email);
	}
	
	// Method to register a new user
	public static boolean registerNewUser( String email, String name, String pw)
	{
		Calendar userCalendar = Calendar.createNewCalendar();
		User user = new User( UUID.randomUUID().toString(), email, name, "", new ArrayList<String>() , new ArrayList<String>(), userCalendar.getID());		
		boolean b = Mongo.registerUser(user, email, pw);
		System.out.println("Register user: " + b);
		return b;
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
}