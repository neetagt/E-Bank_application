package com.emreguven.burmetembank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Account_DetailsActivity extends AppCompatActivity {

    TextView FullnameText;
    TextView balanceText;
    TextView debtText;
    String Acc_No;
    String balance;
    String firstname;
    String lastname;
    String debt;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        FullnameText = findViewById(R.id.fullnameText);
        balanceText = findViewById(R.id.balanceText);
        debtText = findViewById(R.id.debtText);

        Acc_No = MainActivity.Acc_No;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");
            int balanceIndex = cursor.getColumnIndex("COLUMN_BALANCE");
            int firstNameIndex = cursor.getColumnIndex("COLUMN_FIRSTNAME");
            int lastNameIndex = cursor.getColumnIndex("COLUMN_LASTNAME");
            int debtIndex = cursor.getColumnIndex("COLUMN_DEBT");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    balance = cursor.getString(balanceIndex);
                    firstname = cursor.getString(firstNameIndex);
                    lastname = cursor.getString(lastNameIndex);
                    debt = cursor.getString(debtIndex);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FullnameText.setText(firstname + " " + lastname);
        balanceText.setText(balance);
        debtText.setText(debt);
    }

    public void goBack(View view) {
        Intent intent = new Intent(Account_DetailsActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}