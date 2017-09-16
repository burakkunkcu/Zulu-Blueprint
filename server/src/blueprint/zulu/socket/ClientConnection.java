package blueprint.zulu.socket;

import java.io.*;
import java.net.*;

import blueprint.zulu.command.*;
/*
 * ClientConnection - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class ClientConnection extends Thread
{
	// Properties
	private Socket socket;
	private CommandManager commandManager;
	private String client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	// Constructor
	public ClientConnection( Socket socket, CommandManager commandManager)
	{
		this.socket = socket;
		this.commandManager = commandManager;
		this.client = (socket.getInetAddress() + ":" + socket.getPort()).substring( 1);
	}
	
	// Default run method of the thread superclass
	public void run()
	{				
		System.out.println( "Client " + client + " connected!");
		
		try
		{
			// Open input and output streams
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			// Wait for an incoming RemoteCommand
			RemoteCommand incoming = (RemoteCommand) in.readObject();

			// Disconnect the client if disconnect command executed
			if (incoming.getCommandBase().equals("disconnect")) {
				System.out.println("Client " + client + " disconnected! (Safe)");
				return;
			}

			else {
				// Try to execute the command and return the feedback object
				Object object = commandManager.executeCommand(client, incoming.getCommandBase(),
						incoming.getCommandIdentifier(), incoming.getArgs());
				
				System.out.println("Client " + client + " executed command!");

				if (object != null)
					out.writeObject(object);

				else
					out.writeObject(null);
				
				socket.close();
				System.out.println("Client " + client + " disconnected!");
			}
		} 
		catch (Exception e) {
			if (e instanceof SocketException) {
				System.out.println("Client " + client + " disconnected!");
				return;
			}

			else
				e.printStackTrace();
		}
	}
}