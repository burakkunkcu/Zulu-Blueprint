package blueprintlabs.zulu.resources;

import java.util.ArrayList;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
public class User {
    public static final String MEETUP_PERMISSION = "meetup";

    public final String name;
    public final String desc;
    public int imageID;
    public ArrayList<Project> projects;
    private ArrayList<String> divisions;
    private ArrayList<String> permissions;
    private int calendar;
    private String mail;
    public int id;

    public User(String id, String details, int i) {
        this.name = id;
        this.desc = details;
        imageID = i;
        this.divisions = new ArrayList<String>();
        this.permissions = new ArrayList<String>();
        this.calendar = i;
    }

    private User( int id, String name, String desc, ArrayList<String> divisions, ArrayList<String> permissions, int calendar)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.divisions = divisions;
        this.permissions = permissions;
        this.calendar = calendar;
        this.mail = mail;
        imageID = 21;
    }


    public int getID()
    {
        return this.id;
    }

    public ArrayList<String> getPermissions(){
        return permissions;
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

    public boolean hasPermission( String perm)
    {
        return permissions.contains( perm);
    }

    public void addPermission( String perm)
    {
        permissions.add( perm);
    }

    public void removePermission( String perm)
    {
        permissions.remove( perm);
    }

    public Calendar getCalendar()
    {
        return Calendar.getByID( calendar);
    }

    public ArrayList<String> getDivisions()
    {
        return divisions;
    }

    public void setDivisions( ArrayList<String> divisions)
    {
        this.divisions = divisions;
    }

    public String getMail()
    {
        return this.mail;
    }

    public String toString()
    {
        String result = "ID: " + id + "\n";
        result = result + "Name: " + name + "\n";
        result = result + "Desc: " + desc + "\n";
        result = result + "Divisions: " + divisions + "\n";
        result = result + "Permissions: " + permissions + "\n";
        result = result + "Calendar: " + getCalendar() + "\n";

        return result;
    }

    public ArrayList<Project> getProjects(){
        return projects;
    }

    public static User getByID( int id)
    {
        // To Do
        // Connect DB and get user
        return null;
    }
}
