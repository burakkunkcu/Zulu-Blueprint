package blueprintlabs.loginregister;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regMail = (EditText) findViewById(R.id.regMail);
        final EditText regPassword = (EditText) findViewById(R.id.regPassword);
        final Button regButton = (Button) findViewById(R.id.regbutton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = regMail.getText().toString();
                final String password = regPassword.getText().toString();

                boolean control = false;
                String controlString = "@";
                if(mail.contains(controlString) && (mail.contains(".com") || mail.contains(".org")))
                    control = true;
                if (control) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("E-Mail adress does not exist").setNegativeButton("Retry", null).create().show();
                }

            }
        });
    }
}