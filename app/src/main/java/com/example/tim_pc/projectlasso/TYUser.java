
package com.example.tim_pc.projectlasso;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TIM-PC on 2/14/2016.
 */

public class TYUser implements Parcelable{
    private String name;
    private String email;
    private String phoneNumber;
    private int imageID;
    private int itemViewType;


    //fg

    public TYUser(String name, int imageID, String email, String phoneNumber){
        super();
        this.name = name;
        this.imageID = imageID;
        this.itemViewType = 0;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public TYUser(String name){
        super();
        this.name = name;
        this.itemViewType = 1;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getName(){
        return name;
    }

    public int getImageID(){
        return imageID;
    }

    public int getItemViewType(){
        return itemViewType;
    }

    @Override
    public String toString(){
        return name;
    }

    public TYUser(Parcel in){
        String[] data= new String[5];

        in.readStringArray(data);
        this.name = data[0];
        this.imageID = Integer.parseInt(data[1]);
        this.itemViewType = Integer.parseInt(data[2]);
        this.email = data[3];
        this.phoneNumber = data[4];
    }
    @Override
    public int describeContents() {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
// TODO Auto-generated method stub

        dest.writeStringArray(new String[]{this.name,String.valueOf(this.imageID),String.valueOf(this.itemViewType),this.email,this.phoneNumber});
    }

    public static final Parcelable.Creator<TYUser> CREATOR= new Parcelable.Creator<TYUser>() {

        @Override
        public TYUser createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new TYUser(source);  //using parcelable constructor
        }

        @Override
        public TYUser[] newArray(int size) {
// TODO Auto-generated method stub
            return new TYUser[size];
        }
    };


}