package blueprintslabs.zuluserver
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import blueprintslabs.zuluserver.R;


/**
 * Created by User on 27.4.2016.
 */
public class Client extends AsyncTask<Void, Void, Void>{

    private final String SERVER_ID = "46.101.150.199";
    private final int SERVER_PORT = 27017;
    String response = "";
    TextView text;

    Client(String address, int port, TextView text) {
        address = SERVER_ID;
        port = SERVER_PORT;
        this.text = text;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(SERVER_ID,SERVER_PORT);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();


            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {

            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        text.setText(response);
        super.onPostExecute(result);
    }

}