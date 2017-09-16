package blueprintlabs.zulu;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import blueprintlabs.zulu.socket.Client;
import blueprint.zulu.util.*;

/**
 * This service runs in the background and listens to incoming messages from the chat server, and broadcasts received messages in the app
 * Created by Ahmet Burak on 29.4.2016.
 */
public class ServiceChat extends Service {
    private static final String SERVICE_MESSAGE = "message";
    private static final String SERVICE_OUTGOING_MESSAGE = "message";
    private static final String UPDATE_MESSAGE = "update";
    private final String HOST = "46.101.150.199";
    private final int PORT = 2150;

    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    String projectID;

    Thread inputThread;
    Thread outputThread;

    private BroadcastReceiver serviceReceiver;
    private final IBinder mBinder = new LocalBinder();

    public ServiceChat(){
    }

    @Override
    public void onCreate() {
        super.onCreate();


        serviceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String m = intent.getStringExtra("message");
                    String s = intent.getStringExtra("sender");
                    String p = intent.getStringExtra("projectID");
                    String[] args = {s, p, m};
                    Client client = new Client("chat", "send", args);
                    client.start();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        };

        if (serviceReceiver != null) {
            IntentFilter intentFilter = new IntentFilter(SERVICE_OUTGOING_MESSAGE);
            registerReceiver(serviceReceiver, intentFilter);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            socket.setKeepAlive(true);

            //Listen for incoming messages on a new thread
            inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = null;
                    Timer updateCheckerTimer = new Timer(true);
                    updateCheckerTimer.scheduleAtFixedRate(new TimerTask(){
                        public void run(){
                            if(projectID != null) {
                                String args[] = {projectID};
                                Client c = new Client("chat", "getmessages", args);
                                c.start();
                                ArrayList<Message> messages = (ArrayList<Message>) c.getResult();
                                for (Message s : messages){
                                    sendBroadcastMessage(SERVICE_MESSAGE, s.getSenderID(), s.getMessageBody());
                                }

                                String args2[] = {projectID};
                                Client c2 = new Client("chat", "getmessages", args);
                                c.start();
                                ArrayList<String> updates = (ArrayList<String>) c.getResult();
                                for (String s : updates){
                                    sendBroadcastMessage(UPDATE_MESSAGE, s, "");
                                }
                            }
                        }
                    }, 0 , 20 * 1000);
                }
            });

            inputThread.start();
            outputThread.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void sendBroadcastMessage(String intentFilterName, String sender, String message) {
        Intent intent = new Intent(intentFilterName);
        if(intentFilterName == SERVICE_MESSAGE && sender != null && message != null) {
            intent.putExtra("sender", sender);
            intent.putExtra("message", message);
            sendBroadcast(intent);
        }
        else if(intentFilterName == UPDATE_MESSAGE && message != null){
            intent.putExtra("message", message);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(inputThread != null){
            inputThread.interrupt();
            inputThread = null;
        }
        if(outputThread != null){
            outputThread.interrupt();
            outputThread = null;
        }

        unregisterReceiver(serviceReceiver);
    }

    public class LocalBinder extends Binder {
        ServiceChat getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceChat.this;
        }
    }

    public void setProjectID(String s){
        projectID = s;
    }
}
