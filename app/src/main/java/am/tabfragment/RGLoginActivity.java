package am.tabfragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;



import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import android.app.ProgressDialog;
import android.util.Log;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Bind;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RGLoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog;

    @Bind(R.id.input_UserName) EditText LoginUserName;
    @Bind(R.id.input_password) EditText LoginPW;
    @Bind(R.id.btn_login) Button Button2Login;
    @Bind(R.id.link_signup) TextView Link2Signup;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rglogin);
        ButterKnife.bind(this);

        Button2Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        Link2Signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RGRegister.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        Button2Login.setEnabled(false);



        String Username = LoginUserName.getText().toString();
        String password = LoginPW.getText().toString();
        new TYMySQLHandler().execute(Username, password);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to Tabs!!!
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Button2Login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Lasso Login failed", Toast.LENGTH_LONG).show();

        Button2Login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String CurrentUserName = LoginUserName.getText().toString();
        String Currentpassword = LoginPW.getText().toString();

        if (CurrentUserName.isEmpty() || CurrentUserName.length() < 4) {
            LoginUserName.setError("Please enter a valid Lasso User Name!");
            valid = false;
        } else {
            LoginUserName.setError(null);
        }

        if (Currentpassword.isEmpty() || Currentpassword.length() < 6) {
            LoginPW.setError("At least 6 letters are required for the password!");
            valid = false;
        } else {
            LoginPW.setError(null);
        }



        return valid;
    }
    class TYMySQLHandler extends AsyncTask<String, String , String> {


        JSONObject result = null;
        JSONArray resultArray = null;
        String resultString = "";
        OkHttpClient client = new OkHttpClient();

        public TYMySQLHandler(){

        }
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(RGLoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/login.php?username=" + params[0] + "&password="+params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(resultString);
                result = new JSONObject(resultString);



                if(result.getInt("success") == 1) {
                    resultArray = result.getJSONArray("user");
                    SessionManager manager = new SessionManager(RGLoginActivity.this);

                    JSONObject temp = resultArray.getJSONObject(0);
                    TYUser user;
                    if(!temp.getString("groupID").equals("null")) {
                        user = new TYUser(temp.getString("name"), R.mipmap.ic_launcher, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));
                    }else{
                        user = new TYUser(temp.getString("name"), R.mipmap.ic_launcher, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), 1);
                    }

                    manager.setLoginStatus(user);
                    Intent intent = new Intent(RGLoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }else if(result.getInt("success") == 0){
                    return "Login failed";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String string){
            progressDialog.dismiss();
            if(string != null){
                Toast.makeText(getBaseContext(), "Lasso Login failed", Toast.LENGTH_LONG).show();
                Button2Login.setEnabled(true);
            }
        }



    }
}