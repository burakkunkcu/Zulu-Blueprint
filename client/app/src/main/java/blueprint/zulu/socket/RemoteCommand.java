package blueprint.zulu.socket;

import java.io.Serializable;

public class RemoteCommand implements Serializable
{
    // Properties
    private static final long serialVersionUID = 8219318672518338459L;
    private String commandBase;
    private String commandIdentifier;
    private String[] args;

    // COnstructor
    public RemoteCommand( String commandBase, String commandIdentifier, String[] args)
    {
        this.commandBase = commandBase;
        this.commandIdentifier = commandIdentifier;
        this.args = args;
    }

    // Method to get the command base
    public String getCommandBase()
    {
        return commandBase;
    }

    // Method to get the command identifier
    public String getCommandIdentifier()
    {
        return commandIdentifier;
    }

    // Method to get command args
    public String[] getArgs()
    {
        return args;
    }
}