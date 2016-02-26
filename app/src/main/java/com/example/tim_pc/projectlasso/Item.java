package com.example.tim_pc.projectlasso;


import android.view.View;
import android.view.LayoutInflater;
/**
 * Created by tim on 2/21/16.
 */
public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
