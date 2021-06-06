package com.example.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseUser;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    Button login, register;
    DatabaseReference reference;
    private final static String TAG =  StartActivity.class.getSimpleName();

   FirebaseUser firebaseUser;

   protected void onStart(){
       super.onStart();

       firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       Log.d(TAG,"Current user is: "  + firebaseUser);
       if(firebaseUser != null) {
           reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()) {
                       Log.d(TAG, "Get in");
                       Intent intent = new Intent(StartActivity.this, MainActivity.class);
                       startActivity(intent);
                       finish();
                   } else {
                       Log.d(TAG, "this child not exist!!! On start activity");
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        // check the user is null

        /*
       if (firebaseUser != null){
            Log.d(TAG,"Get in"  );
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
          }
          else{
           Log.d(TAG," Do not get in"  );
       }

*/

        login = findViewById(R.id.login);
        register = findViewById((R.id.register));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
            }
        });
    }
}
