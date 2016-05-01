package blueprint.zulu.util;

/**
 * Created by Ahmet Burak on 1.5.2016.
 */
public class Permission {
    private final static int MEETUP_PERMISSION = 0;
    private final static int TASK_PERMISSION = 1;

    private String projectID;
    private int permission;

    public Permission(String ID, int i){
        projectID = ID;
        permission = i;
    }

    public int getPermission(){
        return permission;
    }

    public String getProjectID(){
        return projectID;
    }
}
