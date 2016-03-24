package com.example.tim_pc.projectlasso;


import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by tim on 3/20/16.
 */
public class TYDBHandler extends SQLiteOpenHelper{


    public static final String TAG = "DBHandler.java";
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "User2Database";
    public String tableName = "USERS";
    public String fieldId = "ID";
    public String fieldImageId = "IMAGEID";
    public String fieldName = "NAME";
    public String fieldEmail = "EMAIL";
    public String fieldPhoneNumber = "PHONENUMBER";

    public TYDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE USERS " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME      TEXT    NOT NULL, " +
                " IMAGEID   INT     NOT NULL, " +
                " EMAIL     TEXT    NOT NULL, " +
                " PHONENUMBER TEXT   NOT NULL)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean create(TYUser user){

        boolean success = false;

        if(!checkIfExists(user.getName())){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(fieldName, user.getName());
            values.put(fieldImageId, user.getImageID());
            values.put(fieldEmail, user.getEmail());
            values.put(fieldPhoneNumber, user.getPhoneNumber());
            success = db.insert(tableName, null, values) > 0;

            db.close();

            if(success){
                Log.e(TAG, user.getName() + " created.");
            }
        }

        return success;
    }

    private boolean checkIfExists(String name) {
        boolean exists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldId + " FROM " + tableName + " WHERE " + fieldName + " = '" + name + "'", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                exists = true;
            }
        }

        cursor.close();
        db.close();

        return exists;
    }

    public List<TYUser> read(String searchTerm) {

        List<TYUser> recordsList = new ArrayList<TYUser>();

        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldId + " DESC";
        sql += " LIMIT 10";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String name = cursor.getString(cursor.getColumnIndex(fieldName));
                int imageID = cursor.getInt(cursor.getColumnIndex(fieldImageId));
                String email = cursor.getString(cursor.getColumnIndex(fieldEmail));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(fieldPhoneNumber));
//                TYUser user = new TYUser(name, imageID, email, phoneNumber,);
//                recordsList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
}
