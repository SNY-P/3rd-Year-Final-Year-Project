package com.example.fyp_926975;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.*;

public class DBConnection extends SQLiteOpenHelper {

    public DBConnection(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE user_table(ID INTEGER PRIMARY KEY AUTOINCREMENT, )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private static Connection connection = null;

    public static void open(String dbFile)
    {
        try
        {
            //Attempts to open database file located in repository
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:UniSkills2/app/src/main/java/com/example/uniskills/resources/" + dbFile);
        }
        catch (ClassNotFoundException exception)
        {
            //Throws error if class not found
            System.out.println("Class not found exception: " + exception.getMessage());
        }
        catch (SQLException exception)
        {
            //Throws error if database not found
            System.out.println("Database connection error: " + exception.getMessage());
        }
    }

    public static PreparedStatement newStatement(String query) throws SQLException{
        return connection.prepareStatement(query);
    }
}
