package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
public class Task {

    // properties
    private String ID;
    private String description;
    private String date;
    private int progress;
    private boolean active;

    // constructor
    public Task(String description, String date, int progress, boolean active) {

        this.description = description;
        this.date = date;
        this.progress = progress;
        this.active = active;
    }

    // methods
    public String getDesc() {
        return description;
    }

    public int getProgress(){
        return progress;
    }

    public String getDate() {
        return date;
    }

    public void setDesc(String desc) {
        desc = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    /*
     * only the description is checked since the date,active and progress may
     * vary in different variations of the same task
     */
    public boolean equals(Task other) {

        if (getDesc().equals(other.getDesc()))
            return true;
        else
            return false;
    }

    public String toString(){

        String desc = "Description "+getDesc();
        String date = "Date "+getDate();
        String progress = "Progress "+getProgress();
        String active;

        if(isActive())
            active = "Active Task";
        else
            active ="Passive Task";

        return desc + "\n"+date+"\n"+progress+"\n"+active+"\n";
    }
}
