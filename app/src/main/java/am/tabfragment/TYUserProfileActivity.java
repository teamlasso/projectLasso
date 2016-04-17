
package am.tabfragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TYUserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final TYUser user = getIntent().getParcelableExtra("user");

        ImageView imageView = (ImageView) findViewById(R.id.item_icon);
        imageView.setImageResource(user.getImageID());

        TextInputLayout inputName = (TextInputLayout) findViewById(R.id.input_layout_name);
        

        //Setting the name of the User
        TextView nameText = (EditText) findViewById(R.id.userName);
        nameText.setText(user.getName());

        TextView emailText = (EditText) findViewById(R.id.userEmail);
        emailText.setText(user.getEmail());

        TextView phoneText = (EditText) findViewById(R.id.userPhoneNumber);
        phoneText.setText(user.getPhoneNumber());

        TextView usernameText = (EditText) findViewById(R.id.userUsername);
        usernameText.setText(user.getUsername());

        Button remove = (Button) findViewById(R.id.removebutton);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new TYMySQLHandler().execute(user.getUsername());
            }
        });

    }
    class TYMySQLHandler extends AsyncTask<String, String , String> {


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

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/removeFromGroup.php?username=" + params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(String string){

            setResult(RESULT_OK);
            finish();
        }



    }

}
