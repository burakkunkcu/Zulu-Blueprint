package blueprint.zulu.database;

import java.util.ArrayList;
import java.util.List;

//import java.net.UnknownHostException;
import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.util.JSON;
//import com.mongodb.BasicDBList;

import blueprint.zulu.util.*;
/*
 * Mongo Database - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Mongo {

	// Properties
	private static DB db;
	private static MongoClient mongo;
	private static boolean status = false;

	/* Authentication */

	public static boolean login( String mail, String pw) 
	{
		DBCollection auth = db.getCollection( "login");
		BasicDBObject query = new BasicDBObject( "mail", mail);
		DBCursor cursor = auth.find( query);
		boolean pass = false;
		
		try 
		{
			while (cursor.hasNext()) 
			{
				DBObject dbobj = cursor.next();
				String hash = (String) dbobj.get( "hash");
				
				if (Auth.isExpectedPassword( pw, hash) == true) 
					pass = Auth.isExpectedPassword( pw, hash);
			}
		} 
		finally 
		{
			cursor.close();
		}

		return pass;
	}

	public static String returnSalt( String mail) 
	{
		DBCollection auth = db.getCollection( "login");
		BasicDBObject query = new BasicDBObject( "mail", mail);
		DBCursor cursor = auth.find( query);
		String salt = null;
		
		try 
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				salt = (String) dbobj.get( "salt");
			}
		} 
		finally 
		{
			cursor.close();
		}
		
		return new String(salt);
	}

	/* User methods */

	// Register a user
	public static boolean registerUser( User u, String mail, String pw) 
	{
		// Create a DBObject
		DBObject obj = createDBObject( u);

		// Generate a hash for the pass
		String salt = Auth.getNextSalt();
		String hashPW = Auth.hash( pw, salt);

		// Insert authentication information
		DBObject authObj = new BasicDBObject().append( "id", u.getID()).append( "mail", mail).append( "salt", salt).append( "hash", hashPW);
		DBCollection login = db.getCollection( "login");
		WriteResult res = login.save( authObj);

		// Insert user
		DBCollection users = db.getCollection( "users");
		WriteResult result = users.save( obj);

		// Return the write result
		return result.wasAcknowledged() && res.wasAcknowledged();
	}

	// Get a user
	public static User getUser( String id)
	{
		DBCollection users = db.getCollection( "users");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = users.find( query);
		User tmp = null;
		
		try
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(User)
				tmp = (new Gson()).fromJson( dbobj.toString(), User.class);
			}
		} 
		finally
		{
			cursor.close();
		}
		return tmp;
	}

	public static User getUserByEmail( String email) 
	{
		DBCollection users = db.getCollection( "users");
		BasicDBObject query = new BasicDBObject( "email", email);
		DBCursor cursor = users.find( query);
		User tmp = null;
		
		try
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(User)
				tmp = (new Gson()).fromJson( dbobj.toString(), User.class);
			}
		} 
		finally
		{
			cursor.close();
		}
		return tmp;
	}

	// Update user
	public static void updateUser( String id, String field, Object value) 
	{
		DBCollection users = db.getCollection( "users");
		BasicDBObject updateDocument = new BasicDBObject().append( "$set", new BasicDBObject().append( field, value));
		
		users.update(new BasicDBObject().append( "id", id), updateDocument);
	}

	/* Project methods */

	// Create a project
	public static boolean createProject( Project p) 
	{
		// Save the database object
		//p.setID(RandomID.generate());
		DBObject obj = createDBObject(p);
		DBCollection projects = db.getCollection( "projects");
		WriteResult result = projects.save(obj);

		// Creates a chat channel for the project
		DBCollection chatChannel = db.getCollection( "chatChannel");
		BasicDBObject pMessages = new BasicDBObject( "id", p.getID());
		List<DBObject> messages = new ArrayList<>();
		Message m = new Message( p.getleaderID() , "Hello guys. This is your chat channel!");
		DBObject first = createDBObject(m);
		
		//Creates a dashboard for the projects
		DBCollection dashboards = db.getCollection( "dashboards");
		BasicDBObject pDashboards = new BasicDBObject( "id", p.getID());
		List<DBObject> dashes = new ArrayList<>();
		
		//Dashboard d = new Dashboard( p.getleaderID(), "has created the project: " + p.getName());
		
		//DBObject fdash = createDBObject(d);
		
		//Add the first message
		messages.add( first);
		pMessages.put( "messages", messages);
		chatChannel.insert( pMessages);
		
		//Add the first dash		
		//dashes.add(fdash);
		//pDashboards.put("dashes", dashes);
		//dashboards.insert(pDashboards);


		return result.wasAcknowledged();
	}

	// Remove project
	public static boolean removeProject( String id) 
	{
		DBCollection projects = db.getCollection( "projects");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = projects.find( query);
		boolean b = false;
		
		try 
		{
			while (cursor.hasNext()) 
			{
				WriteResult result = projects.remove(( cursor.next()));
				b = result.isUpdateOfExisting();
			}
		} 
		finally 
		{
			cursor.close();
		}
		
		return b;
	}

	// Get project
	public static Project getProject( String id) 
	{
		DBCollection projects = db.getCollection( "projects");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = projects.find( query);
		Project tmp = null;
		
		try 
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(Project)
				tmp = (new Gson()).fromJson( dbobj.toString(), Project.class);
			}
		} 
		finally 
		{
			cursor.close();
		}
		
		return tmp;
	}

	// Update project
	public static void updateProject( String id, String field, Object value)
	{
		DBCollection tasks = db.getCollection( "projects");
		BasicDBObject updateDocument = new BasicDBObject().append( "$set", new BasicDBObject().append( field, value));
		
		tasks.update(new BasicDBObject().append( "id", id), updateDocument);
	}

	/* Task methods */

	// Create task
	public static boolean createTask( Task t)
	{
		DBObject obj = createDBObject( t);
		DBCollection tasks = db.getCollection( "tasks");
		WriteResult result = tasks.save( obj);
		
		return result.wasAcknowledged();
	}

	// Delete task
	public static boolean removeTask( String id)
	{
		DBCollection tasks = db.getCollection( "tasks");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = tasks.find( query);
		
		try 
		{
			while (cursor.hasNext())
			{
				WriteResult result = tasks.remove( cursor.next());
				status = result.wasAcknowledged();
			}
		} 
		finally 
		{
			cursor.close();
		}
		
		return status;
	}

	// Get task
	public static Task getTask(String id) 
	{
		DBCollection tasks = db.getCollection( "tasks");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = tasks.find( query);
		Task tmp = null;
		
		try 
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(Task)
				tmp = (new Gson()).fromJson( dbobj.toString(), Task.class);
			}
		} 
		finally
		{
			cursor.close();
		}
		
		return tmp;
	}

	// Update task
	public static void updateTask( String id, String field, Object value)
	{
		DBCollection tasks = db.getCollection( "tasks");
		BasicDBObject updateDocument = new BasicDBObject().append( "$set", new BasicDBObject().append( field, value));
		
		tasks.update(new BasicDBObject().append( "id", id), updateDocument);
	}

	/* Calendar methods */

	// Create Calendar
	public static boolean createCalendar( Calendar c) 
	{
		DBObject obj = createDBObject( c);
		DBCollection calendars = db.getCollection( "calendars");
		WriteResult result = calendars.save( obj);
		
		return result.wasAcknowledged();
	}

	// Remove Calendar
	public static boolean removeCalendar( String id) 
	{
		DBCollection calendars = db.getCollection( "calendars");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = calendars.find( query);
		boolean b = false;
		
		try 
		{
			while (cursor.hasNext()) 
			{
				WriteResult result = calendars.remove( cursor.next());
				b = result.wasAcknowledged();
			}
		} 
		finally
		{
			cursor.close();
		}
		
		return b;
	}

	// GetCalendar
	public static Calendar getCalendar( String id) 
	{
		DBCollection calendars = db.getCollection( "calendars");
		BasicDBObject query = new BasicDBObject( "id", id);
		Calendar tmp = null;
		DBCursor cursor = calendars.find( query);
		
		try 
		{
			while (cursor.hasNext())
			{
				DBObject dbobj = cursor.next();
				tmp = (new Gson()).fromJson( dbobj.toString(), Calendar.class);
			}
		} 
		finally 
		{
			cursor.close();
		}
		
		return tmp;

	}

	// Update calendar
	public static void updateCalendar( String id, String field, Object value) 
	{
		DBCollection calendars = db.getCollection( "calendars");
		BasicDBObject updateDocument = new BasicDBObject().append( "$set", new BasicDBObject().append(field, value));
		
		calendars.update(new BasicDBObject().append( "id", id), updateDocument);

	}
	
	//Dashboard
	public static boolean insertDash(String projectID, Dashboard dash)
	{
		DBCollection chatChannel = db.getCollection( "dashboards");
		DBObject obj = createDBObject( dash);
		BasicDBObject updateQuery = new BasicDBObject();
		
		updateQuery.put( "id", projectID);
		BasicDBObject updateCommand = new BasicDBObject();
		
		updateCommand.put( "$push", new BasicDBObject( "dash", obj));
		WriteResult result = chatChannel.update( updateQuery, updateCommand, true, true);
		
		return result.wasAcknowledged();		
	}
	
	public static ArrayList<String> getDashboard( String id)
	{
		DBCollection chatChannel = db.getCollection( "chatChannel");
		BasicDBObject query = new BasicDBObject( "id", id);
		DBCursor cursor = chatChannel.find( query);
		ArrayList<DBObject> list = new ArrayList<DBObject>();
		
		try 
		{
			while (cursor.hasNext()) 
			{
				DBObject dbobj = cursor.next();
				list = (ArrayList<DBObject>) dbobj.get( "dashboards");
			}
		} 
		finally 
		{
			cursor.close();
		}

		// Retrieve the last 20 dashes
		ArrayList<String> d = new ArrayList<String>();
		
		for (int i = 1; i <= list.size() ; i++) 
		{
			DBObject dbo = list.get(list.size() - i);
			d.add(((new Gson()).fromJson( dbo.toString(), Dashboard.class)).getDashBody());
		}

		return d;		
	}

	// Chat
	public static boolean insertMessage( String projectID, Message m) 
	{
		DBCollection chatChannel = db.getCollection( "chatChannel");
		DBObject obj = createDBObject( m);
		BasicDBObject updateQuery = new BasicDBObject();
		
		updateQuery.put( "id", projectID);
		BasicDBObject updateCommand = new BasicDBObject();
		
		updateCommand.put( "$push", new BasicDBObject( "messages", obj));
		WriteResult result = chatChannel.update( updateQuery, updateCommand, true, true);
		
		return result.wasAcknowledged();

	}

	// Return messages
	@SuppressWarnings( "unchecked")
	public static ArrayList<Message> getMessages( String projectID) 
	{
		DBCollection chatChannel = db.getCollection( "chatChannel");
		BasicDBObject query = new BasicDBObject( "id", projectID);
		DBCursor cursor = chatChannel.find( query);
		ArrayList<DBObject> list = new ArrayList<DBObject>();
		
		try 
		{
			while (cursor.hasNext()) 
			{
				DBObject dbobj = cursor.next();
				list = (ArrayList<DBObject>) dbobj.get( "messages");
			}
		} 
		finally 
		{
			cursor.close();
		}

		// Retrieve the last 20 messages
		ArrayList<Message> m = new ArrayList<Message>();
		
		for (int i = 1, j = 0; i <= list.size() && j < 20; i++, j++) 
		{
			DBObject dbo = list.get(list.size() - i);
			m.add((new Gson()).fromJson( dbo.toString(), Message.class));
		}

		return m;

	}

	/* CreateDBObject */
	private static DBObject createDBObject(Object o)
	{
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject) JSON.parse( gson.toJson(o));
		return obj;
	}

	// DBConnect
	@SuppressWarnings( "deprecation")
	public static void DBConnect() 
	{
		mongo = new MongoClient( "46.101.150.199", 27017);
		db = mongo.getDB( "zulu");
	}

	// DBDisconect
	public static void DBDisconnect() 
	{
		mongo.close();
	}

	// The following two methods is for test use only!

	public static void printCollection( String col)
	{
		DBCollection coll = db.getCollection( col);
		DBCursor cursor = coll.find();
		
		while (cursor.hasNext()) 
			System.out.println( cursor.next());
	}

	public static void deleteCollectionElements( String col) 
	{
		DBCollection coll = db.getCollection( col);
		coll.remove(new BasicDBObject());
	}
}
