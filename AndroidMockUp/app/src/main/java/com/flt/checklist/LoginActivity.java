package com.flt.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
    }

    public void launchActivity(View view) {

        if(etUsername.getText().toString().equals("test")
        && etPassword.getText().toString().equals("password")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_LONG).show();
    }
}