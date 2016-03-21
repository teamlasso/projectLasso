package com.example.tim_pc.projectlasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class TYAddSearchUsers extends Activity {

    AutoCompleteTextView auto;
    ArrayAdapter<String> adapter;
    TYDBHandler db;
    String[] item = new String[] {"Search User"};
    List<TYUser> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search_users);

        try{
            db = new TYDBHandler(TYAddSearchUsers.this);
            insertSampleData();
            auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            auto.addTextChangedListener(new TYMyAutoCompleteListener(TYAddSearchUsers.this));
            adapter = new ArrayAdapter<String>(TYAddSearchUsers.this, android.R.layout.simple_dropdown_item_1line, item);
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
                if(products != null) {
                    Intent intent = new Intent();
                    intent.putExtra("user", products.get(0));
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getBaseContext(), "Please search for user", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void insertSampleData(){
        db.create( new TYUser("Tim Yim", R.mipmap.face, "airyimbin@gmail.com", "1234567890"));
        db.create( new TYUser("Portia Randol", R.mipmap.face, "portiarandol@gmail.com", "1234567890"));
        db.create( new TYUser("Kenton Shumway", R.mipmap.face, "kentonshumway@gmail.com", "1234567890"));
        db.create( new TYUser("Elwood Yanni", R.mipmap.face, "elwoodyanni@gmail.com", "1234567890"));
        db.create( new TYUser("Dell Ambriz", R.mipmap.face, "dellambriz@gmail.com", "1234567890"));
        db.create( new TYUser("Alda James", R.mipmap.face, "aldajames@gmail.com", "1234567890"));
        db.create( new TYUser("Lucius Bradway", R.mipmap.face, "luciusbradway@gmail.com", "1234567890"));
        db.create( new TYUser("Esther Parman", R.mipmap.face, "estherparman@gmail.com", "1234567890"));
        db.create( new TYUser("Jarrod Simonton", R.mipmap.face, "jarrod@gmail.com", "1234567890"));
        db.create( new TYUser("Jim Tomer", R.mipmap.face, "jimtomer@gmail.com", "1234567890"));
        db.create( new TYUser("Sophia Grinder", R.mipmap.face, "sophiagrinder@gmail.com", "1234567890"));
        db.create( new TYUser("Jim Bob", R.mipmap.face1, "jimbob@gmail.com", "1234567890"));
        db.create( new TYUser("Jim Peters", R.mipmap.face1, "jimpeters@gmail.com", "1234567890"));
        db.create( new TYUser("Jimmy Olson", R.mipmap.face1, "jimmyolson@gmail.com", "1234567890"));
        db.create( new TYUser("John Doe", R.mipmap.face, "johndoe@gmail.com", "1234567890"));


    }


    public String[] getItemsFromDb(String searchTerm){
        products = db.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (TYUser record : products) {

            item[x] = record.getName();
            x++;
        }
        Arrays.sort(item);
        return item;
    }
}
