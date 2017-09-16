package blueprint.zulu.util;

import java.util.ArrayList;
import java.util.Date;
/*
 * MeetUp - 
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class MeetUp 
{
	@SuppressWarnings("deprecation")
	public static ArrayList<Date> perform( ArrayList<User> users, Date meetDate) 
	{

		ArrayList<Task> catched = new ArrayList<Task>();
		ArrayList<Date> catchedDate = new ArrayList<Date>();

		for (int i = 0; i < users.size(); i++)
		{
			ArrayList<Task> actives = users.get(i).getCalendar().getActiveTasks();
			
			for (int j = 0; j < actives.size(); j++)
			{
				if (actives.get(j).getDate().getDay() == meetDate.getDay()) 
					catched.add(actives.get(j));
			}
		}

		boolean stop = false;

		for (int i = 8; i <= 20; i++) 
		{
			stop = false;
			int copy = 0;
			
			for (int j = 0; j < catched.size() && !stop; j++)
			{
				if (catched.get(j).getDate().getHours() == i)
					stop = true;
				
				copy = j;
			}

			if (!stop)
				catchedDate.add(catched.get(copy).getDate());

		}

		if (catchedDate.size() == 0)
			return null;
		
		return catchedDate;
	}
}