package blueprint.zulu.command;

import blueprint.zulu.database.Mongo;
import blueprint.zulu.util.Task;
import blueprint.zulu.util.Calendar;
import blueprint.zulu.util.Dashboard;
import blueprint.zulu.util.Project;
/*
 * DashboardCommand - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class DashboardCommand extends ZuluCommand
{

	@Override
	public Object execute( String client, String identifier, String[] args) throws Exception 
	{
		Object result = null;

		if (identifier.equals( "get"))
		{
			result = Mongo.getDashboard( args[0]);
		}
		
		return result;
	}
}
