package com.example.fyp_926975;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "FYP_926975.db", null, 1);
    }
    // create database
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(userid INTEGER primary key AUTOINCREMENT," +
                                        "username TEXT," +
                                        "password TEXT)");
        myDB.execSQL("create Table cartesian(cartid INTEGER primary key AUTOINCREMENT," +
                                            "list1 TEXT," +
                                            "list2 TEXT," +
                                            "product TEXT," +
                                            "useranswer TEXT," +
                                            "answercheck TEXT," +
                                            "userID INTEGER," +
                                            "foreign key (userID) REFERENCES user(userid))");
        myDB.execSQL("create Table symmetric(symmid INTEGER primary key AUTOINCREMENT," +
                                            "setlist TEXT," +
                                            "relation TEXT," +
                                            "useranswer TEXT," +
                                            "answercheck TEXT," +
                                            "userID INTEGER," +
                                            "foreign key (userID) REFERENCES user(userid))");
        myDB.execSQL("create Table reflexive(refid INTEGER primary key AUTOINCREMENT," +
                                            "setlist TEXT," +
                                            "relation TEXT," +
                                            "useranswer TEXT," +
                                            "answercheck TEXT," +
                                            "userID INTEGER," +
                                            "foreign key (userID) REFERENCES user(userid))");
        myDB.execSQL("create Table transitive(tranid INTEGER primary key AUTOINCREMENT," +
                                            "setlist TEXT," +
                                            "relation TEXT," +
                                            "useranswer TEXT," +
                                            "answercheck TEXT," +
                                            "userID INTEGER," +
                                            "foreign key (userID) REFERENCES user(userid))");
    }
    // drop database if exists
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
        myDB.execSQL("drop Table if exists cartesian");
        myDB.execSQL("drop Table if exists symmetric");
        myDB.execSQL("drop Table if exists reflexive");
        myDB.execSQL("drop Table if exists transitive");
    }
    // check data
    public Boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkPassword(String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where password = ?", new String[] {password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkCartQuestion(Integer id, Integer uID) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from cartesian where cartid = ? and userID = " + uID, new String[] {id.toString()});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkTranQuestion(Integer id, Integer uID) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from transitive where tranid = ? and userID = " + uID, new String[] {id.toString()});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkSymQuestion(Integer id, Integer uID) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from symmetric where symmid = ? and userID = " + uID, new String[] {id.toString()});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    // insert data
    public Boolean insertUserData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues userList = new ContentValues();
        userList.put("username", username);
        userList.put("password", password);
        myDB.insert("users", null, userList);
        return true;
    }

    public Boolean insertCartesianData(String list1, String list2, String product, String userAnswer, String answerCheck, Integer id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("list1", list1);
        contents.put("list2", list2);
        contents.put("product", product);
        contents.put("useranswer", userAnswer);
        contents.put("answercheck", answerCheck);
        contents.put("userID", id);
        myDB.insert("cartesian", null, contents);
        return true;
    }

    public Boolean insertSymmetricData(String list, String relation, String userAnswer, String answerCheck, Integer id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("setlist", list);
        contents.put("relation", relation);
        contents.put("useranswer", userAnswer);
        contents.put("answercheck", answerCheck);
        contents.put("userID", id);
        myDB.insert("symmetric", null, contents);
        return true;
    }

    public Boolean insertReflexiveData(String list, String relation, String userAnswer, String answerCheck, Integer id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("setlist", list);
        contents.put("relation", relation);
        contents.put("useranswer", userAnswer);
        contents.put("answercheck", answerCheck);
        contents.put("userID", id);
        myDB.insert("reflexive", null, contents);
        return true;
    }

    public Boolean insertTransitiveData(String list, String relation, String userAnswer, String answerCheck, Integer id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("setlist", list);
        contents.put("relation", relation);
        contents.put("useranswer", userAnswer);
        contents.put("answercheck", answerCheck);
        contents.put("userID", id);
        myDB.insert("transitive", null, contents);
        return true;
    }
    // get data
    public Cursor getCartesiandata(Integer input) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from cartesian where userid = " + input, null);
        return cursor;
    }

    public Cursor getUserid(String text) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where username = ?", new String[] {text});
        return cursor;
    }

    public Cursor getReflexivedata(Integer input) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from reflexive where userid = " + input, null);
        return cursor;
    }

    public Cursor getSymmetricData(Integer input) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from symmetric where userid = " + input, null);
        return cursor;
    }

    public Cursor getTransitiveData(Integer input) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from transitive where userid = " + input, null);
        return cursor;
    }

    public Cursor getSpecificCartdata(Integer input, Integer user) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from cartesian where cartid = " + input + " and userid = " + user, null);
        return cursor;
    }

    public Cursor getSpecificTransdata(Integer input, Integer user) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from transitive where tranid = " + input + " and userid = " + user, null);
        return cursor;
    }

    public Cursor getSpecificSymdata(Integer input, Integer user) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from symmetric where symmid = " + input + " and userid = " + user, null);
        return cursor;
    }
}