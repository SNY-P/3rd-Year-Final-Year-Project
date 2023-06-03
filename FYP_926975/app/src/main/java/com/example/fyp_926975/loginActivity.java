package com.example.fyp_926975;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class loginActivity extends AppCompatActivity {
    DBHelper db;
    public static final String USERID = "USERID"; // used for passing variable through intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
        EditText loginUsername = (EditText) findViewById(R.id.loginusername);
        EditText loginPassword = (EditText) findViewById(R.id.loginpassword);
        EditText registerUsername = (EditText) findViewById(R.id.registerusername);
        EditText registerPassword = (EditText) findViewById(R.id.registerpassword);
        Button loginButton = (Button) findViewById(R.id.login);
        Button registerButton = (Button) findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uN = loginUsername.getText().toString();
                String pW = loginPassword.getText().toString();
                String hashedPW = md5(pW); // encrypted password

                if (uN.isEmpty() || pW.isEmpty()) { //empty editText
                    showMessage("Please enter your username and/or password.");
                } // both username and password are correct
                else if (db.checkUsername(uN) == true && db.checkPassword(hashedPW) == true) {
                    Intent intent = new Intent(loginActivity.this, choosePageActivity.class);

                    String uID = "";
                    Cursor user = db.getUserid(uN);
                    while (user.moveToNext()) {
                        uID = user.getString(0);
                    }
                    Integer userID = Integer.parseInt(uID);

                    intent.putExtra(USERID, userID);
                    startActivity(intent);
                }
                else if (db.checkUsername(uN) == false) { // username is incorrect
                    showMessage("Cannot find username. Please try again or register a new account.");
                } // username is correct but password is not
                else if (db.checkUsername(uN) == true && db.checkPassword(hashedPW) == false) {
                    showMessage("Password is incorrect. Please try again.");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uN = registerUsername.getText().toString();
                String pW = registerPassword.getText().toString();
                String hashedPW = md5(pW);

                if (uN.isEmpty() || pW.isEmpty()) { // empty editText
                    showMessage("Please enter a username and/or password.");
                }
                else if (db.checkUsername(uN) == true) { // checks if user already exists
                    showMessage("Username already exists. Please try again.");
                }
                else { // otherwise add the user to database
                    if (db.insertUserData(uN, hashedPW)) {
                        showMessage("You are now registered");
                        registerUsername.setText(""); // clears the editText boxes
                        registerPassword.setText("");
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empty, menu);
        return true;
    }
    // used for encryption of password
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void showMessage(String message) {
        AlertDialog dialog = new AlertDialog.Builder(loginActivity.this)
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