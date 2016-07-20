package com.douwifi.yiks.tz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.douwifi.yiks.tz.common.Config;
import com.douwifi.yiks.tz.model.History;
import com.douwifi.yiks.tz.model.Result;
import com.douwifi.yiks.tz.util.SerializerClass;

import java.util.ArrayList;

/**
 * Created by YiKS on 2016/7/11.
 */
public class HistoryDatabaseHelper extends SQLiteOpenHelper{

    public HistoryDatabaseHelper(Context context){
        super(context, Config.DatabaseName, null, 1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(Config.Create_TableName_Timer);
        db.execSQL(Config.Create_TableName_History);
    }


    public void insert(byte[] result, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("result", result);       //CHUNK blob type field of you
            cv.put("title", title);
            long rawId = db.insert(Config.TableName_History, null, cv); //TABLE table name
//            db.execSQL("insert into " + TableName + " values(" + result + "," + time + ")");
            Log.d("iiiiiiiiiiiiii", ""+rawId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        db.close();
    }


    public ArrayList<History> getAll(){
        ArrayList<History> histories = new ArrayList<History>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cur = db.rawQuery("select * from " + Config.TableName_History, null);
            if (cur.moveToFirst() != false){
                do{
                    int id = cur.getInt(0);
                    byte[] bytes = cur.getBlob(1);
                    String title = cur.getString(2);
                    Result result = (Result) SerializerClass.deserializeObject(bytes);
                    History history = new History(id, result, title);
                    histories.add(history);
                } while (cur.moveToNext());
                cur.moveToLast();
            }
            cur.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
        return histories;
    }
    private void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Config.TableName_History, null, null);
        db.close();
    }

}
