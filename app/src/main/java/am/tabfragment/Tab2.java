package am.tabfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab2 extends Fragment {
    String username;
    private List<FeedItem> feed = new ArrayList<FeedItem>();    //feed list
    EditText mStatusView;   //status textbox
    ArrayAdapter<FeedItem> adapter;
    View v2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v2 = inflater.inflate(R.layout.activity_feed, container, false);

        adapter = new MyListAdapter(feed);
        final ListView feedListView = (ListView) v2.findViewById(R.id.feedListView);
        feedListView.setAdapter(adapter);
        SessionManager manager = new SessionManager(getContext());
        username = manager.getusername();
        new TYMySQLHandler().execute("pull", username);

        Button mPostStatusButton = (Button) v2.findViewById(R.id.post_status_button);

        mPostStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mStatusView = (EditText) v2.findViewById(R.id.editText);
                String status = mStatusView.getText().toString();   //get status String from textbox
                mStatusView.setText("");

                //TODO: replace with currentUser information
                String timestamp = getTimestamp();
                new TYMySQLHandler().execute("push", username, status, timestamp, Integer.toString(R.mipmap.face), Integer.toString(R.mipmap.drunk_emoji));

                adapter.notifyDataSetChanged();

                // Scroll to bottom of list
                feedListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //feedListView.smoothScrollToPosition(0);
                        feedListView.setSelection(feedListView.getCount()-1);
                    }
                } );
            }
        } );


        return v2;
    }


    /* Generic method for adding FeedItem to feed */
    private void addToFeed(int profPicId, int statusPicId, String name, String status)
    {
        feed.add(new FeedItem(profPicId, statusPicId, name, status, this.getTimestamp()));
    }


    private String getTimestamp()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

        return sdf.format(cal.getTime());
    }



    private class MyListAdapter extends ArrayAdapter<FeedItem>
    {
        //Create copy of the list since there are two lists.
        List<FeedItem> localList;

        //Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<FeedItem> feed)
        {
            //Use the List that is passed in from the parameter.
            super(getActivity(), R.layout.feeditem_view, feed);
            localList = feed;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;

            if (convertView == null) {
                //itemView = getLayoutInflater().inflate(R.layout.feeditem_view, parent, false);
                itemView = getActivity().getLayoutInflater().inflate(R.layout.feeditem_view, parent, false);

            } else {
                itemView = convertView;
            }


            //Iterate through the list of Users(member or emergency).
            FeedItem currentFeedItem = localList.get(position);


            // Set profPic of FeedItem
            ImageView profImageView = (ImageView) itemView.findViewById(R.id.profPic);
            profImageView.setImageResource(currentFeedItem.getProfPicId());

            // Set statusPic of FeedItem
            //ImageView statusImageView = (ImageView) itemView.findViewById(R.id.statusPic);
            //statusImageView.setImageResource(currentFeedItem.getStatusPicId());

            // Set name of FeedItem
            TextView nameTextView = (TextView) itemView.findViewById(R.id.name);
            nameTextView.setText(currentFeedItem.getName());

            // Set status of FeedItem
            TextView statusTextView = (TextView) itemView.findViewById(R.id.status);
            statusTextView.setText(currentFeedItem.getStatus());

            // Set timestamp of FeedItem
            TextView timestampTextView = (TextView) itemView.findViewById(R.id.timestamp);
            timestampTextView.setText(currentFeedItem.getTimestamp());


            return itemView;
        }
    }


    class TYMySQLHandler extends AsyncTask<String, String , String>
    {
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
        String run(String... params) throws IOException {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host("ec2-52-87-164-152.compute-1.amazonaws.com")
                    .addPathSegment("insertStatus.php")
                    .addQueryParameter("username", params[0])
                    .addQueryParameter("status", params[1])
                    .addQueryParameter("timestamp", params[2])
                    .addQueryParameter("profPicID",params[3])
                    .addQueryParameter("statusPicID", params[4])
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected void onPreExecute() {
            adapter.clear();
        }

        @Override
        protected String doInBackground(String... params)
        {
            /* Grab */
            if (params[0].equals("pull")) {
                try {
                    // "URL + currentUser username"
                    resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/grabStatuses.php?username=" + params[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.println(resultString);
                    result = new JSONObject(resultString);

                    resultArray = result.getJSONArray("users");
                    if (result.getInt("success") == 1) {
                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject temp = resultArray.getJSONObject(i);

                            //TYUser user = new TYUser(temp.getString("name"), R.mipmap.face1, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));
                            feed.add(new FeedItem(R.mipmap.face, R.mipmap.face, temp.getString("username"), temp.getString("status"), temp.getString("timestamp")));

                            //adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            /* Push */
            else if (params[0].equals("push")) {
                try {
                    // "URL + currentUser username"
                    resultString = run(params[1], params[2], params[3], params[4], params[5]);
                    System.out.println(resultString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "pushed";
            }

            return "success";
        }


        @Override
        protected void onPostExecute(String string) {
            if(string.equals("pushed")){
                new TYMySQLHandler().execute("pull", username);
                //Todo add to list instead of pulling again.
            }
            adapter.notifyDataSetChanged();
        }
    }

}
