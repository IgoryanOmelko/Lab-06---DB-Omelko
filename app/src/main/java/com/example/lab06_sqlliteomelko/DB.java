package com.example.lab06_sqlliteomelko;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper{
    String Errormes = "(!) not found";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE my_test (my_key TEXT PRIMARY KEY, my_value TEXT);";//create new table with 2 columns
        db.execSQL(sql);//run query
    }
    public Boolean DoInsert(String key, String value){
        if (!(value.equals(R.string.Errormes))){
            if (DoSelect(key).equals(value)){//Check of existing the same records
                return false;
            }
            else{
                String sql = "INSERT INTO my_test VALUES('"+key+"','"+value+"');";//inner new row into table
                SQLiteDatabase db = getWritableDatabase(); //get ready to write into database
                try{
                    db.execSQL(sql);//run query
                }
                catch (SQLiteConstraintException ex) {return false;}
                return true;
            }
        }
        else{
            return false;
        }
    }
    public Boolean DoDelete(String key){
        if (DoSelect(key).equals(Errormes)){//Check of existing the same records
            return false;
        }
        else{
            String sql = "DELETE FROM my_test WHERE my_key = '"+key+"';";//Delete row from table
            SQLiteDatabase db = getReadableDatabase(); //get ready to write into database
            db.execSQL(sql);//run query
            return true;
        }
    }
    public Boolean DoUpdate(String key, String value){
            String sql = "UPDATE my_test SET my_value = '"+value+"' WHERE my_key = '"+key+"';";//Delete row from table
            SQLiteDatabase db = getReadableDatabase(); //get ready to write into database
            if (DoSelect(key).equals(value)){//Check of existing the same records
                return false;
            }
            try{
                db.execSQL(sql);//run query
            }
           catch(Exception ex){
               return false;
            }
            return true;
    }
    public String DoSelect(String key){
        String sql = "SELECT my_value FROM my_test WHERE my_key = '"+key+"';";//find value inside table by given key
        SQLiteDatabase db = getReadableDatabase(); //get ready to read into database
        Cursor cur = db.rawQuery(sql,null);//run query and acquire result in a new "table" with only one column
        if (cur.moveToFirst()==true)//jump to the first (and the only one) matching record, if possible
            return cur.getString(0);// return value from the first column (my_value)
        return Errormes;//return special text when no results
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
