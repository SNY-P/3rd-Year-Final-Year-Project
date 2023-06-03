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

public class cartesianRetryActivity extends AppCompatActivity {
    cartesian cart;
    DBHelper db;
    String cL ,iL, product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartesian_retry);

        cart = new cartesian();
        db = new DBHelper(this);
        Button submitButton = (Button) findViewById(R.id.SubmitButtonRetry);
        Button backButton = (Button) findViewById(R.id.backRetry);
        EditText editText = (EditText) findViewById(R.id.userInputRetry);
        TextView list1View = (TextView) findViewById(R.id.list1Retry);
        TextView list2View = (TextView) findViewById(R.id.list2Retry);

        Integer userID = getIntent().getIntExtra(loginActivity.USERID,0);;
        Integer questionNo = getIntent().getIntExtra("question", 0);

        Cursor cursor = db.getSpecificCartdata(questionNo, userID);
        while (cursor.moveToNext()) {
            cL = cursor.getString(1);
            iL = cursor.getString(2);
            product = cursor.getString(3);
        }
        list1View.setText(cL);
        list2View.setText(iL);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uInput = editText.getText().toString();

                if (product.equals(uInput)) {
                    showMessage("Your answer is correct.");
                    db.insertCartesianData(cL, iL, product, uInput, "true", userID);
                }
                else {
                    showMessage("Your answer is incorrect. Please try again.");
                    db.insertCartesianData(cL, iL, product, uInput, "false", userID);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cartesianRetryActivity.this, cartesianResultsActivity.class);
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
        Intent intent = new Intent(cartesianRetryActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(cartesianRetryActivity.this)
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