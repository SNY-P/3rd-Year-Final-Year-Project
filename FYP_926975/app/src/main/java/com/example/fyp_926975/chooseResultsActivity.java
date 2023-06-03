package com.example.fyp_926975;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class chooseResultsActivity extends AppCompatActivity {
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_results);

        Button cart = (Button) findViewById(R.id.cartesianResultButton);
        Button trans = (Button) findViewById(R.id.transitiveResultButton);
        Button sym = (Button) findViewById(R.id.symmetricResultButton);
        Button ref = (Button) findViewById(R.id.reflexiveResultButton);

        userID = getIntent().getIntExtra(loginActivity.USERID, 0);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(chooseResultsActivity.this, cartesianResultsActivity.class);
                buttonClick.putExtra(loginActivity.USERID, userID);
                startActivity(buttonClick);
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(chooseResultsActivity.this, reflexiveResultsActivity.class);
                buttonClick.putExtra(loginActivity.USERID, userID);
                startActivity(buttonClick);
            }
        });

        sym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(chooseResultsActivity.this, symmetricResultsActivity.class);
                buttonClick.putExtra(loginActivity.USERID, userID);
                startActivity(buttonClick);
            }
        });

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(chooseResultsActivity.this, transitiveResultsActivity.class);
                buttonClick.putExtra(loginActivity.USERID, userID);
                startActivity(buttonClick);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_logout, menu);
        return true;
    }

    public Boolean logoutIntent(MenuItem view){
        Intent intent = new Intent(chooseResultsActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view){
        Intent intent = new Intent(chooseResultsActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }
}