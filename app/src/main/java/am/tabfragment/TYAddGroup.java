package am.tabfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TYAddGroup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyadd_group);

        final TYUser user = getIntent().getParcelableExtra("user");


        Button create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName;
                EditText text = (EditText) findViewById(R.id.groupNameBox);
                groupName = text.getText().toString();
                new TYMySQLHandler().execute(user.getUsername(),groupName);
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
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/insertUserGroupID.php?username=" + params[0] + "&groupname="+params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(String string){
            String groupName;
            EditText text = (EditText) findViewById(R.id.groupNameBox);
            groupName = text.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("groupName", groupName);
            setResult(RESULT_OK, intent);
            finish();
        }



    }
}
