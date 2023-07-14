package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculate_creditActivity extends AppCompatActivity {

    TextView monthlyPayText;
    TextView ROIText;
    TextView RepaymentText;
    SeekBar CreditBar;
    SeekBar MaturityBar;

    SQLiteDatabase database;
    String Acc_No;
    String debt;
    String balance;
    String newBalance;
    String newDebt;

    float CreditProgress;
    float MaturityProgress;
    float TotalInterest;
    float MonthlyTotal;
    float Interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_credit);

        monthlyPayText = findViewById(R.id.MonthlyPayText);
        ROIText = findViewById(R.id.InterestRateText);
        RepaymentText = findViewById(R.id.RepaymentText);
        CreditBar= findViewById(R.id.CreditSeekBar);
        MaturityBar = findViewById(R.id.MaturitySeekBar);

        CreditProgress = 0f;
        MaturityProgress = 0f;
        TotalInterest = 0f;
        MonthlyTotal = 0f;
        Interest = 1.21f;
        balance = "";
        debt = "";
        newBalance = "";
        newDebt = "";

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
            Acc_No = MainActivity.Acc_No;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculate(View view) {
        CreditProgress = CreditBar.getProgress() * 2500;
        MaturityProgress = MaturityBar.getProgress() * 3;

        TotalInterest = CreditProgress * Interest;
        if(MaturityProgress == 0) {
            MonthlyTotal = TotalInterest;
        } else {
            MonthlyTotal = TotalInterest / MaturityProgress;
        }

        monthlyPayText.setText(String.valueOf(MonthlyTotal));
        ROIText.setText(String.valueOf(Interest));
        RepaymentText.setText(String.valueOf(TotalInterest));
    }

    public void apply(View view) {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
            int balanceIndex = cursor.getColumnIndex("COLUMN_BALANCE");
            int debtIndex = cursor.getColumnIndex("COLUMN_DEBT");
            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    balance = cursor.getString(balanceIndex);
                    debt = cursor.getString(debtIndex);
                }
            }
            newBalance = String.valueOf(Float.parseFloat(balance) + CreditProgress);
            newDebt = String.valueOf(Float.parseFloat(debt) + TotalInterest);
            database.execSQL("UPDATE data SET COLUMN_BALANCE  = ?, COLUMN_DEBT  = ? WHERE COLUMN_ACCNO  = ?", (new String[] {newBalance, newDebt, Acc_No}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Your loan application is successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Calculate_creditActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void goBack(View view) {
      Intent intent = new Intent(Calculate_creditActivity.this, MainActivity2.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      finish();
      startActivity(intent);
    }
}