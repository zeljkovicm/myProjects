package com.example.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private Button logIn;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {

        email = findViewById(R.id.editTextLoginEmail);
        password = findViewById(R.id.editTextLoginPassword);
        logIn = findViewById(R.id.buttonLogin);
        signUp = findViewById(R.id.textViewSignUpHere);

        logIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignUpHere:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonLogin:
                String checkEmail = email.getText().toString();
                String checkPassword = password.getText().toString();

                if(checkEmail.isEmpty() || checkPassword.isEmpty()){
                    Toast.makeText(this, "Enter your credentials first", Toast.LENGTH_LONG).show();
                }
                else{
                    DataBase db = new DataBase(this);
                    UserModel user = db.getUserByEmail(checkEmail);

                    if(user != null){
                        String password = user.getPassword();
                        if(password.equals(checkPassword)){
                            boolean isLogin = true;
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("isLogin", isLogin); // boolean login statusa
                            editor.putString("email", checkEmail);
                            editor.commit();
                            finish();
                        }
                        else{ // Zbog bezbednosnih razloga ne dajemo smernice sta je greska
                            Toast.makeText(this, "Please check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Please check your credentials", Toast.LENGTH_SHORT).show();
                    }
                }


        }
    }
}