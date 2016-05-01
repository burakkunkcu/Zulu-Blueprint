package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
import java.util.ArrayList;

public class Project
{
    private int id;
    private String name;
    private String desc;
    private int leaderID;
    private ArrayList<Integer> users;
    private int calendar;

    public Project( int id, String name, String desc, int leaderID, ArrayList<Integer> users, int calendar)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.leaderID = leaderID;
        this.users = users;
        this.calendar = calendar;
    }

    public String getID()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public ArrayList<Task> getTasks(){
        return new ArrayList<Task>();
    }

    public ArrayList<User> getUsers()
    {
        return new ArrayList<User>();
    }

    public int getleaderID()
    {
        return this.leaderID;
    }

    public void setName( String name)
    {
        this.name = name;
    }

    public void setDesc( String desc)
    {
        this.desc = desc;
    }

    public void setLeader( int leaderID)
    {
        this.leaderID = leaderID;
    }

    public void addUser( int userID)
    {
        this.users.add( userID);
    }

    public void removeUser( int userID)
    {
        users.remove( userID);
    }

    public Calendar getCalendar()
    {
        return Calendar.getByID( calendar);

    }

    public ArrayList<Calendar> getUserCalendars()
    {
        ArrayList<Calendar> userCalendars = new ArrayList<Calendar>();

        for (Integer userID : users)
        {
            User user = User.getByID( userID);

            userCalendars.add( user.getCalendar());
        }

        return userCalendars;
    }

    public String generateReport()
    {
        // To Do
        return "";
    }

    public static Project getByID( int id)
    {
        // To Do
        // Connect DB and get project
        return null;
    }
}
