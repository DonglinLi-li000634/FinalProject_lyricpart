package com.example.finalproject_lyricpart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpener extends SQLiteOpenHelper {
    protected final static String DB_NAME="LyricDB";
    protected final static int DB_Version=2;
    public final static String TABLE_NAME="LyricTable";
    public final static String COL_LYRIC="LYRIC";
    public final static String COL_ID="_ID";

    public MyDBOpener(Context ctx){
        super(ctx,DB_NAME,null,DB_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_LYRIC+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("DB Upgrade ","Old: "+i+" ,New: "+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        Log.i("DB Downgrade ","Old: "+i+" ,New: "+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
