package com.salimuddin.signupandloginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    //Declare Firebase Obj
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Idealize Firebase
        mAuth = FirebaseAuth.getInstance();

    }


    //menu Creating
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


   //menu listener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if (item.getItemId()==R.id.signOutMennt){

            //Do sign out
            FirebaseAuth.getInstance().signOut();
            //finish Home activity
            finish();

            //Going to home activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }
}
