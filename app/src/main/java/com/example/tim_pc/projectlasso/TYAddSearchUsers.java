package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TYAddSearchUsers extends Activity {

    AutoCompleteTextView auto;
    ArrayAdapter<String> adapter;
    String[] itemList = new String[] {"Search User"};
    List<TYUser> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search_users);

        try{
            auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            auto.addTextChangedListener(new TYMyAutoCompleteListener(TYAddSearchUsers.this));
            adapter = new ArrayAdapter<String>(TYAddSearchUsers.this, android.R.layout.simple_dropdown_item_1line, itemList);
            auto.setAdapter(adapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((products.size() > 0)) {
                    Intent intent = new Intent();
                    intent.putExtra("user", products.get(0));
                    setResult(RESULT_OK, intent);
                    finish();
                }else if (auto.getText() == null){
                    Toast.makeText(getBaseContext(), "Please search for user", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getBaseContext(), "User does not exist", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void getItemsFromDb(String searchTerm){
        if(searchTerm.toString().length() > 1) {
            products = new ArrayList<TYUser>();
            new TYMySQLHandler().execute(searchTerm);
        }
        adapter.notifyDataSetChanged();
    }

    class TYMySQLHandler extends AsyncTask<String, String , String> {

        //private List<TYUser> membersList;
        String resultString = "";
        JSONObject result = null;
        JSONArray resultArray = null;

        InputStream is = null;
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
        protected String doInBackground(String... params) {

            try {
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/grabUsers.php?search="+params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println(resultString);
                result = new JSONObject(resultString);
                products = new ArrayList<TYUser>();

                resultArray = result.getJSONArray("users");

                for(int i = 0; i<resultArray.length(); i++){
                    JSONObject temp = resultArray.getJSONObject(i);
                    TYUser user = new TYUser(temp.getString("name"), R.mipmap.face1, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));
                    products.add(user);
                    //adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String string){
            int rowCount = products.size();
            if (rowCount > 0){
                String[] item = new String[rowCount];
                int x = 0;

                for (TYUser record : products) {

                    item[x] = record.getUsername();
                    x++;
                }
                Arrays.sort(item);
                itemList = item;
            }else{
                itemList = new String[] {"Search User"};
            }
            adapter.notifyDataSetChanged();
        }



    }
}
