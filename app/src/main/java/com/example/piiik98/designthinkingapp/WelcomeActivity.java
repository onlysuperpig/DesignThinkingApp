package com.example.piiik98.designthinkingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView house, friend;
    private TextView usernameText;
    private DatabaseReference mDatabase;
    private Bundle bundle;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        house = findViewById(R.id.house);
        friend = findViewById(R.id.friend);
        usernameText = findViewById(R.id.usernameText);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("user").child(getIntent().getExtras().getString("user"));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.v("tmz",""+ ds.getKey()); //displays the key for the node
                    Log.v("tmz",""+ ds.child("username").getValue());   //gives the value for given keyname
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                usernameText.setText("Hello " + user.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent (WelcomeActivity.this, InstantViewingActivity.class );
                startActivity(intent);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (WelcomeActivity.this, ClosePersonActivity.class);
                startActivity(intent);
            }
        });



//        usernameText.setText("Hello " + mDatabase.child("user").child(bundle.getString("user")).toString());
    }
}
