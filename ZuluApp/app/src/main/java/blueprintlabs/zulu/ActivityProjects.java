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

        if(currentUser != null){
            projectsList = currentUser.getProjects();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProjectsAdapter(projectsList, ActivityProjects.this);
        mRecyclerView.setAdapter(adapter);

        //getActionBar().setTitle("PROJECTS");

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
    //GET DATA FROM SERVER

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
            client.run();
            User user = (User) client.getResult();
            return user;
        }


        protected void onPostExecute(User user) {
            if (user != null){
                currentUser = user;
            }
        }
    }

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
            client.run();
            return (boolean)client.getResult();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(ActivityProjects.this, "Project successfully added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addProject(String name, String desc){
        new addProject(name, desc).execute();
    }

    @Override
    public void onListFragmentInteraction(int position) {
        Intent intent = new Intent(this, ActivityProjectView.class);
        String projectID = projectsList.get(position).getID();
        intent.putExtra("projectID", projectID);
        intent.putExtra("email", userMail);
        startActivity(intent);
    }
}
