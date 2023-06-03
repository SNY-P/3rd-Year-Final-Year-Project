package com.example.fyp_926975;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import java.util.ArrayList;

public class reflexiveResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList questionID, set, relation, userAnswer, ifCorrect;
    DBHelper DB;
    reflexiveAdapter reflexiveAdapter;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflexive_results);

        DB = new DBHelper(this);

        this.questionID = new ArrayList<>();
        set = new ArrayList<>();
        relation = new ArrayList<>();
        userAnswer = new ArrayList<>();
        ifCorrect = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerviewRef);
        reflexiveAdapter = new reflexiveAdapter(this, this.questionID, set, relation, userAnswer, ifCorrect);
        recyclerView.setAdapter(reflexiveAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = getIntent().getIntExtra(loginActivity.USERID,0);
        displaydata(userID);
    }

    private void displaydata(Integer input) {
        Cursor cursor = DB.getReflexivedata(input);
        if (cursor.getCount() == 0) {
            Toast.makeText(reflexiveResultsActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()) {
                questionID.add(cursor.getString(0));
                set.add(cursor.getString(1));
                relation.add(cursor.getString(2));
                userAnswer.add(cursor.getString(3));
                if ((cursor.getString(3).equals("true") && cursor.getString(4).equals("true")) ||
                        (cursor.getString(3).equals("false") && cursor.getString(4).equals("false"))) {
                    ifCorrect.add("Right");
                }
                else {
                    ifCorrect.add("Wrong");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_result_logout, menu);
        return true;
    }

    public Boolean logoutIntent(MenuItem view){
        Intent intent = new Intent(reflexiveResultsActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(reflexiveResultsActivity.this, chooseResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(reflexiveResultsActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }
}