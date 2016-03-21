package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class AddSearchUsers extends Activity {

    AutoCompleteTextView auto;
    ArrayAdapter<String> adapter;
    DBHandler db;
    String[] item = new String[] {"Search User"};
    List<User> products;

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

        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("user", products.get(0));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void insertSampleData(){
        db.create( new User("Tim Yim", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new User("Portia Randol", R.mipmap.face, "portiarandol@gmail.com", "1234567890"));
        db.create( new User("Kenton Shumway", R.mipmap.face, "kentonshumway@gmail.com", "1234567890"));
        db.create( new User("Elwood Yanni", R.mipmap.face, "elwoodyanni@gmail.com", "1234567890"));
        db.create( new User("Dell Ambriz", R.mipmap.face, "dellambriz@gmail.com", "1234567890"));
        db.create( new User("Alda James", R.mipmap.face, "aldajames@gmail.com", "1234567890"));
        db.create( new User("Lucius Bradway", R.mipmap.face, "luciusbradway@gmail.com", "1234567890"));
        db.create( new User("Esther Parman", R.mipmap.face, "estherparman@gmail.com", "1234567890"));
        db.create( new User("Jarrod Simonton", R.mipmap.face, "jarrod@gmail.com", "1234567890"));
        db.create( new User("Jim Tomer", R.mipmap.face, "jimtomer@gmail.com", "1234567890"));
        db.create( new User("Sophia Grinder", R.mipmap.face, "sophiagrinder@gmail.com", "1234567890"));
        db.create( new User("Jim Bob", R.mipmap.face1, "jimbob@gmail.com", "1234567890"));
        db.create( new User("Jim Peters", R.mipmap.face1, "jimpeters@gmail.com", "1234567890"));
        db.create( new User("Jimmy Olson", R.mipmap.face1, "jimmyolson@gmail.com", "1234567890"));
        db.create( new User("John Doe", R.mipmap.face, "johndoe@gmail.com", "1234567890"));

    }


    public String[] getItemsFromDb(String searchTerm){
        products = db.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (User record : products) {

            item[x] = record.getName();
            x++;
        }
        Arrays.sort(item);
        return item;
    }
}
