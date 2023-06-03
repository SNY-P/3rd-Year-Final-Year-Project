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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class symmetricQuestionActivity extends AppCompatActivity {
    DBHelper db;
    Relations rel;
    public ArrayList<Integer> set;
    public ArrayList<ArrayList<Integer>> list, listSort;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetric_question);

        db = new DBHelper(this);
        rel = new Relations();
        TextView setView = (TextView) findViewById(R.id.setS);
        TextView message = (TextView) findViewById(R.id.correctS);
        Button trueClick = (Button) findViewById(R.id.trueButtonS);
        Button falseClick = (Button) findViewById(R.id.falseButtonS);
        Button nextQuestion = (Button) findViewById(R.id.nextButtonS);
        Button confirmButton = (Button) findViewById(R.id.confirmS);
        Button helpButton = (Button) findViewById(R.id.helpSymmetric);
        EditText editText1 = findViewById(R.id.value1S);
        EditText editText2 = findViewById(R.id.value2S);

        userID = getIntent().getIntExtra(loginActivity.USERID ,0);

        set = rel.createSet();
        list = rel.relListCreate(rel.relData2d(set));
        listSort = sorting(list);
        String str = listSort.toString();
        setView.setText(str);

        trueClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rel.isSymmetric(listSort)) {
                    editText1.setEnabled(true);
                    editText2.setEnabled(true);
                    confirmButton.setEnabled(true);
                }
                else {
                    message.setText("It Seems You Answer is Incorrect. Please Try Again");
                    db.insertSymmetricData(set.toString(), listSort.toString(), "true", "false", userID);
                }
            }
        });

        falseClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setEnabled(false);
                editText2.setEnabled(false);
                confirmButton.setEnabled(false);
                if (listSort.isEmpty() || rel.isSymmetric(listSort) == false) {
                    message.setText("Well Done! Your Answer is Correct");
                    db.insertSymmetricData(set.toString(), listSort.toString(), "false", "false", userID);
                }
                else if (rel.isSymmetric(listSort) == true) {
                    message.setText("It Seems You Answer is Incorrect. Please Try Again");
                    db.insertSymmetricData(set.toString(), listSort.toString(), "false", "true", userID);
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
                editText1.setText("");
                editText2.setText("");
                editText1.setEnabled(false);
                editText2.setEnabled(false);
                confirmButton.setEnabled(false);
                message.setText("");
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1Str = editText1.getText().toString();
                String v2Str = editText2.getText().toString();

                if (v1Str.isEmpty() || v2Str.isEmpty()) {
                    showMessage("Please enter your answer");
                }
                else {
                    Integer v1 = Integer.parseInt(v1Str);
                    Integer v2 = Integer.parseInt(v2Str);

                    ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
                    temp.add(new ArrayList<>());
                    temp.add(new ArrayList<>());
                    temp.get(0).add(v1);
                    temp.get(0).add(v2);
                    temp.get(1).add(v2);
                    temp.get(1).add(v1);

                    if (listSort.contains(temp.get(0)) && listSort.contains(temp.get(1))) {
                        message.setText("Well Done! Your Answer is Correct");
                        db.insertSymmetricData(set.toString(), listSort.toString(), "true", "true", userID);
                    }
                    else {
                        message.setText("It Seems You Answer is Incorrect. Please Try Again");
                        db.insertSymmetricData(set.toString(), listSort.toString(), "true", "false", userID);
                    }
                }
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("Let R be a binary relation on a set A.\n" +
                            "R is symmetric if and only if for all x, y ∈ A if (x, y) ∈ R then " +
                            "(y, x) ∈ R");
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
        Intent intent = new Intent(symmetricQuestionActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(symmetricQuestionActivity.this, symmetricResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(symmetricQuestionActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public ArrayList<ArrayList<Integer>> sorting(ArrayList<ArrayList<Integer>> data) {
        Collections.sort(data, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> integers, ArrayList<Integer> t1) {
                return integers.get(0).compareTo(t1.get(0));
            }
        });

        return data;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(symmetricQuestionActivity.this)
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