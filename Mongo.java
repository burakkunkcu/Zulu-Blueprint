package blueprint.zulu;

//import java.net.UnknownHostException;
import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.util.JSON;

public class Mongo {

	private static DB db;
	private static MongoClient mongo;

	/* User methods */

	// Register a user
	public static boolean registerUser(User u) {
		DBConnect();
		DBObject obj = createDBObject(u);
		DBCollection users = db.getCollection("users");
		WriteResult result = users.save(obj);
		DBDisconnect();
		return result.isUpdateOfExisting();
	}

	// Get a user
	public User getUser(String id) {
		DBConnect();
		DBCollection users = db.getCollection("users");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = users.find(query);
		User tmp = null;
		try {
			while (cursor.hasNext()) {
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(Employee)
				tmp = (new Gson()).fromJson(dbobj.toString(), User.class);
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return tmp;
	}

	/* Project methods */

	// Create a project
	public boolean createProject(Project p) {
		DBConnect();
		DBObject obj = createDBObject(p);
		DBCollection projects = db.getCollection("projects");
		WriteResult result = projects.save(obj);
		DBDisconnect();
		return result.isUpdateOfExisting();
	}

	// Remove project
	public boolean removeProject(String id) {
		DBConnect();
		DBCollection projects = db.getCollection("projects");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = projects.find(query);
		boolean b = false;
		try {
			while (cursor.hasNext()) {
				WriteResult result = users.remove((cursor.next()));
				b = result.isUpdateOfExisting();
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return b;
	}

	// Get project
	public Project getProject(String id) {
		DBConnect();
		DBCollection projects = db.getCollection("projects");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = projects.find(query);
		Project tmp = null;
		try {
			while (cursor.hasNext()) {
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(Employee)
				tmp = (new Gson()).fromJson(dbobj.toString(), Project.class);
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return tmp;
	}

	/* Task methods */

	// Create task
	public boolean createTask(Task t) {
		DBConnect();
		DBObject obj = createDBObject(t);
		DBCollection tasks = db.getCollection("tasks");
		WriteResult result = tasks.save(obj);
		DBDisconnect();
		return result.isUpdateOfExisting();
	}

	// Delete task
	public boolean deleteTask(String id) {
		DBConnect();
		DBCollection tasks = db.getCollection("tasks");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = tasks.find(query);
		boolean b = false;
		try {
			while (cursor.hasNext()) {
				WriteResult result = tasks.remove((cursor.next()));
				b = result.isUpdateOfExisting();
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return b;
	}

	// Get task
	public Task getTask(String id) {
		DBConnect();
		DBCollection tasks = db.getCollection("tasks");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = tasks.find(query);
		Task tmp = null;
		try {
			while (cursor.hasNext()) {
				DBObject dbobj = cursor.next();
				// Converting BasicDBObject to a custom Class(Task)
				tmp = (new Gson()).fromJson(dbobj.toString(), Task.class);
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return tmp;
	}

	/* Calendar methods */

	// Create Calendar
	public boolean createCalendar(Calendar c) {
		DBConnect();
		DBObject obj = createDBObject(c);
		DBCollection calendars = db.getCollection("calendars");
		WriteResult result = calendars.save(obj);
		DBDisconnect();
		return result.isUpdateOfExisting();
	}

	// Delete Calendar
	public boolean deleteCalendar(String id) {
		DBConnect();
		DBCollection calendars = db.getCollection("calendars");
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = calendars.find(query);
		boolean b = false;
		try {
			while (cursor.hasNext()) {
				WriteResult result = calendars.remove((cursor.next()));
				b = result.isUpdateOfExisting();
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return b;
	}

	public Calendar getCalendar(String id) {
		DBConnect();
		DBCollection calendars = db.getCollection("calendars");
		BasicDBObject query = new BasicDBObject("id", id);
		Calendar tmp = null;
		DBCursor cursor = calendars.find(query);
		try {
			while (cursor.hasNext()) {
				DBObject dbobj = cursor.next();
				tmp = (new Gson()).fromJson(dbobj.toString(), Calendar.class);
			}
		} finally {
			cursor.close();
		}
		DBDisconnect();
		return tmp;

	}

	/* CreateDBObject */
	private static DBObject createDBObject(Object o) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject) JSON.parse(gson.toJson(o));
		return obj;
	}

	// DBConnect
	@SuppressWarnings("deprecation")
	private static void DBConnect() {
		mongo = new MongoClient("localhost", 27017);
		db = mongo.getDB("zulu");
	}

	// DBDisconect
	private static void DBDisconnect() {
		mongo.close();
	}

}
