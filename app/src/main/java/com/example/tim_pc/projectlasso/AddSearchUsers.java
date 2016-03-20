package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

public class AddSearchUsers extends Activity {

    AutoCompleteTextView auto;
    ArrayAdapter<String> adapter;
    DBHandler db;
    String[] item = new String[] {"Search User"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search_users);

        try{
            db = new DBHandler(AddSearchUsers.this);
            insertSampleData();
            auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            auto.addTextChangedListener(new MyAutoCompleteListener(AddSearchUsers.this));
            adapter = new ArrayAdapter<String>(AddSearchUsers.this, android.R.layout.simple_dropdown_item_1line, item);
            auto.setAdapter(adapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSampleData(){
        db.create( new User("Tim Yim", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("1", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("2", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("3", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("4", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("5", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("6", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("7", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("8", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("9", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("10", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("Jim Bob", R.mipmap.face1, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("John Doe", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));

    }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<User> products = db.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (User record : products) {

            item[x] = record.getName();
            x++;
        }

        return item;
    }
}
