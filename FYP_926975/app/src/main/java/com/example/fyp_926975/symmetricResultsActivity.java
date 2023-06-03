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

public class symmetricResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList questionID, set, relation, userAnswer, ifCorrect;
    DBHelper DB;
    symmetricAdapter symmetricAdapter;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetric_results);

        DB = new DBHelper(this);
        Button retry = (Button) findViewById(R.id.retrybuttonSym);
        EditText questionID = (EditText) findViewById(R.id.questionIDinputSym);

        this.questionID = new ArrayList<>();
        set = new ArrayList<>();
        relation = new ArrayList<>();
        userAnswer = new ArrayList<>();
        ifCorrect = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerviewSym);
        symmetricAdapter = new symmetricAdapter(this, this.questionID, set, relation, userAnswer, ifCorrect);
        recyclerView.setAdapter(symmetricAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = getIntent().getIntExtra(loginActivity.USERID,0);
        displaydata(userID);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer questionNum = Integer.parseInt(questionID.getText().toString());
                if (DB.checkSymQuestion(questionNum, userID) == true) {
                    Intent intent = new Intent(symmetricResultsActivity.this, symmetricRetryActivity.class);
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

    private void displaydata(Integer input) {
        Cursor cursor = DB.getSymmetricData(input);
        if (cursor.getCount() == 0) {
            Toast.makeText(symmetricResultsActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(symmetricResultsActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(symmetricResultsActivity.this, chooseResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(symmetricResultsActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(symmetricResultsActivity.this)
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