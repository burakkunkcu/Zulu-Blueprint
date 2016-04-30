package blueprintlabs.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText logMail = (EditText) findViewById(R.id.logMail);
        final EditText logPassword = (EditText) findViewById(R.id.logPassword);
        final TextView regTxt = (TextView) findViewById(R.id.regTxt);
        final Button logButton = (Button) findViewById(R.id.logButton);

        regTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, Projects.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = logMail.getText().toString();
                final String password = logPassword.getText().toString();



                //Get the salt byte[] from the server by using mail
                //If mail does not match with any user, returns false, move on to the byte

                byte salt[];
                //Get salt
                boolean control = false;

                if(byte != null)
                {
                    //Convert to character array
                    char[] pw = new char[password.length];
                    for(int i =0; i < password.length; i++) {
                        pw[i] = password.charAt(i);
                    }
                    //Convert the password to hash and send to the server
                    byte[] hash = Auth.hash(pw ,salt);
                    //send hash, Listen server, get the result(true,false)


                    //update control with hash
                    if(control){

                    }
                    else
                    {
                        //Todo error pw does not match
                    }

                }
                else{
                //todo error the user does not exist
                }





                if(mail.equals("mert@gmail.com")&&password.equals("1"))
                    control = true;

                if (control) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(intent);

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Password or e-mail is incorrect").setNegativeButton("Retry", null).create().show();
                }


            }
        });
    }
}