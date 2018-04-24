package com.example.user.sabkuchapp;

/**
 * Created by user on 4/16/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Account_database.db";
    public static final String TABLE_NAME = "Account_Table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "Amount";
    public static final String COL_4 = "Fruit_id";
    public static final String COL_5 = "Fruit_image";
    public static final String COL_6 = "Fruit_weight";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME  TEXT,Amount INTEGER,Fruit_id  INTEGER,Fruit_image TEXT,Fruit_weight TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,Integer surname,Integer Fruit_id,String Fruit_image,String Fruit_weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,Fruit_id);
        contentValues.put(COL_5,Fruit_image);
        contentValues.put(COL_6,Fruit_weight);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public void update( String name,Integer surname,Integer Fruit_id,String Fruit_image,String Fruit_weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,Fruit_id);
        contentValues.put(COL_5,Fruit_image);
        contentValues.put(COL_6,Fruit_weight);
       db.update(TABLE_NAME, contentValues,
              null, null);

    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }



    public Integer deleteData (String Fruit_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null,null);
    }
}