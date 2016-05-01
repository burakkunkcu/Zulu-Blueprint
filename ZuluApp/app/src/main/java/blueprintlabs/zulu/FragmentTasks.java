package blueprintlabs.zulu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import blueprintlabs.zulu.adapters.TaskAdapter;
import blueprintlabs.zulu.resources.Task;
import blueprintlabs.zulu.resources.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that shows the current tasks.
 */
public class FragmentTasks extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

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

        List<Task> data = parent.globalProject.getTasks();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        //TaskAdapter adapter = new TaskAdapter(data, mListener);
        //recyclerView.setAdapter(adapter);



        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView aaa = (RecyclerView) view;
            if (mColumnCount <= 1) {
                aaa.setLayoutManager(new LinearLayoutManager(context));
            } else {
                aaa.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            aaa.setAdapter(new TaskAdapter(data, mListener, FragmentTasks.this));

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

    /**
     * Interface for instantiating activites to implement to communicate with this fragment.
     * Currently method callbacks are directly used by the fragment instead of this listener.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Task item);
    }
}
