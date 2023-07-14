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


public class SignUpActivity extends AppCompatActivity {

    SQLiteDatabase database;
    TextView FirstnameText;
    TextView LastnameText;
    TextView Acc_NoText;
    TextView nativeText;
    TextView amountText;
    TextView debtText;
    TextView telephoneText;
    TextView passwordText;
    TextView genderText;
    Button signUpButton;
    String FirstName;
    String LastName;
    String Acc_No;
    String Place;
    String Balance;
    String Debt;
    String Phone;
    String Password;
    String Gender;
    boolean exists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        FirstnameText = findViewById(R.id.FirstNameText);
        LastnameText = findViewById(R.id.LastnameText);
        Acc_NoText = findViewById(R.id.AadharText);
        nativeText = findViewById(R.id.NativeText);
        amountText = findViewById(R.id.curbalText);
        debtText = findViewById(R.id.debtText2);
        telephoneText = findViewById(R.id.telephoneText);
        passwordText = findViewById(R.id.passwordText2);
        genderText = findViewById(R.id.GenderText);
        signUpButton = findViewById(R.id.signUpButton2);

        FirstName = "";
        LastName= "";
        Acc_No = "";
        Place = "";
        Balance = "";
        Debt = "";
        Phone = "";
        Password = "";
        Gender = "";
        exists=false;

        try {
            database = this.openOrCreateDatabase("DATABASE_NAME", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signUp(View view) {
        FirstName = FirstnameText.getText().toString();
        LastName = LastnameText.getText().toString();
        Acc_No = Acc_NoText.getText().toString();
        Place = nativeText.getText().toString();
        Balance = amountText.getText().toString();
        Debt = debtText.getText().toString();
        Phone = telephoneText.getText().toString();
        Password = passwordText.getText().toString();
        Gender = genderText.getText().toString();
        exists=false;

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int Acc_NoIndex = cursor.getColumnIndex("COLUMN_ACCNO");

            while(cursor.moveToNext()) {
                if(Acc_No.matches(cursor.getString(Acc_NoIndex))) {
                    exists = true;
                    break;
                }
            }
             if(exists == false) {
                database.execSQL("INSERT INTO data (COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_ACCNO, COLUMN_PLACE, COLUMN_BALANCE," +
                        " COLUMN_DEBT, COLUMN_PHONE, COLUMN_PASSWORD, COLUMN_GENDER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        (new String[] {FirstName, LastName, Acc_No, Place, Balance, Debt, Phone, Password, Gender}));
                Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "An Account with this Account Number already exists", Toast.LENGTH_SHORT).show();
            }
            } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}