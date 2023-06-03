package com.example.fyp_926975;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class choosePageActivity extends AppCompatActivity {
    Integer uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_page);

        Button cartProd = (Button) findViewById(R.id.cartProdButton);
        Button sym = (Button) findViewById(R.id.symmetric);
        Button tran = (Button) findViewById(R.id.transitive);
        Button ref = (Button) findViewById(R.id.reflexive);
        Button results = (Button) findViewById(R.id.resultspage);

        uID = getIntent().getIntExtra(loginActivity.USERID, 0);

        cartProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(choosePageActivity.this, cartesianProductActivity.class);
                buttonClick.putExtra(loginActivity.USERID, uID);
                startActivity(buttonClick);
            }
        });

        sym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(choosePageActivity.this, symmetricQuestionActivity.class);
                buttonClick.putExtra(loginActivity.USERID, uID);
                startActivity(buttonClick);
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(choosePageActivity.this, reflexiveQuestionActivity.class);
                buttonClick.putExtra(loginActivity.USERID, uID);
                startActivity(buttonClick);
            }
        });

        tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(choosePageActivity.this, transitiveQuestionActivity.class);
                buttonClick.putExtra(loginActivity.USERID, uID);
                startActivity(buttonClick);
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonClick = new Intent(choosePageActivity.this, chooseResultsActivity.class);
                buttonClick.putExtra(loginActivity.USERID, uID);
                startActivity(buttonClick);
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
        Intent intent = new Intent(choosePageActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }
}