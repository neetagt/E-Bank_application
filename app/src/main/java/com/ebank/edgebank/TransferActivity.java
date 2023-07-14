package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransferActivity extends AppCompatActivity {

    TextView Acc_NoText;
    TextView amountText;
    String RecAccNo;
    String SenderAcc_No;
    String TransferAmount;
    String SenderBalance;
    String SenderNewBalance;
    String RecBalance;
    String RecNewBalance;
    String SenderDebt;
    SQLiteDatabase database;
    Cursor senderCursor;
    Cursor RecCursor;

    int RecAccIndex;
    int RecBalanceIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Acc_NoText = findViewById(R.id.RecAccNoText);
        amountText = findViewById(R.id.AmountSendText);

        RecAccNo = "";
        RecBalance = "";
        RecNewBalance = "";

        SenderAcc_No = MainActivity.Acc_No;
        SenderBalance = "";
        SenderNewBalance = "";
        SenderDebt = "";

        TransferAmount = "";

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
            senderCursor = database.rawQuery("SELECT * FROM data", null);
            int SenderAcc_NoIndex = senderCursor.getColumnIndex("COLUMN_ACCNO");
            int SenderBalanceIndex = senderCursor.getColumnIndex("COLUMN_BALANCE");
            int SenderDebtIndex = senderCursor.getColumnIndex("COLUMN_DEBT");

            RecCursor = database.rawQuery("SELECT * FROM data", null);
            RecAccIndex = RecCursor.getColumnIndex("COLUMN_ACCNO");
            RecBalanceIndex = RecCursor.getColumnIndex("COLUMN_BALANCE");

            while(senderCursor.moveToNext()) {
                if(SenderAcc_No.matches(senderCursor.getString(SenderAcc_NoIndex))) {
                    SenderBalance = senderCursor.getString(SenderBalanceIndex);
                    SenderDebt = senderCursor.getString(SenderDebtIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendMoney(View view) {
        RecAccNo = Acc_NoText.getText().toString();
        TransferAmount = amountText.getText().toString();

        boolean exists = false;

        try {
            Cursor existsCursor = database.rawQuery("SELECT * FROM data", null);
            int existsAccIndex = existsCursor.getColumnIndex("COLUMN_ACCNO");

            while(existsCursor.moveToNext()) {
                if(RecAccNo.matches(existsCursor.getString(existsAccIndex))) {
                    exists = true;
                    break;
                }
            }

            while(RecCursor.moveToNext()) {
                if(RecAccNo.matches(RecCursor.getString(RecAccIndex))) {
                    RecBalance = RecCursor.getString(RecBalanceIndex);
                }
            }

            if(exists == true) {
                if(Float.parseFloat(SenderBalance) >= Float.parseFloat(TransferAmount)) {
                    SenderNewBalance = String.valueOf(Float.parseFloat(SenderBalance) - Float.parseFloat(TransferAmount));
                    RecNewBalance = String.valueOf(Float.parseFloat(RecBalance) + Float.parseFloat(TransferAmount));
                    database.execSQL("UPDATE data SET COLUMN_BALANCE = ? WHERE COLUMN_ACCNO  = ?", (new String[] {SenderNewBalance, SenderAcc_No}));
                    database.execSQL("UPDATE data SET COLUMN_BALANCE = ? WHERE COLUMN_ACCNO  = ?", (new String[] {RecNewBalance, RecAccNo}));
                    Toast.makeText(this, "Your transfer was successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TransferActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                } else{
                    Toast.makeText(this, "You do not have enough balance in your account", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "ACCOUNT NOT FOUND", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TransferActivity.this, MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }


        } catch(Exception e) {
            System.out.println("ERROR : UNSUCCESSFUL");
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(TransferActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}