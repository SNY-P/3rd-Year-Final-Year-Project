package com.example.fyp_926975;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class symmetricRetryActivity extends AppCompatActivity {
    DBHelper db;
    Relations rel;
    Integer userID;
    String set, relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetric_retry);

        db = new DBHelper(this);
        rel = new Relations();
        TextView setView = (TextView) findViewById(R.id.setSR);
        TextView message = (TextView) findViewById(R.id.correctSR);
        Button backButton = (Button) findViewById(R.id.backSymmetric);
        Button confirmButton = (Button) findViewById(R.id.confirmSR);
        EditText editText1 = findViewById(R.id.value1SR);
        EditText editText2 = findViewById(R.id.value2SR);

        userID = getIntent().getIntExtra(loginActivity.USERID,0);;
        Integer questionNo = getIntent().getIntExtra("question", 0);

        Cursor cursor = db.getSpecificSymdata(questionNo, userID);
        while (cursor.moveToNext()) {
            set = cursor.getString(1);
            relation = cursor.getString(2);
        }
        setView.setText(relation);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer v1 = Integer.parseInt(editText1.getText().toString());
                Integer v2 = Integer.parseInt(editText2.getText().toString());

                ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
                temp.add(new ArrayList<>());
                temp.add(new ArrayList<>());
                temp.get(0).add(v1);
                temp.get(0).add(v2);
                temp.get(1).add(v2);
                temp.get(1).add(v1);

                if (relation.contains(temp.get(0).toString()) && relation.contains(temp.get(1).toString())) {
                    message.setText("Well Done! Your Answer is Correct");
                    db.insertSymmetricData(set, relation, "true", "true", userID);
                }
                else {
                    message.setText("It Seems You Answer is Incorrect. Please Try Again");
                    db.insertSymmetricData(set, relation, "true", "false", userID);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(symmetricRetryActivity.this, symmetricResultsActivity.class);
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

    public Boolean logoutIntent(MenuItem view) {
        Intent intent = new Intent(symmetricRetryActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }
}