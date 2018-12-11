package com.database.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Employee.db";
    public static final String TABLE_NAME = "Employee_details";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "ADDRESS";
    public static final String COL_4 = "AGE";
    public static final String COL_5 = "TYPE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, ADDRESS TEXT,AGE INTEGER,TYPE TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("Drop table if exists "+TABLE_NAME);
       onCreate(db);
    }

    public boolean insertData(String name, String address, String age, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean wasSuccess = true;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(COL_2, name);
            values.put(COL_3, address);
            values.put(COL_4, age);
            values.put(COL_5, type);

            long success = db.insert(TABLE_NAME, null, values);
            if (success == -1) {
                wasSuccess = false;
            }
            else {
                db.setTransactionSuccessful();
            }
        }finally {
                db.endTransaction();
                db.close();
            }
        return wasSuccess;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from "+TABLE_NAME,null);
        return cur;
    }

    public boolean updateData(String id, String name, String address, String age, String type){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, id);
        values.put(COL_2, name);
        values.put(COL_3, address);
        values.put(COL_4, age);
        values.put(COL_5, type);

        db.update(TABLE_NAME, values, "ID = ?", new String[]{id});
        return true;

    }

    public Integer deleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

}
