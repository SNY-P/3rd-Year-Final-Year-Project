package com.example.fyp_926975;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

public class transitiveRetryActivity extends AppCompatActivity {
    DBHelper db;
    Relations rel;
    Integer userID;
    String set, relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitive_retry);

        db = new DBHelper(this);
        rel = new Relations();
        EditText x = (EditText) findViewById(R.id.valueXTR);
        EditText y = (EditText) findViewById(R.id.valueYTR);
        EditText z = (EditText) findViewById(R.id.valueZTR);
        Button confirmButton = (Button) findViewById(R.id.confirmTR);
        Button backButton = (Button) findViewById(R.id.backTransitive);
        TextView setView = (TextView) findViewById(R.id.setTR);
        TextView message = (TextView) findViewById(R.id.correctTR);

        userID = getIntent().getIntExtra(loginActivity.USERID, 0);
        Integer questionNo = getIntent().getIntExtra("question", 0);

        Cursor cursor = db.getSpecificTransdata(questionNo, userID);
        while (cursor.moveToNext()) {
            set = cursor.getString(1);
            relation = cursor.getString(2);
        }
        setView.setText(relation);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1 = x.getText().toString();
                String v2 = y.getText().toString();
                String v3 = z.getText().toString();
                String combined = "[" + v1 + ", " + v2 + ", " + v3 + "]";

                if (v1.isEmpty() || v2.isEmpty() || v3.isEmpty()) {
                    showMessage("Please enter the values");
                }
                else {
                    if (relation.contains(v1) && relation.contains(v2)) {
                        if (relation.contains(v3)) {
                            message.setText("Well Done! Your Answer is Correct");
                            db.insertTransitiveData(set, relation, combined, "true", userID);
                        }
                        else {
                            message.setText("It Seems You Answer is Incorrect. Please Try Again");
                            db.insertTransitiveData(set, relation, combined, "false", userID);
                        }
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(transitiveRetryActivity.this, transitiveResultsActivity.class);
                intent.putExtra(loginActivity.USERID, userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    public Boolean logoutIntent(MenuItem view){
        Intent intent = new Intent(transitiveRetryActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(transitiveRetryActivity.this)
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