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

import java.util.List;
import java.util.ArrayList;


/**
 * Created by danielbdeutsch on 2/21/16.
 */
public class FeedActivity extends Activity
{
    private List<FeedItem> feed = new ArrayList<FeedItem>();    //feed list
    private EditText mStatusView;   //status textbox


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        /* Populate feed with filler data */
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");
        addToFeed(R.mipmap.face, R.mipmap.beer_mug, "Danny", "Having fun");

        /* Set up ListAdapter */
        final ArrayAdapter<FeedItem> adapter = new MyListAdapter(feed);
        final ListView feedListView = (ListView) findViewById(R.id.feedListView);
        feedListView.setAdapter(adapter);

        /* "Post" button OnClickListener */
        Button mPostStatusButton = (Button) findViewById(R.id.post_status_button);
        mPostStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String status = mStatusView.getText().toString();   //get status String from textbox
                addToFeed(R.mipmap.face1, R.mipmap.drunk_emoji, "Tim", status);

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
        feed.add(new FeedItem(profPicId, statusPicId, name, status));
    }


    //TODO: Creates a dynamic adapter to handle both members and emergency contacts.
    //Parameter: The parameter is the list of FeedItems.
    private class MyListAdapter extends ArrayAdapter<FeedItem> {
        //Create copy of the list since there are two lists.
        List<FeedItem> localList;

        //TODO: Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<FeedItem> feed)
        {
            super(FeedActivity.this, R.layout.FeedItem_view, feed);
            localList = feed;
        }

        @Override
        public int getViewTypeCount()
        {
            return 2;
        }

        @Override
        public int getItemViewType(int position)
        {
            return getItem(position).getItemViewType();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //Get view even is view is null.
            View itemView = convertView;
            int rowType = getItemViewType(position);



            if (convertView == null) {
                itemView = getLayoutInflater().inflate(R.layout.FeedItem_view, parent, false);
            }
            else { //Make sure itemView is not null.
                itemView = convertView;
            }


            if (rowType == 0){
                //Iterate through the list of FeedItems
                FeedItem currentFeedItem = localList.get(position);

                //Setting the image of the User
                ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
                imageView.setImageResource(currentMember.getImageID());

                //Setting the name of the User
                TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
                nameText.setText(currentMember.getName());
            }
            else {
                //Iterate through the list of Users.
                User currentHeader = localList.get(position);
                //Grab text and add.
                TextView headerText = (TextView) itemView.findViewById(R.id.separator);
                headerText.setText(currentHeader.getName());
            }

            return itemView;
        }
    }
}
