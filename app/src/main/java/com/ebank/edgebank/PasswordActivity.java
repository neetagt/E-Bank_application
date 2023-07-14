package com.emreguven.burmetembank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PasswordActivity extends AppCompatActivity {

    TextView PasswordText;
    String Password;
    int random_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        PasswordText = findViewById(R.id.PasswordGenText);
        Password = "";

        Random random = new Random();

        for(int i = 0; i < 8; i++) {
            random_int = random.nextInt(10);
            Password += String.valueOf(random_int);
        }
        PasswordText.setText(Password);
    }

    public void goBack(View view) {
        Intent intent = new Intent(PasswordActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}