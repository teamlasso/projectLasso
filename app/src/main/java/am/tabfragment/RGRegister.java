package am.tabfragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RGRegister extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    ProgressDialog progressDialog;
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




        String name = Rname.getText().toString();
        String email = Remail.getText().toString();
        String password = rPW.getText().toString();
        String phoneNumber = rPNumber.getText().toString();
        String Username = rUserName.getText().toString();

        new TYMySQLHandler().execute(Username, name, email, phoneNumber, password);
    }


    public void onSignupSuccess() {
        Button2SignUp.setEnabled(true);
        Intent intent = new Intent();
        intent.putExtra("username", rUserName.getText().toString());
        intent.putExtra("password", rPW.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Lasso Signup failed", Toast.LENGTH_LONG).show();

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
        String run(String... params) throws IOException {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host("ec2-52-87-164-152.compute-1.amazonaws.com")
                    .addPathSegment("insertNewUser.php")
                    .addQueryParameter("username", params[0])
                    .addQueryParameter("name", params[1])
                    .addQueryParameter("email", params[2])
                    .addQueryParameter("phonenumber",params[3])
                    .addQueryParameter("password", params[4])
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(RGRegister.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating your Lasso account...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                resultString = run(params[0], params[1], params[2], params[3], params[4]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(resultString);
                result = new JSONObject(resultString);

                if(result.getInt("success") == 1){
                    return "success";
                }
            }catch (JSONException e){
                e.printStackTrace();
            }



                return null;
        }


        @Override
        protected void onPostExecute(String string){
            progressDialog.dismiss();
            if(string != null){
                onSignupSuccess();
            }else{
                onSignupFailed();
            }

        }



    }
}