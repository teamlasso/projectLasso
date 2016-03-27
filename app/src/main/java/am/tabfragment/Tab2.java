package am.tabfragment;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

public class Tab2 extends Fragment {

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

        Button mPostStatusButton = (Button) v2.findViewById(R.id.post_status_button);

        mPostStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mStatusView = (EditText) v2.findViewById(R.id.editText);
                String status = mStatusView.getText().toString();   //get status String from textbox

                //TODO: replace with currentUser information
                addToFeed(R.mipmap.face, R.mipmap.drunk_emoji, "Amanda", status);

                adapter.notifyDataSetChanged();

                // Scroll to bottom of list
                feedListView.post(new Runnable() {
                    @Override
                    public void run() {
                        feedListView.smoothScrollToPosition(0);
                        // feedListView.setSelection(feedListView.getCount()-1);
                    }
                } );
            }
        } );


        return v2;
    }


    /* Generic method for adding FeedItem to feed */
    private void addToFeed(int profPicId, int statusPicId, String name, String status)
    {
        feed.add(0, new FeedItem(profPicId, statusPicId, name, status, this.getTimestamp()));
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
            ImageView statusImageView = (ImageView) itemView.findViewById(R.id.statusPic);
            statusImageView.setImageResource(currentFeedItem.getStatusPicId());

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

     /*
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
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                // "URL + currentUser username"
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/grabStatuses.php?username="+params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println(resultString);
                result = new JSONObject(resultString);


                resultArray = result.getJSONArray("users");
                if(result.getInt("success") == 1) {
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject temp = resultArray.getJSONObject(i);
                        TYUser user = new TYUser(temp.getString("name"), R.mipmap.face1, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));
                        members.add(1, user);
                        //adapter.notifyDataSetChanged();
                    }
                }else if(result.getInt("success") == 0){
                    members.add(currentUser);
                    try {
                        resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/insertUserGroupID.php?username="+params[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String string){
            bar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    } */

}
