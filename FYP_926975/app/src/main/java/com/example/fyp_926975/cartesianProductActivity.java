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

public class cartesianProductActivity extends AppCompatActivity {
    cartesian cart;
    DBHelper db;
    ArrayList<Character> cL;
    ArrayList<Integer> iL;
    Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartesian_product);

        cart = new cartesian();
        db = new DBHelper(this);
        Button submitButton = findViewById(R.id.SubmitButton);
        Button nextQuestion = findViewById(R.id.next);
        Button helpButton = findViewById(R.id.helpCartesian);
        EditText editText = findViewById(R.id.userInput);
        TextView list1View = (TextView) findViewById(R.id.list1);
        TextView list2View = (TextView) findViewById(R.id.list2);

        // get the userID for use in database
        userID = getIntent().getIntExtra(loginActivity.USERID, 0);

        cL = cart.list1();
        iL = cart.list2();

        String cLStr = "L = " + cL.toString();
        String iLStr = "M = " + iL.toString();
        list1View.setText(cLStr);
        list2View.setText(iLStr);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uInput = editText.getText().toString();
                ArrayList<Integer> check = cart.answerCheck(cart.listProduct(cL, iL), uInput);

                int correct = check.get(0);
                int incorrect = check.get(1);
                int total = correct + incorrect;

                if (uInput.isEmpty()) { //editText is empty
                    showMessage("Please enter your answer");
                }
                else if (correct > 0 && incorrect == 0) { // all of them are correct
                    showMessage("You got " + correct + " out of " + total);
                    db.insertCartesianData(cL.toString(), iL.toString(), cart.listProduct(cL, iL).toString(), uInput, "True", userID);
                }
                else if (correct >= 0 && incorrect > 0) { // some of them are correct
                    showMessage("You got " + correct + " out of " + total);
                    db.insertCartesianData(cL.toString(), iL.toString(), cart.listProduct(cL, iL).toString(), uInput, "False", userID);
                }
                else { // none of them are correct
                    showMessage("You got none correct");
                    db.insertCartesianData(cL.toString(), iL.toString(), cart.listProduct(cL, iL).toString(), uInput, "False", userID);
                }
            }
        });
        // create a new question when button is clicked
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                cL = cart.list1();
                iL = cart.list2();
                String cLStr = "L = " + cL.toString();
                String iLStr = "M = " + iL.toString();
                list1View.setText(cLStr);
                list2View.setText(iLStr);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("If A and B are sets, we call A × B the Cartesian product of A and B:\n" +
                            "\n" +
                            "A × B = {(a, b) | a ∈ A and b ∈ B}");
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
        Intent intent = new Intent(cartesianProductActivity.this, loginActivity.class);
        startActivity(intent);
        return true;
    }

    public Boolean resultIntent(MenuItem view) {
        Intent intent = new Intent(cartesianProductActivity.this, cartesianResultsActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public Boolean homeIntent(MenuItem view) {
        Intent intent = new Intent(cartesianProductActivity.this, choosePageActivity.class);
        intent.putExtra(loginActivity.USERID, userID);
        startActivity(intent);
        return true;
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(cartesianProductActivity.this)
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