package blueprint.zulu.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/*
 * Calendar - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Calendar implements Serializable
{

	// Properties
	private static final long serialVersionUID = -8701730094281170938L;
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
	
}