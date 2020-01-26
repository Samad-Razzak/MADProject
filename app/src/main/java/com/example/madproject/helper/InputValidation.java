package com.example.madproject.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.regex.Pattern;

public class InputValidation {

    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    public boolean isEditTextFilled(EditText txtEditText1, EditText txtEditText2, EditText txtEditText3,
                                    EditText txtEditText4, CheckBox chkBox, String message){
        String value1 = txtEditText1.getText().toString().trim();
        String value2 = txtEditText2.getText().toString().trim();
        String value3 = txtEditText3.getText().toString().trim();
        String value4 = txtEditText4.getText().toString().trim();

        if(value1.isEmpty())
        {
            txtEditText1.setError(message);
            hideKeyboardFrom(txtEditText1);
            return false;
        }else if(value2.isEmpty()){
            txtEditText2.setError(message);
            hideKeyboardFrom(txtEditText2);
            return false;
        }else if(value3.isEmpty()){
            txtEditText3.setError(message);
            hideKeyboardFrom(txtEditText3);
            return false;
        }else if(value4.isEmpty()){
            txtEditText4.setError(message);
            hideKeyboardFrom(txtEditText4);
            return false;
        }else if(!chkBox.isChecked()){
            chkBox.setError(message);
            return false;
        }
        return true;
    }

    public boolean isEditTextFilled(EditText txtEditText1, EditText txtEditText2, String message){
        String value1 = txtEditText1.getText().toString().trim();
        String value2 = txtEditText2.getText().toString().trim();

        if(value1.isEmpty())
        {
            txtEditText1.setError(message);
            hideKeyboardFrom(txtEditText1);
            return false;
        }else if(value2.isEmpty()){
            txtEditText2.setError(message);
            hideKeyboardFrom(txtEditText2);
            return false;
        }
        return true;
    }

    public boolean isEmailValid(EditText txtEditText, String message){
        String value = txtEditText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            hideKeyboardFrom(txtEditText);
            return false;
        }
        return true;
    }

    public boolean isPasswordMatches(EditText txtEditText1, EditText txtEditText2, String message){
        String pass1 = txtEditText1.getText().toString().trim();
        String pass2 = txtEditText2.getText().toString().trim();
        if(!pass1.contentEquals(pass2)){
            txtEditText2.setError(message);
            hideKeyboardFrom(txtEditText2);
            return false;
        }
        return true;
    }

    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
