package com.example.fyp_926975;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class reflexiveQuestionActivity extends AppCompatActivity {
    DBHelper db;
    Relations rel;
    public ArrayList<Integer> set;
    public ArrayList<ArrayList<Integer>> list, listSort;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflexive_question);

        db = new DBHelper(this);
        rel = new Relations();
        TextView message = (TextView) findViewById(R.id.correctR);
        TextView setView = (TextView) findViewById(R.id.setR);
        Button trueClick = (Button) findViewById(R.id.trueButtonR);
        Button falseClick = (Button) findViewById(R.id.falseButtonR);
        Button nextQuestion = (Button) findViewById(R.id.nextButtonR);
        Button helpButton = (Button) findViewById(R.id.helpReflexive);

        userID = getIntent().getIntExtra(loginActivity.USERID, 0);

        set = rel.createSet();
        list = rel.relListCreate(rel.relData2d(set));
        listSort = sorting(list);
        String str = listSort.toString();
        setView.setText(str);

        trueClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rel.isReflexive(listSort) == true) { // answer is correct
                    message.setText("Well Done! Your Answer is Correct");
                    db.insertReflexiveData(set.toString(), listSort.toString(), "true", "true", userID);
                }
                else { // answer is incorrect
                    message.setText("It Seems You Answer is Incorrect. Please Try Again");
                    db.insertReflexiveData(set.toString(), listSort.toString(), "true", "false", userID);

                }
            }
        });

        falseClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rel.isReflexive(listSort) == false) { // answer is correct
                    message.setText("Well Done! Your Answer is Correct");
                    db.insertReflexiveData(set.toString(), listSort.toString(), "false", "false", userID);
                }
                else { // answer is incorrect
                    message.setText("It Seems You Answer is Incorrect. Please Try Again");
                    db.insertReflexiveData(set.toString(), listSort.toString(), "false", "true", userID);

                }
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set = rel.createSet();
                list = rel.relListCreate(rel.relData2d(set));
                listSort = sorting(list);
                setView.setText(listSort.toString());
                message.setText("");
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("Let R be a binary relation on a set A.\n" +
                            "R is reflexive if and only if (x, x) ∈ R for all x ∈ A.");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_result_logout, menu);
        return true;
    }

    public Boolean logoutIntent(MenuItem view) {
        Intent intent = new Intent(reflexiveQuestionActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(reflexiveQuestionActivity.this, reflexiveResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(reflexiveQuestionActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    private ArrayList<ArrayList<Integer>> sorting(ArrayList<ArrayList<Integer>> data) {
        Collections.sort(data, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> integers, ArrayList<Integer> t1) {
                return integers.get(0).compareTo(t1.get(0));
            }
        });

        return data;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(reflexiveQuestionActivity.this)
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