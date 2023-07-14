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

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView Acc_NoText;
    TextView passwordText;
    Button forgotButton;
    String Acc_No;
    String password;
    SQLiteDatabase database;
    boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Acc_NoText = findViewById(R.id.tcTextView2);
        passwordText = findViewById(R.id.passwordTextView2);
        forgotButton = findViewById(R.id.forgotButton);

        Acc_No = "";
        password = "";
        exists = false;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(View view) {
        Acc_No = Acc_NoText.getText().toString();
        exists = false;

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
        int passwordIndex = cursor.getColumnIndex("COLUMN_PASSWORD");

        while(cursor.moveToNext()) {
            if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                password = cursor.getString(passwordIndex);
                exists = true;
                break;
            }
        }
        if(exists == true) {
            passwordText.setText(password);
        } else {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}