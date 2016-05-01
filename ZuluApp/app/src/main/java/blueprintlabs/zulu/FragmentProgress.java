package blueprintlabs.zulu;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import blueprintlabs.zulu.adapters.ProgressAdapter;
import blueprint.zulu.util.*;
import blueprintlabs.zulu.socket.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * The fragment that displays global and user progress.
 */
public class FragmentProgress extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    ProgressAdapter adapter;
    ArrayList<Task> userTasks = new ArrayList<Task>();
    ArrayList<Task> allTasks = new ArrayList<Task>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentProgress() {
    }

    // Initialize the fragment with number of colums to draw
    @SuppressWarnings("unused")
    public static FragmentProgress newInstance(int columnCount) {
        FragmentProgress fragment = new FragmentProgress();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progressitem, container, false);

        ActivityProjectView parent = (ActivityProjectView) getActivity();

        String projectCalendarID = parent.globalProject.getCalendar();

        String userCalendarID = parent.globalUser.getCalendar();
        String ProjectID = parent.globalProject.getID();

        adapter = new ProgressAdapter(allTasks, userTasks, mListener);

        updateAllTasks(projectCalendarID);
        updateUserTasks(userCalendarID, ProjectID);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public class getTasksbyCalendarID extends AsyncTask<Void, Void, ArrayList<Task>> {
        private final String[] args;

        public getTasksbyCalendarID(String ID){
            args = new String[1];
            args[0] = ID;
        }

        @Override
        protected ArrayList<Task> doInBackground(Void... params) {
            Client client = new Client("calendar", "gettasks", args);
            client.start();
            ArrayList<Task> result = (ArrayList<Task>) client.getResult();
            return  result;
        }

        @Override
        protected void onPostExecute(ArrayList<Task> tasks) {
            if(tasks != null){
                allTasks = tasks;
            }
            else{
                Toast.makeText(getActivity(), "Error retrieving tasks", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class getTasksUserProject extends AsyncTask<Void, Void, ArrayList<Task>> {
        private final String[] args;

        public getTasksUserProject(String ID , String projectID){
            args = new String[2];
            args[0] = ID;
            args[1] = projectID;
        }

        @Override
        protected ArrayList<Task> doInBackground(Void... params) {
            Client client = new Client("calendar", "gettasksofproject", args);
            client.start();
            ArrayList<Task> result = (ArrayList<Task>) client.getResult();
            return  result;
        }

        @Override
        protected void onPostExecute(ArrayList<Task> tasks) {
            if(tasks != null){
                userTasks = tasks;
            }
            else{
                Toast.makeText(getActivity(), "Error retrieving tasks", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateAllTasks(String s){
        new getTasksbyCalendarID(s).execute();
        adapter.notifyDataSetChanged();
    }

    private void updateUserTasks(String s, String projectID){
        new getTasksUserProject(s, projectID).execute();
        adapter.notifyDataSetChanged();
    }

    /**
     * Interface for instantiating activites to implement to communicate with this fragment.
     * Currently method callbacks are directly used by the fragment instead of this listener.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Task item);
    }
}
