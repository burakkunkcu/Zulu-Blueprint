package blueprintlabs.zulu.socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import blueprint.zulu.socket.RemoteCommand;

/**ç
 * Created by Burak on 29.04.2016.
 */
public class Client extends Thread
{
    private String commandBase;
    private String commandIdentifier;
    private String[] commandArgs;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Object result;

    public Client( String commandBase, String commandIdentifier, String[] commandArgs)
    {
        this.commandBase = commandBase;
        this.commandIdentifier = commandIdentifier;
        this.commandArgs = commandArgs;
    }

    @Override
    public void run()
    {
        try
        {
            //Creating a new socket via defined port 2150
            Socket client = new Socket("46.101.150.199", 2150);

            //Opening streams
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            //Sends a new command
            if(commandBase.equals("login")) {
                if (commandIdentifier.equals("salt")) {
                    out.writeObject(new RemoteCommand(commandBase, commandIdentifier, commandArgs));
                    result = in.readObject();
                } else if (commandIdentifier.equals("login")) {
                    out.writeObject(new RemoteCommand(commandBase, commandIdentifier, commandArgs));
                    result = in.readObject();
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Object getResult()
    {
        while(this.isAlive()) {}
        return result;
    }

    public void close(){
        try {
            client.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
