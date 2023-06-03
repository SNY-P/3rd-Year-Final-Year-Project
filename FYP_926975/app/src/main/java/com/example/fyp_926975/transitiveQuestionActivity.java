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

public class transitiveQuestionActivity extends AppCompatActivity {
    DBHelper db;
    Relations rel;
    public ArrayList<Integer> set;
    public ArrayList<ArrayList<Integer>> list, listSort;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitive_question);

        db = new DBHelper(this);
        rel = new Relations();
        EditText x = (EditText) findViewById(R.id.valueXT);
        EditText y = (EditText) findViewById(R.id.valueYT);
        EditText z = (EditText) findViewById(R.id.valueZT);
        Button confirmButton = (Button) findViewById(R.id.confirmT);
        Button nextButton = (Button) findViewById(R.id.nextButtonT);
        Button helpButton = (Button) findViewById(R.id.helpTransitive);
        TextView setView = (TextView) findViewById(R.id.setT);
        TextView message = (TextView) findViewById(R.id.correctT);

        userID = getIntent().getIntExtra(loginActivity.USERID, 0);

        set = rel.createSet();
        list = rel.relListCreate(rel.relData2d(set));
        listSort = sorting(list);
        String str = listSort.toString();
        setView.setText(str);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xStr = x.getText().toString();
                String yStr = y.getText().toString();
                String zStr = z.getText().toString();

                if (xStr.isEmpty() || yStr.isEmpty() || zStr.isEmpty()) {
                    showMessage("Please enter the values");
                }
                else {
                    Integer v1 = Integer.parseInt(xStr);
                    Integer v2 = Integer.parseInt(yStr);
                    Integer v3 = Integer.parseInt(zStr);

                    ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
                    temp.add(new ArrayList<>());
                    temp.add(new ArrayList<>());
                    temp.add(new ArrayList<>());

                    temp.get(0).add(v1);
                    temp.get(0).add(v2);
                    temp.get(1).add(v2);
                    temp.get(1).add(v3);
                    temp.get(2).add(v1);
                    temp.get(2).add(v3);

                    if (rel.isTransitive(listSort, temp)) {
                        message.setText("Well Done! Your Answer is Correct");
                        db.insertTransitiveData(set.toString(), listSort.toString(), temp.toString(), "true", userID);
                    }
                    else {
                        message.setText("It Seems You Answer is Incorrect. Please Try Again");
                        db.insertTransitiveData(set.toString(), listSort.toString(), temp.toString(), "false", userID);
                    }
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set = rel.createSet();
                list = rel.relListCreate(rel.relData2d(set));
                listSort = sorting(list);
                x.setText("");
                y.setText("");
                z.setText("");
                setView.setText(listSort.toString());
                message.setText("");
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("Let R be a binary relation on a set A.\n" +
                            "R is transitive if and only if for all x, y, z ∈ A if (x, y) ∈ R and " +
                            "(y, z) ∈ R then (x, z) ∈ R");
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
        Intent intent = new Intent(transitiveQuestionActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(transitiveQuestionActivity.this, transitiveResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(transitiveQuestionActivity.this, choosePageActivity.class);
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
        AlertDialog dialog = new AlertDialog.Builder(transitiveQuestionActivity.this)
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