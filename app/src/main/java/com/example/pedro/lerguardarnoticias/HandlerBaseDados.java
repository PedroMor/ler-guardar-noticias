package com.example.pedro.lerguardarnoticias; /** Pedro Moreira A030425 **/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HandlerBaseDados extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "noticiasbd.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTICIAS = "noticiasbd";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITULOS = "titulos";
    public static final String COLUMN_CONTEUDO = "conteudo";

    public HandlerBaseDados(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOTICIAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULOS + " TEXT, " +
                COLUMN_CONTEUDO + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICIAS);
        onCreate(db);
    }

    public void insertNoticia(String titulo, String conteudo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULOS, titulo);
        values.put(COLUMN_CONTEUDO, conteudo);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTICIAS, null, values);
        db.close();
    }

    public void clearBD() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NOTICIAS);
    }

    public Cursor getCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NOTICIAS, null);
        return data;
    }
}