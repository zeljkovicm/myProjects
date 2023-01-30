package com.example.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText firstName, lastName, email, password, confirmPassword;
    private TextView loginHere;
    private Button signUp;

    private final static String APP_DATA_EMAIL = "email";
    private final static String APP_DATA_FNAME = "firstName";
    private final static String APP_DATA_LNAME = "lastName";
    private final static String APP_DATA_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
    }

    private void initComponents() {

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
        loginHere = findViewById(R.id.textViewLoginHere);
        signUp = findViewById(R.id.buttonSignUp);

        loginHere.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewLoginHere:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonSignUp:

                String checkFirstName = firstName.getText().toString();
                String checkLastName = lastName.getText().toString();
                String checkEmail = email.getText().toString();
                String checkPassword = password.getText().toString();
                String checkConfirmPassword = confirmPassword.getText().toString();

                if(checkFirstName.isEmpty() || checkLastName.isEmpty() || checkEmail.isEmpty() ||
                        checkPassword.isEmpty() || checkConfirmPassword.isEmpty()){
                    Toast.makeText(this, "Please check your details", Toast.LENGTH_LONG).show();
                }

                if(checkEmail.matches("^[a-zA-Z0-9.! #$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")){
                    if(checkPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
                        if((checkPassword.equals(checkConfirmPassword))){

                            DataBase db = new DataBase(this);
                            db.addUser(checkFirstName, checkLastName, checkEmail, checkPassword);

                            Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(this, "You will be redirected to login.", Toast.LENGTH_LONG).show();

                            finish();
                        }
                        else{
                            Toast.makeText(this, "Password do not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Password needs to have at least 1 uppercase, 1 lower case, 1 number and at least 8 characters!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Email is not in right format", Toast.LENGTH_SHORT).show();
                }
        }
    }
}