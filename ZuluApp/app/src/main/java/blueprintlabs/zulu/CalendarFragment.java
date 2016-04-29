package blueprintlabs.zulu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import blueprintlabs.zulu.resources.Project;
import blueprintlabs.zulu.resources.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CalendarView cv;
    Button newEventButton;
    Button meetupButton;

    private OnFragmentInteractionListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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


        // assign event handler
        /**
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                //Toast.makeText(ProjectView.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
         **/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        cv = (CalendarView) view.findViewById(R.id.calendar_view);

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
                    Toast.makeText(getActivity(), "Please select a day first!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(User user) {
        if (mListener != null) {
            mListener.onFragmentInteraction(user);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(User user);
    }
}
