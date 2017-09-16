package blueprint.zulu;

import java.util.logging.*;
import blueprint.zulu.database.Mongo;
import blueprint.zulu.socket.GreetingServer;
/*
 * Zulu - Main class 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Zulu
{	
	// Properties
	private static Thread socket;
	
	// Main method of the project
	public static void main( String args[])
	{
		System.out.println( "Welcome to Zulu!");
		System.out.println( "Starting server socket...");
		
		// Starting server socket
		socket = new GreetingServer( 2150);
		socket.start();	
		
		System.out.println( "Server socket started!");
		System.out.println( "Connecting to Mongo Database...");
		
		// Connecting to MongoDB
		Mongo.DBConnect();
		
		// Comment out this line if you want to remove MongoDB logs
		// Logger.getLogger( "org.mongodb.driver" ).setLevel( Level.SEVERE);;
		
		System.out.println( "Successfully conneted to the database!");
	}
}