package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    ImageView image;
    TextView firstnameText;
    ImageButton WithdrawalButton;
    ImageButton AccountDetailButton;
    ImageButton DepositMoneyButton;
    ImageButton debtButton;
    ImageButton transferButton;
    ImageButton creditButton;
    ImageButton passwordButton;
    ImageButton settingsButton;
    String name;
    String lastname;
    String sex;
    String Acc_No;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        image = findViewById(R.id.UserImage);
        firstnameText = findViewById(R.id.firstNameText);
        WithdrawalButton = findViewById(R.id.ChequeBookButton);
        AccountDetailButton = findViewById(R.id.AccountDetailsButton);
        DepositMoneyButton = findViewById(R.id.DepositMoneyButton2);
        debtButton = findViewById(R.id.showDebtsButton);
        transferButton = findViewById(R.id.TransactionsButton);
        creditButton = findViewById(R.id.CalculateCreditButton);
        passwordButton = findViewById(R.id.PasswordGenButton);
        settingsButton = findViewById(R.id.SettingsButton);

        Acc_No = MainActivity.Acc_No;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int FirstNameIndex = cursor.getColumnIndex("COLUMN_FIRSTNAME" + "");
            int LastnameIndex = cursor.getColumnIndex("COLUMN_LASTNAME");
            int GenderIndex = cursor.getColumnIndex("COLUMN_GENDER");
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    name = cursor.getString(FirstNameIndex);
                    lastname = cursor.getString(LastnameIndex);
                    sex = cursor.getString(GenderIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstnameText.setText(name + " " + lastname);

        if(sex.matches("Male")) {
            image.setImageResource(R.drawable.mr);
        }
        if(sex.matches("Female")) {
            image.setImageResource(R.drawable.mrs);
        }
    }

    public void WithdrawalActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, WithdrawalActivity.class);
        startActivity(intent);
    }

    public void Account_DetailsActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, Account_DetailsActivity.class);
        startActivity(intent);
    }

    public void DepositActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, DepositActivity.class);
        startActivity(intent);
    }

    public void DebtsActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, DebtsActivity.class);
        startActivity(intent);
    }

    public void TransferActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, TransferActivity.class);
        startActivity(intent);
    }

    public void Calculate_creditActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, Calculate_creditActivity.class);
        startActivity(intent);
    }

    public void PasswordActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, PasswordActivity.class);
        startActivity(intent);
    }

    public void SettingsActivity(View view) {
        Intent intent = new Intent(MainActivity2.this, SettingsActivity.class);
        startActivity(intent);
    }
}