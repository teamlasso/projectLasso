package am.tabfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab3 extends Fragment {
    public List<TYUser> members = new ArrayList<TYUser>();
    private List<TYUser> emergencyContact = new ArrayList<TYUser>();
    ArrayAdapter<TYUser> adapter;
    TYUser currentUser;
    OkHttpClient httpclient = new OkHttpClient();
    private ProgressBar bar;
    private Boolean group;
    private TextView groupName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_contacts, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        group = false;
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        groupName = (TextView) view.findViewById(R.id.textView4);

        currentUser = new TYUser("test4", R.mipmap.face, "test4", "1234567", "test4", 1);
        members.add(new TYUser("Members"));
        new TYMySQLHandler().execute(currentUser.getUsername());
        adapter = new MyListAdapter(members);
        final ListView membersList= (ListView) view.findViewById(R.id.membersList);
        membersList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        membersList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if(!members.get(position).getName().equals("Members")) {
                    Intent test = new Intent(getActivity(), TYUserProfileActivity.class);
                    test.putExtra("user", members.get(position));
                    startActivity(test);
                }


            }
        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
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
                if(group) {
                    Intent addUser = new Intent(getActivity(), TYAddSearchUsers.class);
                    startActivityForResult(addUser, 0);
                }else{
                    Intent createGroup = new Intent(getActivity(), TYAddGroup.class);
                    createGroup.putExtra("user", currentUser);
                    startActivityForResult(createGroup, 1);
                }
            }
        });

        //Testing
        //ContactsListAdapter adapter = new ContactsListAdapter(this, members);
        //setListAdapter(adapter);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                TYUser user = data.getParcelableExtra("user");
                Boolean exists = false;
                for(TYUser listUser : members){
                    if(listUser.getUsername() != null){
                        if(listUser.getUsername().equals(user.getUsername())) {
                            exists = true;
                            break;
                        }
                    }
                }
                if(!exists) {
                    members.add(1, user);
                }else{
                    Toast.makeText(getActivity(), "User already in group", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();

            }
        }
        else if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                members = new ArrayList<TYUser>();
                members.add(new TYUser("Members"));
                new TYMySQLHandler().execute("test");

            }
        }
    }


    private class MyListAdapter extends ArrayAdapter<TYUser> {
        //Create copy of the list since there are two lists.
        List<TYUser> localList;

        //Set to dynamic. Users could be member or emergency.
        public MyListAdapter(List<TYUser> listOfUser){
            //Use the List that is passed in from the parameter.
            super(getActivity(), R.layout.item_view, listOfUser);
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
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.item_view, parent, false);
                }
                else{
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.item_header, parent, false);
                }
            }
            else{//Make sure itemView is not null.
                itemView = convertView;
            }

            if(rowType == 0){
                //Iterate through the list of Users(member or emergency).
                TYUser currentMember = localList.get(position);

                //Setting the image of the User
                ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
                imageView.setImageResource(currentMember.getImageID());

                //Setting the name of the User
                TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
                nameText.setText(currentMember.getName());
            }
            else{
                //Iterate through the list of Users.
                TYUser currentHeader = localList.get(position);
                //Grab text and add.
                TextView headerText = (TextView) itemView.findViewById(R.id.separator);
                headerText.setText(currentHeader.getName());
            }

            return itemView;
        }
    }

    class TYMySQLHandler extends AsyncTask<String, String , String>{

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
                resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/grabGroupMembers.php?username="+params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println(resultString);
                result = new JSONObject(resultString);


                resultArray = result.getJSONArray("users");
                if(result.getInt("success") == 1) {
                    group = true;

                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject temp = resultArray.getJSONObject(i);
                        TYUser user = new TYUser(temp.getString("name"), R.mipmap.face1, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));
                        members.add(1, user);
                        //adapter.notifyDataSetChanged();
                    }
                }else if(result.getInt("success") == 0){
                    members.add(currentUser);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String string){
            bar.setVisibility(View.GONE);
            try {
                groupName.setText(result.getString("groupname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }



    }


}
