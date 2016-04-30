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

/**
 * Created by Ahmet Burak on 29.4.2016.
 */
public class ServiceChat extends Service {
    private static final String SERVICE_MESSAGE = "message";
    private static final String SERVICE_OUTGOING_MESSAGE = "message";
    private static final String UPDATE_MESSAGE = "update";
    private final String HOST = "";
    private final int PORT = 111;

    Socket socket;
    BufferedReader in;
    BufferedWriter out;

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
                //TODO SEND MESSAGE USING THE SOCKET
            }
        };

        if (serviceReceiver != null) {
            IntentFilter intentFilter = new IntentFilter(SERVICE_OUTGOING_MESSAGE);
            registerReceiver(serviceReceiver, intentFilter);
        }

        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //Listen for incoming messages on a new thread
            inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = null;
                    try {
                        while ((s = in.readLine()) != null) {
                            //TODO PROCESS THE MESSAGE USING THE BROADCASTER
                        }
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //Listen to outgoing messages on a new thread
            outputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //TODO FEED THE OUTPUTSTREAM
                }
            });
            inputThread.start();
            outputThread.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
}
