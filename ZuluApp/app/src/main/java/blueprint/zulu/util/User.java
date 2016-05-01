package blueprint.zulu.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/*
 * User - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class User implements Serializable
{
	public final static String MEETUP_PERMISSION = "m";

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

	//Returns the IDs of the projects
	public ArrayList<String> getProjects(){return this.projects; }
	
	// Method to check user permissions
	public boolean hasPermission( String perm)
	{
		return permissions.contains( perm);
	}



	
	// String representation of the User class
	public String toString()
	{
		String result = "ID: " + id + "\n";
		result = result + "Name: " + name + "\n";
		result = result + "Desc: " + desc + "\n";
		result = result + "Projects: " + projects + "\n"; // TO DO
		result = result + "Permissions: " + permissions + "\n";
		result = result + "Calendar: ";
		
		return result;
	}

	
	public void setID(String id)
	{
		this.id = id;
	}
}