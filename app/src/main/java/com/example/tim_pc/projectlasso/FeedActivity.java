package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by danielbdeutsch on 2/21/16.
 */
public class FeedActivity extends Activity
{
    private List<FeedItem> feed = new ArrayList<FeedItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        addToFeed();
        populateFeed();
    }


    /* Generic method for adding FeedItem to feed */
    private void addToFeed(int profPicId, int statusPicId, String name, String status) {
        feed.add(new FeedItem(profPicId, statusPicId, name, status));
    }

    /* Method for adding bulk to members list */
    private void addToFeed(){
        feed.add(new FeedItem(R.mipmap.face, R.mipmap.face1, "Danny Deutsch", "Inebriated"));
        feed.add(new FeedItem(R.mipmap.face, R.mipmap.face1, "Tim Yim", "Hammered"));
    }

    /* Populates feed on the app itself */
    private void populateFeed(){
        ArrayAdapter<FeedItem> adapter = new MyListAdapter(feed);
        ListView feed = (ListView) findViewById(R.id.emergencyList);
        feed.setAdapter(adapter);
    }

    //Creates a dynamic adapter to handle both members and emergency contacts.
    //Parameter: The parameter is the list of users.
    private class MyListAdapter extends ArrayAdapter<FeedItem>
    {
        //Create copy of the list since there are two lists.
        List<FeedItem> localList;

        //Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<FeedItem> feed){
            //Use the List that is passed in from the parameter.
            super(FeedActivity.this, R.layout.activity_feed, feed);
            localList = feed;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            //Get view even is view is null.
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_feed, parent, false);
            }

            //Iterate through the list of FeedItem's
            FeedItem currentMember = localList.get(position);

            /* Sets profile pic in FeedItem */
            ImageView profPic = (ImageView)itemView.findViewById(R.id.item_icon);
            profPic.setImageResource(currentMember.getProfPicId());

            /* Sets status pic in FeedItem */
            ImageView statusPic = (ImageView)itemView.findViewById(R.id.item_icon);
            statusPic.setImageResource(currentMember.getStatusPicId());

            /* Sets name in FeedItem */
            TextView name = (TextView) itemView.findViewById(R.id.item_txtName);
            name.setText(currentMember.getName());

            /* Sets status in FeedItem */
            TextView status = (TextView) itemView.findViewById(R.id.item_txtName);
            status.setText(currentMember.getName());

            return itemView;
        }
    }
}
