package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    TextView AccNoTextView;
    TextView passwordTextView;
    Button signInButton;
    TextView forgotTextView;
    Button signUpButton;
    SQLiteDatabase database;
    public static String Acc_No;
    String password;
    boolean exists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccNoTextView = findViewById(R.id.tcTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        signInButton = findViewById(R.id.signInButton);
        forgotTextView = findViewById(R.id.forgotTextView);
        signUpButton = findViewById(R.id.signUpButton);
        Acc_No="";
        password = "";
        exists=false;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS data (COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_FIRSTNAME VARCHAR(20), COLUMN_LASTNAME VARCHAR(20), COLUMN_ACCNO VARCHAR(12), COLUMN_PLACE VARCHAR(20), COLUMN_BALANCE DECIMAL(18,2), COLUMN_DEBT DECIMAL(18,2), COLUMN_PHONE VARCHAR(10), COLUMN_PASSWORD VARCHAR(8), COLUMN_GENDER VARCHAR(6))");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signIn(View view) {

        Acc_No = AccNoTextView.getText().toString();
        password = passwordTextView.getText().toString();
        exists=false;

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
            int passwordIndex = cursor.getColumnIndex("COLUMN_PASSWORD");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex)) && password.matches(cursor.getString(passwordIndex))) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    exists = true;
                    break;
                }
            }
            if(exists == false)
            {
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}