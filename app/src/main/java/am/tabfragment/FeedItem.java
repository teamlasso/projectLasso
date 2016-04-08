package am.tabfragment;

/**
 * Created by will on 3/27/16.
 */

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
    public FeedItem(int profPicId, int statusPicId, String name, String status, String timestamp)
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

