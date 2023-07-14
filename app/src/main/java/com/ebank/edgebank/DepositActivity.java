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

public class DepositActivity extends AppCompatActivity {

    TextView amountText;
    Button DepositButton;
    Button DepositCancelButton;
    String Acc_No;
    String balance;
    String newAmount;
    String DepositMoney;
    String debt;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        amountText = findViewById(R.id.DepositAmountText);
        DepositButton = findViewById(R.id.DepositMoneyButton);
        DepositCancelButton = findViewById(R.id.DepositCancelButton);

        Acc_No = MainActivity.Acc_No;
        balance = "";
        newAmount = "";
        debt = "";

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Amount(View view) {
        DepositMoney = amountText.getText().toString();

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int balanceIndex = cursor.getColumnIndex("COLUMN_BALANCE");
        int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
        int debtIndex = cursor.getColumnIndex("COLUMN_DEBT");

        while(cursor.moveToNext()) {
            if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                balance = cursor.getString(balanceIndex);
                debt = cursor.getString(debtIndex);
            }
        }

        newAmount = String.valueOf(Float.parseFloat(balance) + Float.parseFloat(DepositMoney));
        database.execSQL("UPDATE data SET COLUMN_BALANCE  = ? WHERE COLUMN_ACCNO  = ? ", (new String[] {newAmount, Acc_No}));
        Toast.makeText(this, "Your transaction was successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DepositActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(DepositActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}