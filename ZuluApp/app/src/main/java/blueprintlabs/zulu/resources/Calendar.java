package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Calendar {
    // properties
    private ArrayList<Task> tasks;

    // constructor
    public Calendar(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // methods
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getActiveTasks() {
        ArrayList<Task> actives;
        Iterator it;

        it = tasks.iterator();
        actives = new ArrayList<Task>();

        while (it.hasNext()) {
            Task check = (Task) it.next();
            if (check.isActive())
                actives.add(check);
        }

        return actives;

    }

    public ArrayList<Task> getPassiveTasks() {
        ArrayList<Task> passives;
        Iterator it;

        it = tasks.iterator();
        passives = new ArrayList<Task>();

        while (it.hasNext()) {
            Task check = (Task) it.next();
            if (!check.isActive())
                passives.add(check);
        }

        return passives;

    }



    /************************************************************************
     public ArrayList<Task> getTasksByInterval(String interval) {

     return null; }
     ********************************************************************/

    // it calculates the average progress of the tasks in the ArrayList
    public int getProgress() {

        int size;
        int sum;
        Iterator it;

        size = tasks.size();
        sum = 0;
        it = tasks.iterator();

        while (it.hasNext()) {
            sum += ((Task) it.next()).getProgress();
        }

        return sum / size;
    }

    // it calculates only the average progress of the active tasks
    public int getActiveProgress() {

        int size;
        int sum;
        Iterator it;
        ArrayList<Task> actives;

        actives = getActiveTasks();
        size = actives.size();
        sum = 0;
        it = actives.iterator();

        while (it.hasNext()) {
            sum += ((Task) it.next()).getProgress();
        }

        return sum / size;

    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public boolean removeTask(Task task){

        return tasks.remove(task);

    }

    //it adds the parameter calendar's tasks to this.
    public void merge(Calendar calendar){

        Iterator it;

        it = calendar.getTasks().iterator();

        while(it.hasNext()){
            this.addTask(((Task)it.next()));
        }
    }

    //it prints the active , passive tasks separately along with the progresses
    public String generateCalendarReport(){
        String progress = "Progress is "+getProgress();
        String activeProgress="Progress of active tasks is "+getActiveProgress();
        String active ="";
        String passive="";

        Iterator actives;
        Iterator passives;

        actives = this.getActiveTasks().iterator();
        passives = this.getPassiveTasks().iterator();

        while(actives.hasNext())
            active+=actives.next();

        while(passives.hasNext())
            passive+=passives.next();

        return progress+"\n"+activeProgress+"\n"+active+passive;
    }

    public static Calendar getByID( int id)
    {
        // To Do
        // Connect DB and get calendar
        return null;
    }
}
