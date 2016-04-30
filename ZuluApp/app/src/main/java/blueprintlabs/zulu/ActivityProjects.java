package blueprintlabs.zulu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import blueprintlabs.zulu.adapters.ProjectsAdapter;
import blueprintlabs.zulu.resources.Project;

public class ActivityProjects extends AppCompatActivity implements ProjectsAdapter.OnListFragmentInteractionListener{

    private RecyclerView mRecyclerView;
    private List<Project> projectsList;
    private ProjectsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO ONCE INITIALIZED GET THE PROJECTS FROM SERVER AND INITIATE THE RECYCLERVIEW ADAPTER
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<Project>();
        projectsList.add(new Project(1, "Project1", "HUE", 31, new ArrayList<Integer>(), 31));
        projectsList.add(new Project(1, "Project2", "HUE", 31, new ArrayList<Integer>(), 31));
        projectsList.add(new Project(1, "Project3", "HUE", 31, new ArrayList<Integer>(), 31));

        adapter = new ProjectsAdapter(projectsList, ActivityProjects.this);
        mRecyclerView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add a new project
                if (view != null){
                    //do something
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


    @Override
    public void onListFragmentInteraction(int position) {
        Intent intent = new Intent(this, ActivityProjectView.class);
        intent.putExtra("positionOfProject", position);
        startActivity(intent);
    }
}
