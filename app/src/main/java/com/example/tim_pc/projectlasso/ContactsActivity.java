package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {
    private List<User> members = new ArrayList<User>();
    private List<User> emergencyContact = new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addToMembers();
        addToEmergency();
        populateMembersList();
        populateEmergencyList();
        //Testing



    }

    //Generic method for adding one user to list.
    private void addToMembers(String name, int imageID){
        members.add(new User(name, imageID));
    }

    //Method for adding bulk to members list
    private void addToMembers(){
        members.add(new User("Tim Yim", R.mipmap.face));
    }

    //Generic method for adding one user to the list.
    private void addToEmergency(String name, int imageID){
        emergencyContact.add(new User(name, imageID));
    }

    //Method for add to the emergency list.
    private void addToEmergency(){
        emergencyContact.add(new User("Jim Bob", R.mipmap.face1));
        emergencyContact.add(new User("John Doe", R.mipmap.face));
    }

    //Populates the Emergency List on the app itself.
    private void populateEmergencyList(){
        ArrayAdapter<User> adapter = new MyListAdapter(emergencyContact);
        ListView membersList = (ListView) findViewById(R.id.emergencyList);
        membersList.setAdapter(adapter);

    }

    //Populates the Members List on the app itself.
    private void populateMembersList(){
        //Create a new adapter for populating the list.
        ArrayAdapter<User> adapter = new MyListAdapter(members);
        ListView membersList = (ListView) findViewById(R.id.membersList);
        membersList.setAdapter(adapter);

    }

    //Creates a dynamic adapter to handle both members and emergency contacts.
    //Parameter: The parameter is the list of users.
    private class MyListAdapter extends ArrayAdapter<User> {
        //Create copy of the list since there are two lists.
        List<User> localList;

        //Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<User> listOfUser){
            //Use the List that is passed in from the parameter.
            super(ContactsActivity.this, R.layout.item_view, listOfUser);
            localList = listOfUser;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //Get view even is view is null.
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Iterate through the list of Users(member or emergency).
            User currentMember = localList.get(position);

            //Setting the image of the User
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentMember.getImageID());

            //Setting the name of the User
            TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
            nameText.setText(currentMember.getName());

            return itemView;
        }
    }
}
