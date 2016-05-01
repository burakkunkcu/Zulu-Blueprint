package blueprintlabs.zulu;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blueprintlabs.zulu.resources.Action;
import blueprintlabs.zulu.resources.Project;
import blueprintlabs.zulu.resources.Task;
import blueprintlabs.zulu.resources.User;
import blueprintlabs.zulu.socket.Client;

public class ActivityProjectView extends AppCompatActivity implements FragmentTasks.OnListFragmentInteractionListener,
                                                                FragmentDashboard.OnListFragmentInteractionListener,
                                                                    FragmentProgress.OnListFragmentInteractionListener,
                                                                        FragmentUsers.OnListFragmentInteractionListener,
                                                                            FragmentCalendar.OnFragmentInteractionListener
                                                                                ,FragmentChat.OnFragmentInteractionListener{


    private static final String SERVICE_MESSAGE = "message";
    private static final String SERVICE_OUTGOING_MESSAGE = "message";
    private static final String UPDATE_MESSAGE = "update";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     *The {@Link Project} that will be represented
     */
    public Project globalProject;
    public User globalUser;
    public ArrayList<Date> meetupDates;

    /**
     * Represents if a service is bound to the activity and the bound service
     */
    private boolean mBound = false;
    private ServiceChat mService;

    private BroadcastReceiver messageReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == SERVICE_MESSAGE) {
                if (mSectionsPagerAdapter != null) {
                    if (mSectionsPagerAdapter.getRegisteredFragment(5) != null) {
                        if (mSectionsPagerAdapter.getRegisteredFragment(5) instanceof FragmentChat) {
                            FragmentChat f = (FragmentChat) mSectionsPagerAdapter.getRegisteredFragment(5);
                            String message = intent.getStringExtra("message");
                            String sender = intent.getStringExtra("sender");
                            f.sendChatMessage(message, sender);
                        }
                    }
                }
            }
            else if(intent.getAction() == UPDATE_MESSAGE){
                if (mSectionsPagerAdapter != null) {
                    if (mSectionsPagerAdapter.getRegisteredFragment(0) != null) {
                        if (mSectionsPagerAdapter.getRegisteredFragment(0) instanceof FragmentChat) {
                            FragmentDashboard f = (FragmentDashboard) mSectionsPagerAdapter.getRegisteredFragment(0);
                            String message = intent.getStringExtra("message");
                            f.updateView(message);
                        }
                    }
                }
            }
        }
    };


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServiceChat.LocalBinder binder = (ServiceChat.LocalBinder) service;
            ServiceChat mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO INITIATE THE PROJECT HERE
        Intent intent = getIntent();
        String projectID = intent.getStringExtra("projectID");
        if(globalProject == null){
            new getProjectbyID(projectID).execute();
        }

        if(globalUser == null){
            new getUserTask(intent.getStringExtra("email")).execute();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVICE_MESSAGE);
        intentFilter.addAction(SERVICE_OUTGOING_MESSAGE);
        registerReceiver(messageReceiver, intentFilter);

        //TODO USE THIS PROJECT NUMBER TO ACCESS THE SERVER AND POPULATE THE INFORMATION OF PROJECT

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //getActionBar().setTitle(project.getName());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /**
         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });
         **/

    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, ServiceChat.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                return true;

            case R.id.refresh:
                //refresh the display, get new values
                //ViewGroup vg = findViewById (R.layout.activity_main);
                //vg.invalidate();
                refresh();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("WIP");
            return rootView;
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch(position) {

                case 0: return FragmentDashboard.newInstance(0);
                case 1: return FragmentCalendar.newInstance("", "");
                case 2: return FragmentTasks.newInstance(0);
                case 3: return FragmentUsers.newInstance(0);
                case 4: return FragmentProgress.newInstance(0);
                case 5: return FragmentChat.newInstance("","");
                default: return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Dashboard";
                case 1:
                    return "Calendar";
                case 2:
                    return "Tasks";
                case 3:
                    return "Overview";
                case 4:
                    return "Progress";
                case 5:
                    return "Chat";

            }
            return null;
        }
    }

    private void refresh(){
        for(int i = 0; i <= 5; i++){
            Fragment fragment = mSectionsPagerAdapter.getRegisteredFragment(i);
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.attach(fragment).detach(fragment).commit();
        }
    }


    public void sendMessageBroadcast(String sender, String message){
        Intent intent = new Intent();
        intent.setAction(SERVICE_OUTGOING_MESSAGE);
        intent.putExtra("sender", sender);
        intent.putExtra("message", message);
        sendBroadcast(intent);
    }

    public class getProjectbyID extends AsyncTask<Void, Void, Project>{
        private final String ID;
        private final String[] args;

        public getProjectbyID(String projectID){
            ID = projectID;
            args = new String[1];
            args[0] = ID;
        }

        @Override
        protected Project doInBackground(Void... params) {
            Client client = new Client("project", "get", args);
            client.start();
            client.run();
            Project project = (Project) client.getResult();
            return project;
        }

        @Override
        protected void onPostExecute(Project project) {
            if (project != null){
                globalProject = project;
            }
        }
    }

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
                globalUser = user;
            }
        }
    }

    public class MeetupTask extends AsyncTask<Void, Void, ArrayList<Date>>{
        final String[] args;

        public MeetupTask(){
            args = new String[2];
            args[0] = globalProject.getID();
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
                meetupDates = result;
            }
        }

    }

    public ArrayList<Date> getMeetupDates(){
        new MeetupTask().execute();
        return meetupDates;
    }

    public class newTask extends AsyncTask<Void, Void, Boolean>{
        final String[] args;
        final Date dateStart;
        final Date datefinish;

        public newTask(String name, Date dateStart, Date datefinish){
            args = new String[2];
            args[0] = name;
            this.dateStart = dateStart;
            this.datefinish = datefinish;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean bool = true;
            for (int i = dateStart.getHours(); i <= datefinish.getHours(); i++){
                Date d = dateStart;
                d.setHours(i);
                Gson gson = new Gson();
                String jsonInString = gson.toJson(d);
                args[1] = jsonInString;
                Client client = new Client("task", "create", args);
                client.start();
                client.run();
                bool = bool && (boolean) client.getResult();
            }
            return bool;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result){
                Toast.makeText(ActivityProjectView.this, "Event created", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onListFragmentInteraction(User item){}
    public void onListFragmentInteraction(Action item){}
    public void onListFragmentInteraction(Task item){}
    public void onFragmentInteraction(User user){}
    public void onFragmentInteraction(Uri uri){}


    public void updateData(){}
}
