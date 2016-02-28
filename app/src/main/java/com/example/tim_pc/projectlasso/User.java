
package com.example.tim_pc.projectlasso;

import java.io.Serializable;

/**
 * Created by TIM-PC on 2/14/2016.
 */
public class User implements Serializable{
    private String name;
    private int imageID;
    private int itemViewType;


    //fg
    public User(String name, int imageID){
        super();
        this.name = name;
        this.imageID = imageID;
        this.itemViewType = 0;
    }

    public User(String name){
        super();
        this.name = name;
        this.itemViewType = 1;
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
}