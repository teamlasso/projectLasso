package com.example.tim_pc.projectlasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tim on 2/21/16.
 */
public class ContactsListAdapter extends ArrayAdapter<Item>{
    private LayoutInflater myInflater;
    //Create copy of the list since there are two lists.
    List<Item> localList;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public enum RowType{
        LIST_ITEM, HEADER_ITEM
    }
    //Set to dynamic. Users could be member or emergency.
    public ContactsListAdapter(Context context, List<Item> listOfUser){
        //Use the List that is passed in from the parameter.
        super(context, 0, listOfUser);
        localList = listOfUser;
    }

    @Override
    public int getViewTypeCount(){
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position){
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);
        View View;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = myInflater.inflate(R.layout.item_view, null);
                    holder.View=getItem(position).getView(myInflater, convertView);
                    break;
                case TYPE_SEPARATOR:
                    convertView = myInflater.inflate(R.layout.item_header, null);
                    holder.View=getItem(position).getView(myInflater, convertView);
                    break;
            }
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    public static class ViewHolder {
        public  View View;
    }
}
