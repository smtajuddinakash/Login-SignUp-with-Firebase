package com.salimuddin.signupandloginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    private EditText SignupmailEditText,SignuppassEditText;
    private Button susignUp, susignIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up");

        //Initialize Fire Base
        mAuth = FirebaseAuth.getInstance();

        //Initialize Progress bar
        progressBar = findViewById(R.id.progressid);

        //Initialize Edit Text
        SignupmailEditText = findViewById(R.id.suemailTextId);
        SignuppassEditText = findViewById(R.id.supasswordTextId);

        //Initialize Buttons
        susignIn = findViewById(R.id.loginBtnId);
        susignUp = findViewById(R.id.createBtnId);

        //Set Listener to Buttons
        susignUp.setOnClickListener(this);
        susignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            //Start login Activity
            case R.id.loginBtnId:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            case R.id.createBtnId:

                //Calling userRegister Method
                userRegister();

                //Clear Edit Text
                SignuppassEditText.setText("");
                SignupmailEditText.setText("");
                break;
        }

    }

    private void userRegister() {

        //Convert TextEditor to String

        String email = SignupmailEditText.getText().toString().trim();
        String password = SignuppassEditText.getText().toString().trim();


        //Checking email is Empty | if email is empty give error
        if (email.isEmpty()){
            SignupmailEditText.setError("Enter A email address ");
            SignupmailEditText.requestFocus();
            return;
        }

        //Checking email Format | if email format not match it gives error
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SignupmailEditText.setError("Enter a valid email address ");
            SignupmailEditText.requestFocus();
            return;
        }
        //Checking password is Empty | if password is empty give error
        if (password.isEmpty()){
            SignuppassEditText.setError("Enter a password");
            SignuppassEditText.requestFocus();
            return;
        }

        //If password is less then 6 it's give error
        if (password.length()<6){
            SignuppassEditText.setError("Minimum length Should be 6");
            SignuppassEditText.requestFocus();
            return;
        }

        //Visible progressbar
        progressBar.setVisibility(View.VISIBLE);

        //FireBase Create User With email and pass
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "User is already Register", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "ERROR: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}
