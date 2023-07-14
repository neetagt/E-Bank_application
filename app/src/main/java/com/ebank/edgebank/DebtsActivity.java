package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DebtsActivity extends AppCompatActivity {

    TextView debtText;
    String Acc_No;
    String debt;
    String balance;
    String newBalance;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);

        debtText = findViewById(R.id.TotalDebtText);

        Acc_No = MainActivity.Acc_No;
        newBalance = "";

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
            int debtIndex = cursor.getColumnIndex("COLUMN_DEBT");
            int balanceIndex = cursor.getColumnIndex("COLUMN_BALANCE");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    debt = cursor.getString(debtIndex);
                    balance = cursor.getString(balanceIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        debtText.setText(debt);
    }

    public void CalculateDebt(View view) {
        if(Float.parseFloat(balance) >= Float.parseFloat(debt)) {
            newBalance = String.valueOf(Float.parseFloat(balance) - Float.parseFloat(debt));
            debt = String.valueOf(0);
            database.execSQL("UPDATE data SET COLUMN_BALANCE = ?, COLUMN_DEBT = ? WHERE COLUMN_ACCNO  = ?", (new String[] {newBalance, debt, Acc_No}));
            Toast.makeText(this, "All your debts have been paid", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DebtsActivity.this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "No sufficient Balance in your account", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(DebtsActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}