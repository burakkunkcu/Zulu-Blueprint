package blueprintlabs.zulu;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import blueprint.zulu.util.CalendarView;
import blueprint.zulu.util.*;
import blueprintlabs.zulu.socket.Client;


/**
 *Fragment to handle the calendar functions. Instantiated by the {@Link ActivityProjectView}
 */
public class FragmentCalendar extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //Properties
    CalendarView cv;
    Button newEventButton;
    Button meetupButton;
    ArrayList<Date> meetupList;

    private OnFragmentInteractionListener mListener;

    public FragmentCalendar() {
        // Required empty public constructor
    }

    /**
    * Stub method to create new instance using parameters.
     */
    public static FragmentCalendar newInstance(String param1, String param2) {
        FragmentCalendar fragment = new FragmentCalendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        cv = (CalendarView) view.findViewById(R.id.calendar_view);


        //Dummy variables
        HashSet<Date> aa = new HashSet<Date>();
        aa.add(new Date());
        aa.add(new Date(116, 3, 11));
        aa.add(new Date(116, 3, 30));
        aa.add(new Date(116, 3, 2));

        HashSet<Date> bb = new HashSet<Date>();
        aa.add(new Date());
        aa.add(new Date(116, 3, 2));
        aa.add(new Date(116, 3, 7));
        aa.add(new Date(116, 3, 27));

        cv.updateCalendar(aa, bb);

        newEventButton = (Button)view.findViewById(R.id.button_newevent);
        meetupButton = (Button) view.findViewById(R.id.button_meetup);

        /**
        ActivityProjectView activity = (ActivityProjectView) getActivity();
        if(!activity.globalUser.hasPermission(User.MEETUP_PERMISSION)){
            meetupButton.setEnabled(false);
        }
        **/

        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cv.selectedDate != null) {
                    Bundle args = new Bundle();
                    //set clicked days date and month to arguments to the dialog fragment
                    Date today = cv.selectedDate;
                    DialogNewEvent box = new DialogNewEvent();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMMM");
                    args.putString("date", sdf.format(((Date) today)));
                    box.setArguments(args);

                    FragmentActivity activity = (FragmentActivity) (getActivity());
                    FragmentManager fm = activity.getSupportFragmentManager();
                    box.show(activity.getFragmentManager(), "meetupbox");
                }
                else{
                    Toast.makeText(getActivity(), "Please select a day first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        meetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cv.selectedDate != null) {
                    if (meetupButton.isEnabled()) {
                        Bundle args = new Bundle();
                        //set clicked days date and month to arguments to the dialog fragment
                        Date today = cv.selectedDate;
                        DialogMeetup box = new DialogMeetup();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMMM");
                        args.putString("date", sdf.format(((Date) today)));
                        box.setArguments(args);

                        FragmentActivity activity = (FragmentActivity) (getActivity());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        box.show(activity.getFragmentManager(), "meetupbox");
                    }
                    else{
                        //Toast.makeText(getActivity(), "You don't have the permission to arrange a meetup.", Toast.LENGTH_SHORT).show();
                        meetupButton.setError("You don't have permission to arrange a meetup");
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Please select a day first!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Call a date to arrange a meetup at that date
    public class MeetupTask extends AsyncTask<Void, Void, ArrayList<Date>>{
        final String[] args;

        public MeetupTask(){
            args = new String[2];
            args[0] = ((ActivityProjectView)getActivity()).globalProject.getID();
            Gson gson = new Gson();
            String jsonInString = gson.toJson(new Date());
            args[1] = (new Date()).toString();
        }

        @Override
        protected ArrayList<Date> doInBackground(Void... params) {
            Client client = new Client("calendar", "meetup", args);
            client.start();
            client.run();
            return (ArrayList<Date>) client.getResult();
        }

        @Override
        protected void onPostExecute(ArrayList<Date> result) {
            if(result != null){
                meetupList = result;
            }
        }
    }

    public ArrayList<Date> getMeetupList(){
        return meetupList;
    }

    /**
     * Interface for instantiating activites to implement to communicate with this fragment.
     * Currently method callbacks are directly used by the fragment instead of this listener.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(User user);
    }
}
