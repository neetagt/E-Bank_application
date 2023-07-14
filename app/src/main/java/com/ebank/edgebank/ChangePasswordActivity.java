package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView oldPasswordText;
    TextView NewPasswordText;
    String currentPassword;
    String newPassword;
    String oldPassword;
    String Acc_No;
    boolean exists;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordText = findViewById(R.id.OldPasswordText);
        NewPasswordText = findViewById(R.id.NewPasswordText);

        Acc_No = MainActivity.Acc_No;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
            int passwordIndex = cursor.getColumnIndex("COLUMN_PASSWORD");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    currentPassword = cursor.getString(passwordIndex);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(View view) {
        oldPassword = oldPasswordText.getText().toString();
        newPassword = NewPasswordText.getText().toString();

        if(oldPassword.matches(currentPassword)) {
            database.execSQL("UPDATE data SET COLUMN_PASSWORD  = ? WHERE COLUMN_ACCNO  = ?", (new String[] {newPassword, Acc_No}));
            Toast.makeText(this, "Your password has been successfully updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}