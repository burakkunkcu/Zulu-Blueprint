package blueprintlabs.zulu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import blueprintlabs.zulu.adapters.DashboardAdapter;
import blueprintlabs.zulu.resources.Action;
import blueprintlabs.zulu.resources.Message;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentDashboard extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int NOTIFY_ID = 1231233;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private DashboardAdapter adapter;
    NotificationCompat.Builder mBuilder;
    StringBuilder notif = new StringBuilder();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentDashboard() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentDashboard newInstance(int columnCount) {
        FragmentDashboard fragment = new FragmentDashboard();
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
        View view = inflater.inflate(R.layout.dashboard_list_row_layout, container, false);

        ArrayList<Action> aa = new ArrayList<Action>();
        aa.add(new Action("aa", "bb", "cc"));
        aa.add(new Action("aa", "bb", "cc"));
        aa.add(new Action("aa", "bb", "cc"));
        aa.add(new Action("aa", "bb", "cc"));
        aa.add(new Action("aa", "bb", "cc"));
        aa.add(new Action("aa", "bb", "cc"));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new DashboardAdapter(aa, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    boolean updateView(String string) {
        adapter.add(new Action(string, "", ""));
        notif.append(string);
        mBuilder = new android.support.v7.app.NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.next_icon)
                        .setContentTitle("Zulu")
                        .setContentText(notif.toString());
        Intent resultIntent = new Intent(getActivity(), ActivityProjectView.class);
        // The stack builder object will contain an artificial back stack for the started activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ActivityProjectView.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        //this is necessart for Android Gingerbread and below versions
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int notifyID = NOTIFY_ID;
        mNotificationManager.notify(notifyID, mBuilder.build());
        return true;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Action item);
    }
}
