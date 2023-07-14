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

public class WithdrawalActivity extends AppCompatActivity {

    TextView amountText;
    Button sendButton;
    Button cancelButton;
    String Acc_No;
    String balance;
    String newAmount;
    String withdrawalAmount;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        amountText = findViewById(R.id.WithdrawalAmountText);
        sendButton = findViewById(R.id.CheckAccountButton);
        cancelButton = findViewById(R.id.WithdrawalCancelButton);

        Acc_No = MainActivity.Acc_No;
        balance = "";
        newAmount = "";

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void balance(View view) {
        withdrawalAmount = amountText.getText().toString();

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int balanceIndex = cursor.getColumnIndex("COLUMN_BALANCE");
        int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");

        while(cursor.moveToNext()) {
            if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                balance = cursor.getString(balanceIndex);
            }
        }

        if(Float.parseFloat(balance) - Float.parseFloat(withdrawalAmount) >= 0) {
            newAmount = String.valueOf(Float.parseFloat(balance) - Float.parseFloat(withdrawalAmount));
            database.execSQL("UPDATE data SET COLUMN_BALANCE  = ? WHERE COLUMN_ACCNO  = ? ", (new String[] {newAmount, Acc_No}));
            Toast.makeText(this, "Your transaction was successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WithdrawalActivity.this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            } else {
            Toast.makeText(this, "No sufficient balance", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(WithdrawalActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}