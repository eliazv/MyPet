package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    String getEmail="";
    String getTel="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                onBackPressed();
            }
        });


        Button buttLogout = (Button) findViewById(R.id.btnLogout);
        buttLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loggedUser = "";
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        TextView Username =  findViewById(R.id.tvUsername);
        Username.setText(loggedUser);

        dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(loggedUser)) {
                    getEmail = snapshot.child(loggedUser).child("email").getValue(String.class);
                    TextView email =  findViewById(R.id.tvEmail);
                    email.setText(getEmail);

                    getTel = snapshot.child(loggedUser).child("telefono").getValue(String.class);
                    TextView Tel =  findViewById(R.id.tvTel);
                    Tel.setText(getTel);

                } else {
                    Toast.makeText(getBaseContext(), "Utente non trovato.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}