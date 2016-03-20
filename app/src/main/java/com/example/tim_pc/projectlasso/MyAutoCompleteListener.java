package com.example.tim_pc.projectlasso;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by tim on 3/20/16.
 */
public class MyAutoCompleteListener implements TextWatcher {


    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public MyAutoCompleteListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {


        AddSearchUsers mainActivity = ((AddSearchUsers) context);
        mainActivity.item = mainActivity.getItemsFromDb(userInput.toString());
        mainActivity.adapter.notifyDataSetChanged();
        mainActivity.adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, mainActivity.item);
        mainActivity.auto.setAdapter(mainActivity.adapter);

    }
}
