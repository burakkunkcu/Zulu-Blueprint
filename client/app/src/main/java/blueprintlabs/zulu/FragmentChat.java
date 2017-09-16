package blueprintlabs.zulu;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import blueprintlabs.zulu.adapters.ChatAdapter;
import blueprint.zulu.util.*;
import blueprintlabs.zulu.socket.Client;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentChat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lw;
    private Button button;
    private EditText input;
    ChatAdapter adapter;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentChat() {
        // Required empty public constructor
    }

    //Stub constructor to create FragmentList using params
    public static FragmentChat newInstance(String param1, String param2) {
        FragmentChat fragment = new FragmentChat();
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

        adapter = new ChatAdapter(getActivity().getApplicationContext(), R.layout.chat_right);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        button = (Button) view.findViewById(R.id.send);
        input = (EditText) view.findViewById(R.id.message);
        lw = (ListView) view.findViewById(R.id.messages);

        lw.setAdapter(adapter);

        input = (EditText) view.findViewById(R.id.message);
        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        lw.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lw.setAdapter(adapter);

        //to scroll the list view to bottom on data change
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                lw.setSelection(adapter.getCount() - 1);
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

    /**
     * These 3 methods update the view with incoming message.
     */
    boolean sendChatMessage() {
        ActivityProjectView activity = (ActivityProjectView) getActivity();
        adapter.add(new Message(input.getText().toString(), activity.globalUser.getName()));

        activity.sendMessageBroadcast(activity.globalUser.getName(), input.getText().toString());

        input.setText("");

        return true;
    }

    boolean sendChatMessage(String message, String sender) {
        adapter.add(new Message(message, sender));
        return true;
    }

    boolean sendChatMessage(Message message){
        adapter.add(message);
        return true;
    }

    /**
     * AsyncTask to initiate chat with last 20 messages previously loaded.
     */
    public class getInitialMessages extends AsyncTask<Void, Void, ArrayList<Message>>{
        private final Project project;
        private final String[] args;

        public getInitialMessages(){
            project =((ActivityProjectView) FragmentChat.this.getActivity()).globalProject;
            args = new String[1];
            args[0] = project.getID();
        }

        @Override
        protected ArrayList<Message> doInBackground(Void... params) {
            Client client = new Client("project", "chat", args);
            client.start();
            client.run();
            return (ArrayList<Message>)client.getResult();
        }

        @Override
        protected void onPostExecute(ArrayList<Message> messages) {
            for (Message m : messages){
                sendChatMessage(m);
            }
        }
    }

    /**
     * Interface for instantiating activites to implement to communicate with this fragment.
     * Currently method callbacks are directly used by the fragment instead of this listener.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
