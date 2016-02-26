package com.example.tim_pc.projectlasso;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tim on 2/21/16.
 */
public class ContactsHeader implements Item{
    private String headerName;

    public ContactsHeader(String name){
        headerName = name;
    }

    @Override
    public int getViewType(){
        return ContactsListAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.item_header, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(headerName);

        return view;
    }


}
