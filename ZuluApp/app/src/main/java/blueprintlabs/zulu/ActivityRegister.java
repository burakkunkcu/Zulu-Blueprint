package blueprintlabs.zulu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import blueprintlabs.zulu.resources.Project;
import blueprintlabs.zulu.socket.Client;

public class ActivityRegister extends AppCompatActivity {
    EditText regMail;
    EditText regPassword;
    EditText regName;
    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        regMail = (EditText) findViewById(R.id.regMail);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regName = (EditText) findViewById(R.id.regName);
        regButton = (Button) findViewById(R.id.regbutton);

        //getActionBar().setTitle("REGISTER");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    private void register(){
        // Reset errors.
        regMail.setError(null);
        regPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = regMail.getText().toString();
        String password = regPassword.getText().toString();
        String name = regName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            regPassword.setError(getString(R.string.error_invalid_password));
            focusView = regPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            regMail.setError(getString(R.string.error_field_required));
            focusView = regMail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            regMail.setError(getString(R.string.error_invalid_email));
            focusView = regMail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a toast, and return to login screen
            new RegisterTask(email, password, name, this).execute((Void) null);
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String[] args;

        RegisterTask(String email, String password, String name, Context context) {
            mEmail = email;
            mPassword = password;
            mName = name;
            this.context = context;
            args = new String[3];
            args[0] = mEmail;
            args[1] = mName;
            args[2] = mPassword;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Client client = new Client("register", "", args);
            client.start();
            System.out.println(client.getResult());
            boolean aa = (boolean) client.getResult();
            return aa;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Toast.makeText(ActivityRegister.this, "Register successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context ,ActivityLogin.class);
                intent.putExtra("fromreg", true);
                intent.putExtra("mail", mEmail);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ActivityRegister.this, "Error connecting network, please try again", Toast.LENGTH_SHORT);
            }
        }
    }
}
