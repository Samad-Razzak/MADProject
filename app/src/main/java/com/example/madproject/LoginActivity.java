package com.example.madproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madproject.helper.InputValidation;
import com.example.madproject.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    DatabaseHelper databaseHelper;
    InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
    }

    public void login(View view){
        if(!inputValidation.isEditTextFilled(txtUsername, txtPassword, getString(R.string.error_message))){
            return;
        }

        if(databaseHelper.checkUser(txtUsername.getText().toString().trim(),
                txtPassword.getText().toString())){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Invalid username password", Toast.LENGTH_SHORT).show();
        }
    }
}
