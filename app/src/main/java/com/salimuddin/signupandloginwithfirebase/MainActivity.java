package com.salimuddin.signupandloginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mailEditText,passEditText;
    private Button signUp, signIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        this.setTitle("Sign In");

        //Initialize Edit Text
        mailEditText = findViewById(R.id.emailTextId);
        passEditText = findViewById(R.id.passwordTextId);

        //Initialize Progress bar
        progressBar = findViewById(R.id.progressid);

        //Initialize Buttons
        signIn = findViewById(R.id.signinBtnId);
        signUp = findViewById(R.id.signupBtnId);

        //Set Listener to Buttons
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signinBtnId:

                //Calling userLogin Method

                userLogin();
                break;
            case R.id.signupBtnId:
                //Start Sign Up Activity
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);

                break;
        }

    }

    private void userLogin() {

        //Convert TextEditor to String

        String email = mailEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        //Checking email is Empty | if email is empty give error
        if (email.isEmpty()){
            mailEditText.setError("Enter A email address ");
            mailEditText.requestFocus();
            return;
        }
        //Checking email Format | if email format not match it gives error
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mailEditText.setError("Enter a valid email address ");
            mailEditText.requestFocus();
            return;
        }
        //Checking password is Empty | if password is empty give error
        if (password.isEmpty()){
            passEditText.setError("Enter a password");
            passEditText.requestFocus();
            return;
        }
        //If password is less then 6 it's give error
        if (password.length()<6){
            passEditText.setError("Minimum length Should be 6");
            passEditText.requestFocus();
            return;
        }

        //Visible progressbar
        progressBar.setVisibility(View.VISIBLE);

        //FireBase Sign In With email and pass

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
