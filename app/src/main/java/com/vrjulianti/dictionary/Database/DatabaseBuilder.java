package com.vrjulianti.dictionary.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseBuilder extends SQLiteOpenHelper {
    private static String DB_NAME = "kamus.db";

    public static String ENGLISH_TABLE ="English";
    public static String INDO_TABLE = "Indonesia";
    public static String ID_COLUMN = "Id";
    public static String WORD_COLUMN ="Word";
    public static String TRANSLATE_COLUMN = "Translate";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_ENGLISH_TABLE = "create table " + ENGLISH_TABLE + " (" +
            ID_COLUMN + " integer primary key autoincrement, " +
            WORD_COLUMN + " text not null, " +
            TRANSLATE_COLUMN + " text not null);";

    private static String CREATE_INDONESIA_TABLE = "create table " + INDO_TABLE + " (" +
            ID_COLUMN + " integer primary key autoincrement, " +
            WORD_COLUMN  + " text not null, " +
            TRANSLATE_COLUMN + " text not null);";


    DatabaseBuilder(Context context)
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_ENGLISH_TABLE);
        db.execSQL(CREATE_INDONESIA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ENGLISH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INDO_TABLE);
        onCreate(db);
    }
}
