package com.project.mycashbook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DataBaseAccess instance;
    Cursor c = null;

    private DataBaseAccess(Context context){
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DataBaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    public Cursor Get(String table){
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    public Cursor Sum(String field, String table, String where){
        return db.rawQuery("SELECT SUM(" + field +") AS result FROM " + table + " WHERE " + where, null);
    }

    public Cursor SumGroup(String field, String table){
        return db.rawQuery("SELECT SUM(" + field +") AS result, createddate FROM " + table + " GROUP BY createddate", null);
    }

    public Cursor Where(String table, String where){
        return db.rawQuery("SELECT * FROM " + table + " WHERE " + where, null);
    }

    public boolean insertUser(String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("user", null, contentValues);
        return result != -1;
    }

    public boolean updateUser(String password, String username){
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = db.update("user",  contentValues, "username=?", new String[]{username});
        return result != -1;
    }

    public boolean insertMoney(Integer jumlah, String keterangan, String tanggal, String flow){
        ContentValues contentValues = new ContentValues();
        contentValues.put("jumlah", jumlah);
        contentValues.put("keterangan", keterangan);
        contentValues.put("createddate", tanggal);
        contentValues.put("flow", flow);
        long result = db.insert("money", null, contentValues);
        return result != -1;
    }
}
