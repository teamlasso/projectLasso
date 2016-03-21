package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {
    public List<User> members = new ArrayList<User>();
    private List<User> emergencyContact = new ArrayList<User>();
    ArrayAdapter<User> adapter;
    //public static ParseUser


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        members.add(new User("Members"));
        members.add(new User("Tim Yim", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        members.add(new User("Portia Randol", R.mipmap.face, "portiarandol@gmail.com", "1234567890"));
        members.add(new User("Kenton Shumway", R.mipmap.face, "kentonshumway@gmail.com", "1234567890"));
        members.add(new User("Elwood Yanni", R.mipmap.face, "elwoodyanni@gmail.com", "1234567890"));
        members.add(new User("Dell Ambriz", R.mipmap.face, "dellambriz@gmail.com", "1234567890"));
        members.add(new User("Alda James", R.mipmap.face, "aldajames@gmail.com", "1234567890"));
        members.add(new User("Lucius Bradway", R.mipmap.face, "luciusbradway@gmail.com", "1234567890"));
        members.add(new User("Esther Parman", R.mipmap.face, "estherparman@gmail.com", "1234567890"));
        members.add(new User("Emergency Contacts"));
        members.add(new User("Jim Bob", R.mipmap.face1, "jimbob@gmail.com", "1234567890"));
        members.add(new User("John Doe", R.mipmap.face, "johndoe@gmail.com", "1234567890"));
        adapter = new MyListAdapter(members);
        final ListView membersList = (ListView) findViewById(R.id.membersList);
        membersList.setAdapter(adapter);
        membersList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

//                String item = members.get(position).toString();
//
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                Intent test = new Intent(ContactsActivity.this, UserProfileActivity.class);
                test.putExtra("user", members.get(position));
                startActivity(test);


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                members.add(new User("A B", R.mipmap.face1, "airyimbin@gmail.com", "1234567890"));
//                adapter.notifyDataSetChanged();
//                membersList.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        membersList.setSelection(membersList.getCount()-1);
//                    }
//                });
                Intent addUser = new Intent(ContactsActivity.this, AddSearchUsers.class);
                startActivityForResult(addUser, 0);
            }
        });

        //Testing
        //ContactsListAdapter adapter = new ContactsListAdapter(this, members);
        //setListAdapter(adapter);



    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                User user = data.getParcelableExtra("user");
                members.add(1, user);
                adapter.notifyDataSetChanged();

            }
        }
    }
    //Generic method for adding one user to list.
    private void addToMembers(String name, int imageID, String email, String phoneNumber){
        members.add(new User(name, imageID, email, phoneNumber));
    }

    //Method for adding bulk to members list
    private void addToMembers(){
        members.add(new User("Members"));
        members.add(new User("Tim Yim", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
    }

    //Generic method for adding one user to the list.
    private void addToEmergency(String name, int imageID, String email, String phoneNumber){
        emergencyContact.add(new User(name, imageID, email, phoneNumber));
    }

    //Method for add to the emergency list.
    private void addToEmergency(){
        emergencyContact.add(new User("Emergency Contacts"));
        emergencyContact.add(new User("Jim Bob", R.mipmap.face1, "airyimbin@gmail.com", "1234567890"));
        emergencyContact.add(new User("John Doe", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
    }

    //Populates the Emergency List on the app itself.
    private void populateEmergencyList(){
        ArrayAdapter<User> adapter = new MyListAdapter(emergencyContact);
        ListView membersList = (ListView) findViewById(R.id.membersList);
        membersList.setAdapter(adapter);

    }

    //Populates the Members List on the app itself.
    private void populateMembersList(){
        //Create a new adapter for populating the list.
        ArrayAdapter<User> adapter = new MyListAdapter(members);
        ListView membersList = (ListView) findViewById(R.id.membersList);
        membersList.setAdapter(adapter);

    }

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
        public int getViewTypeCount(){

            return 2;
        }

        @Override
        public int getItemViewType(int position){

            return getItem(position).getItemViewType();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //Get view even is view is null.
            View itemView = convertView;
            int rowType = getItemViewType(position);



            if(convertView == null){
                if(rowType == 0){//Dynamically change amount to inflate depending on what the item type is.
                    itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
                }
                else{
                    itemView = getLayoutInflater().inflate(R.layout.item_header, parent, false);
                }
            }
            else{//Make sure itemView is not null.
                itemView = convertView;
            }

            if(rowType == 0){
                //Iterate through the list of Users(member or emergency).
                User currentMember = localList.get(position);

                //Setting the image of the User
                ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
                imageView.setImageResource(currentMember.getImageID());

                //Setting the name of the User
                TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
                nameText.setText(currentMember.getName());
            }
            else{
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
