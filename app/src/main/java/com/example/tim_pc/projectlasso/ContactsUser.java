package com.example.tim_pc.projectlasso;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tim_pc.projectlasso.ContactsListAdapter.RowType;

/**
 * Created by TIM-PC on 2/14/2016.
 */
public class ContactsUser implements Item{
    private String name;
    private int imageID;


    //fg
    public ContactsUser(String name, int imageID){
        super();
        this.name = name;
        this.imageID = imageID;
    }

    public String getName(){
        return name;
    }

    public int getImageID(){
        return imageID;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.item_view, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        //Setting the image of the User
        ImageView imageView = (ImageView)view.findViewById(R.id.item_icon);
        imageView.setImageResource(imageID);

        //Setting the name of the User
        TextView nameText = (TextView) view.findViewById(R.id.item_txtName);
        nameText.setText(name);

        return view;
    }
}
