package blueprint.zulu.command;
/*
 * ZuluCommand - Abstract class of the command classes
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public abstract class ZuluCommand
{
	public abstract Object execute( String client, String identifier, String[] args) throws Exception;
}