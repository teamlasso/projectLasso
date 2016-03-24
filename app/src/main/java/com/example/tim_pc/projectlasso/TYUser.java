
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
    private String username;
    private int groupID;



    //fg

    public TYUser(String name, int imageID, String email, String phoneNumber, String username, int groupID){
        super();
        this.name = name;
        this.imageID = imageID;
        this.itemViewType = 0;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.groupID = groupID;
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

    public String getUsername(){ return username; }

    public int getGroupID() { return groupID; }

    @Override
    public String toString(){
        return name;
    }

    public TYUser(Parcel in){
        String[] data= new String[7];

        in.readStringArray(data);
        this.name = data[0];
        this.imageID = Integer.parseInt(data[1]);
        this.itemViewType = Integer.parseInt(data[2]);
        this.email = data[3];
        this.phoneNumber = data[4];
        this.username = data[5];
        this.groupID = Integer.parseInt(data[6]);

    }
    @Override
    public int describeContents() {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
// TODO Auto-generated method stub

        dest.writeStringArray(new String[]{this.name,String.valueOf(this.imageID),String.valueOf(this.itemViewType),this.email,this.phoneNumber,this.username,String.valueOf(this.groupID)});
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