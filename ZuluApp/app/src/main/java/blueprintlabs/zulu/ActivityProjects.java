package blueprintlabs.zulu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import blueprintlabs.zulu.adapters.ProjectsAdapter;
import blueprintlabs.zulu.resources.Project;
import blueprintlabs.zulu.resources.User;
import blueprintlabs.zulu.socket.Client;

public class ActivityProjects extends AppCompatActivity implements ProjectsAdapter.OnListFragmentInteractionListener{

    private RecyclerView mRecyclerView;
    private List<Project> projectsList;
    private ProjectsAdapter adapter;
    User currentUser;
    String userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userMail = getIntent().getStringExtra("email");

        if (currentUser == null){
            new getUserTask(userMail).execute();
        }

        projectsList = new ArrayList<Project>();

        if(currentUser != null){

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProjectsAdapter(projectsList, ActivityProjects.this);
        mRecyclerView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add a new project
                if (view != null){
                    DialogAddProject dialogBox = new DialogAddProject();
                    FragmentActivity activity = (FragmentActivity)(ActivityProjects.this);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    dialogBox.show(fm, "addProject");
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_projects, container, false);

        return view;
    }

    /**
     * Retrieves the user and populates the {@Link  projectsList} with users projects using  {@Link getByProjectID}.
     */
    public class getUserTask extends AsyncTask<Void, Void, User> {
        private final String mEmail;
        private final String[] args;

        public getUserTask(String email){
            mEmail = email;
            args = new String[1];
            args[0] = mEmail;
        }

        @Override
        protected User doInBackground(Void... params) {
            Client client = new Client("user", "get", args);
            client.start();
            User user = (User) client.getResult(); //returns null
            System.out.println("------------user-------------");
            System.out.println(client.getResult());
            System.out.println("--------------user-----------");
            return user;
        }


        protected void onPostExecute(User user) {
            if (user != null){
                currentUser = user;
                for(String s : currentUser.getProjects()){
                    new getProjectById(s).execute();
                }
            }
            else{
                Toast.makeText(ActivityProjects.this, "Problem in server, please contact developer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Create a new project with user's leadership.
     */
    public class addProject extends AsyncTask<Void, Void, Boolean>{
        private final String mName;
        private final String mDesc;
        private final User mUser;
        private final String[] args;

        public addProject(String name, String desc){
            this.mName = name;
            this.mDesc = desc;
            mUser = currentUser;
            args = new String[3];
            args[0] = mName;
            args[1] = mDesc;
            args[2] = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Client client = new Client("project", "create", args);
            client.start();
            return (boolean)client.getResult();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(ActivityProjects.this, "Project successfully added", Toast.LENGTH_SHORT).show();
                new getUserTask(userMail).execute();
            }
        }
    }


    /**
     * AsyncTask to retrieve a project with its ID.
     */
    public class getProjectById extends AsyncTask<Void, Void, Project>{
        private final String[] args;

        public getProjectById(String ID){
            args = new String[1];
            args[0] = ID;
        }

        @Override
        protected Project doInBackground(Void... params) {
            Client client = new Client("project", "get", args);
            client.start();
            Project p = (Project) client.getResult();
            return p;
        }

        @Override
        protected void onPostExecute(Project project) {
            if(project != null) {
                projectsList.add(project);
                ActivityProjects.this.adapter.notifyDataSetChanged();
            }
        }
    }

    //Method to execute {@Link getProjectById} from outside the class
    public void addProject(String name, String desc){
        new addProject(name, desc).execute();
    }

    //Action that is done on action with list items.
    @Override
    public void onListFragmentInteraction(int position) {
        Intent intent = new Intent(this, ActivityProjectView.class);
        String projectID = projectsList.get(position).getID();
        intent.putExtra("projectID", projectID);
        intent.putExtra("email", userMail);
        startActivity(intent);
    }
}
