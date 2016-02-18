package com.example.tim_pc.projectlasso;

/**
 * Created by TIM-PC on 2/14/2016.
 */
public class User {
    private String name;
    private int imageID;


    //fg
    public User(String name, int imageID){
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
}
