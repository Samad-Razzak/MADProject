package com.example.madproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madproject.helper.InputValidation;
import com.example.madproject.model.User;
import com.example.madproject.sql.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirm;
    EditText txtEmail;
    CheckBox chkAgree;

    DatabaseHelper databaseHelper;
    InputValidation inputValidation;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirm  = (EditText) findViewById(R.id.txtConfirm);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        chkAgree = (CheckBox) findViewById(R.id.AgreeChkBox);

        user = new User();
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
    }

    public void gotoLogin(View view)
    {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void signUp(View view){
        if(!inputValidation.isEditTextFilled(txtUsername, txtPassword, txtConfirm, txtEmail, chkAgree, getString(R.string.error_message))){
            return;
        }

        if(!inputValidation.isEmailValid(txtEmail, getString(R.string.error_message))){
            return;
        }

        if(!inputValidation.isPasswordMatches(txtPassword, txtConfirm, getString(R.string.error_message))){
            return;
        }

        if(!databaseHelper.checkUser(txtEmail.getText().toString().trim())){
            user.setName(txtUsername.getText().toString().trim());
            user.setEmail(txtEmail.getText().toString().trim());
            user.setPassword(txtPassword.getText().toString().trim());
            user.setAgree(chkAgree.isChecked() ? 1 : 0);

            databaseHelper.addUser(user);

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        }
    }
}
