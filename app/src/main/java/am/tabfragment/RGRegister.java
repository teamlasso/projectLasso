package am.tabfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Bind;


public class RGRegister extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText Rname;
    @Bind(R.id.input_email) EditText Remail;
    @Bind(R.id.input_password) EditText rPW;
    @Bind(R.id.input_PhoneNumber) EditText rPNumber;
    @Bind(R.id.input_UserName) EditText rUserName;
    @Bind(R.id.btn_signup) Button Button2SignUp;
    @Bind(R.id.link_login) TextView Link2Login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgregister);
        ButterKnife.bind(this);

        Button2SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        Link2Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }
    public void signup() {
        Log.d(TAG, "Signup for Lasso!");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        Button2SignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RGRegister.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating your Lasso account...");
        progressDialog.show();

        String name = Rname.getText().toString();
        String email = Remail.getText().toString();
        String password = rPW.getText().toString();
        String phoneNumber = rPNumber.getText().toString();
        String Username = rUserName.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        Button2SignUp.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Lasso Login failed", Toast.LENGTH_LONG).show();

        Button2SignUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String name2 = Rname.getText().toString();
        String email2 = Remail.getText().toString();
        String password2 = rPW.getText().toString();
        String phoneNumber2 = rPNumber.getText().toString();
        String Username2 = rUserName.getText().toString();


        if (name2.isEmpty() || name2.length() < 4) {
            Rname.setError("Your full name must be least 4 characters long!");
            valid = false;
        } else {
            Rname.setError(null);
        }

        if (phoneNumber2.isEmpty() || phoneNumber2.length() < 10) {
            rPNumber.setError("Your phone number must be least 10 characters long!");
            valid = false;
        } else {
            rPNumber.setError(null);
        }

        if (Username2.isEmpty() || Username2.length() < 4) {
            rUserName.setError("Your UserName must be least 4 characters long!");
            valid = false;
        } else {
            rUserName.setError(null);
        }

        if (email2.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            Remail.setError("Please enter a valid email address!");
            valid = false;
        } else {
            Remail.setError(null);
        }

        if (password2.isEmpty() || password2.length() < 6 ) {
            rPW.setError("Password must be at least 6 characters long, please!");
            valid = false;
        } else {
            rPW.setError(null);
        }
        return valid;
    }
}