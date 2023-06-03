package com.example.fyp_926975;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class cartesianResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList questionNum, list1, list2, userAnswer, correctAnswer, ifCorrect;
    DBHelper DB;
    cartesianAdapter cartesianAdapter;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartesian_results);

        DB = new DBHelper(this);
        Button retry = (Button) findViewById(R.id.retrybutton);
        EditText questionID = (EditText) findViewById(R.id.questionIDinput);

        questionNum = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        userAnswer = new ArrayList<>();
        correctAnswer = new ArrayList<>();
        ifCorrect = new ArrayList<>();
        // using recyclerView to show results
        recyclerView = findViewById(R.id.recyclerview);
        cartesianAdapter = new cartesianAdapter(this, questionNum, list1, list2, userAnswer, correctAnswer, ifCorrect);
        recyclerView.setAdapter(cartesianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = getIntent().getIntExtra(loginActivity.USERID,0);
        displaydata(userID);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer questionNum = Integer.parseInt(questionID.getText().toString());
                if (DB.checkCartQuestion(questionNum, userID) == true) {
                    Intent intent = new Intent(cartesianResultsActivity.this, cartesianRetryActivity.class);
                    intent.putExtra("question", questionNum);
                    intent.putExtra(loginActivity.USERID, userID);
                    startActivity(intent);
                }
                else {
                    showMessage("The question does not exist. Please choose and existing question.");
                }
            }
        });
    }
    // used to display data in the recyclerView
    private void displaydata(Integer input) {
        Cursor cursor = DB.getCartesiandata(input);
        if (cursor.getCount() == 0) {
            Toast.makeText(cartesianResultsActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()) {
                questionNum.add(cursor.getString(0));
                list1.add(cursor.getString(1));
                list2.add(cursor.getString(2));
                userAnswer.add(cursor.getString(4));
                correctAnswer.add(cursor.getString(3));
                if (cursor.getString(5).equals("True")) {
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
        Intent intent = new Intent(cartesianResultsActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(cartesianResultsActivity.this, chooseResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(cartesianResultsActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(cartesianResultsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }
}