package com.example.tim_pc.projectlasso;

/**
 * Created by danielbdeutsch on 2/21/16.
 */
public class FeedItem
{
    private int profPicId;
    private int statusPicId;
    private String name;
    private String status;

    public FeedItem(int ProfPicId, int statusPicId, String name, String status) {
        super();
        this.profPicId = profPicId;
        this.statusPicId = statusPicId;
        this.name = name;
        this.status = status;
    }

    public int getProfPicId() {
        return profPicId;
    }

    public int getStatusPicId() {
        return statusPicId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
