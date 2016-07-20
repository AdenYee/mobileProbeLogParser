package com.douwifi.yiks.tz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.douwifi.yiks.tz.common.Config;

public class TimerDatabaseHelper extends SQLiteOpenHelper
{

	public TimerDatabaseHelper(Context context, String name,
							   CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(Config.Create_TableName_Timer);
		db.execSQL(Config.Create_TableName_History);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{


	}

}
