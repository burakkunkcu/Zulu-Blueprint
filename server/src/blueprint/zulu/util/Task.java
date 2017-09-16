package blueprint.zulu.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import blueprint.zulu.database.Mongo;
/*
 * Task - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Task implements Serializable{

	// Properties
	private static final long serialVersionUID = -1095017152278898681L;
	private String id;
	private String description;
	private String date;
	private int progress;
	private boolean active;
	private String projectID;

	// Constructor
		private Task( String id, String projectID, String description, String date, int progress, boolean active) 
		{
			this.id = id;
			this.projectID = projectID;
			this.description = description;
			this.date = date;
			this.progress = progress;
			this.active = active;
		}

		// Methods
		public String getID()
		{
			return id;
		}
		
		public String getProjectID()
		{
			return projectID;
		}
		
		// Method to get task description
		public String getDesc()
		{
			return description;
		}
		// Method to get task progress
		public int getProgress()
		{
			return progress;
		}

		// Method to get task date
		public Date getDate() {

			String expectedPattern = "MM/dd/yyyy hh";
			SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
			Date dateRet = null;

			// Usage "09/22/2009 08" - 08-09
			try 
			{
				dateRet = formatter.parse( date);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}

			return dateRet;
		}

		// Method to set task description
		public void setDesc( String desc)
		{
			Mongo.updateTask( id, "decription", desc);
		}

		// Method to set task date
		public void setDate( String date)
		{
			Mongo.updateTask( id, "date", date);
		}

		// Method to set active status
		public void setActive( boolean active) 
		{
			Mongo.updateTask( id, "active", active);
		}
		
		// Method to set task progress
		public void setProgress( int progress) 
		{
			Mongo.updateTask( id, "progress", progress);
		}

		// Method to check if task is active
		public boolean isActive() 
		{
			return active;
		}

		/*
		 * only the description is checked since the date,active and progress may
		 * vary in different variations of the same task
		 */
		public boolean equals( Task other)
		{

			if (getDesc().equals(other.getDesc()))
				return true;
			else
				return false;
		}
		
		// String representation of the Task class
		public String toString()
		{
			
			String desc = "Description "+getDesc();
			String date = "Date "+getDate();
			String progress = "Progress "+getProgress();
			String active;
			
			if(isActive())
				active = "Active Task";
			else 
				active ="Passive Task";
			
			return desc + "\n"+date+"\n"+progress+"\n"+active+"\n";
		}
		
		// Method to get task by it's ID
		public static Task getByID( String id)
		{
			return Mongo.getTask( id);
		}
		
		// Method to create a new task
		public static String createNewTask( String desc, String date, String projectID)
		{
			Task task = new Task( UUID.randomUUID().toString(), projectID, desc, date, 0, true);
			Mongo.createTask( task);
			
			return task.getID();
		}
	}