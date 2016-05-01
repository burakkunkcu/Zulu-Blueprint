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
import android.widget.Button;
import android.widget.Toast;

import blueprintlabs.zulu.adapters.TaskAdapter;
import blueprint.zulu.util.*;
import blueprintlabs.zulu.socket.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that shows the current tasks.
 */
public class FragmentTasks extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    List<Task> data;
    TaskAdapter adapter;

    Button bttnAdd;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     * Not yet implemented
     */
    public FragmentTasks() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentTasks newInstance(int columnCount) {
        FragmentTasks fragment = new FragmentTasks();
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
        View view = inflater.inflate(R.layout.fragment_person_list, container, false);

        ActivityProjectView parent = (ActivityProjectView) getActivity();
        String calendarID = parent.globalProject.getCalendar();

        updateTasks(calendarID);

        adapter = new TaskAdapter(data, mListener, FragmentTasks.this);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView aaa = (RecyclerView) view;
            if (mColumnCount <= 1) {
                aaa.setLayoutManager(new LinearLayoutManager(context));
            } else {
                aaa.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            aaa.setAdapter(adapter);

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

    public class getTasksbyCalendarID extends AsyncTask<Void, Void, ArrayList<Task>>{
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
                data = tasks;
            }
            else{
                Toast.makeText(getActivity(), "Error retrieving tasks", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateTasks(String s){
        new getTasksbyCalendarID(s).execute();
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
