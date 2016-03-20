package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


/**
 * Created by danielbdeutsch on 2/21/16.
 */
public class FeedActivity extends Activity
{
    private List<FeedItem> feed = new ArrayList<FeedItem>();    //feed list
    EditText mStatusView;   //status textbox


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        /* Populate feed with filler data */
        addToFeed(R.mipmap.face, R.mipmap.drunk_emoji, "Amanda", "Fun times");
        addToFeed(R.mipmap.face, R.mipmap.drunk_emoji, "Tim", "Schwasted");

        /* Set up ListAdapter */
        final ArrayAdapter<FeedItem> adapter = new MyListAdapter(feed);
        final ListView feedListView = (ListView) findViewById(R.id.feedListView);
        feedListView.setAdapter(adapter);

        /* "Post" button OnClickListener */
        Button mPostStatusButton = (Button) findViewById(R.id.post_status_button);

        mPostStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mStatusView = (EditText) findViewById(R.id.editText);
                String status = mStatusView.getText().toString();   //get status String from textbox

                addToFeed(R.mipmap.face, R.mipmap.drunk_emoji, "Amanda", status);

                adapter.notifyDataSetChanged();

                /* Scroll to bottom of list */
                feedListView.post(new Runnable() {
                    @Override
                    public void run() {
                        feedListView.setSelection(feedListView.getCount()-1);
                    }
                } );
            }
        } );

    }


    /* Generic method for adding FeedItem to feed */
    private void addToFeed(int profPicId, int statusPicId, String name, String status)
    {
        feed.add(new FeedItem(profPicId, statusPicId, name, status, this.getTimestamp()));
    }



    private String getTimestamp()
    {
        int time = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);

        return tsTemp.toString();
    }



    private class MyListAdapter extends ArrayAdapter<FeedItem>
    {
        //Create copy of the list since there are two lists.
        List<FeedItem> localList;

        //Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<FeedItem> feed)
        {
            //Use the List that is passed in from the parameter.
            super(FeedActivity.this, R.layout.feeditem_view, feed);
            localList = feed;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;

            if (convertView == null) {
                itemView = getLayoutInflater().inflate(R.layout.feeditem_view, parent, false);
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
}
