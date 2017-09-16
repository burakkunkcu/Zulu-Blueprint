package blueprint.zulu.socket;

import java.io.*;
import java.net.*;
import blueprint.zulu.command.*;
/*
 * GreetingServer - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class GreetingServer extends Thread
{
	// Properties
	private ServerSocket serverSocket;
	private CommandManager commandManager;
	private int port;
	Socket socket;
	
	// Constructor
	public GreetingServer( int port)
	{
		this.port = port;
		this.commandManager = new CommandManager();
		
		try 
		{
			// Try to open a new server socket
			serverSocket = new ServerSocket( this.port);
			//serverSocket.setSoTimeout( 10000);
		} 
		catch (IOException e) 
		{
			// Print error if socket couldn't be opened
			System.out.println( "Couldn't open server socket at port: " + port);
		}
	}
	
	// Default run method of the thread superclass
	public void run()
	{
		while(true)
		{
			try 
			{
				// Start accepting new clients
				try{
					socket = serverSocket.accept();
				}
				catch(NullPointerException e){
					serverSocket.close();
					socket = serverSocket.accept();
				}
				// Create a new thread for every new client (Necessary for multi-thread socket communication)
				Thread clientConnection = new ClientConnection( socket, commandManager);
				
				// Start client connection
				clientConnection.start();
			} 
			catch (IOException e)
			{
				System.out.println( "Couldn't create a connection");
			}
		}
	}
}