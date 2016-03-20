package com.example.tim_pc.projectlasso;

import java.sql.Timestamp;

public class FeedItem
{
    private int profPicId;
    private int statusPicId;
    private String name;
    private String status;
    private String timestamp;

    /***************
       CONSTRUCTOR
     ***************/
    public FeedItem(int ProfPicId, int statusPicId, String name, String status, String timestamp)
    {
        super();
        this.profPicId = profPicId;
        this.statusPicId = statusPicId;
        this.name = name;
        this.status = status;
        this.timestamp = timestamp;
    }


    /************
       GETTERS
     ************/
    public int getProfPicId()    { return profPicId; }
    public int getStatusPicId()  { return statusPicId; }
    public String getName()      { return name; }
    public String getStatus()    { return status; }
    public String getTimestamp() { return timestamp; }
}
