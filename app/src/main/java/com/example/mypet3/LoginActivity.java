package com.example.mypet3;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    DatabaseReference dbRef;
    static String loggedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");


        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button buttReg = (Button) findViewById(R.id.btnRegist);
        buttReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrActivity.class));
            }
        });
        Button btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(view -> login());

        Button buttLogin = findViewById(R.id.btnLogin);
        buttLogin.setOnClickListener(view -> login());
               /* new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                TextView uEmail =  findViewById(R.id.txtEmailLog);
                TextView uPass =  findViewById(R.id.txtPassLog);
                String suEmail= (String) uEmail.getText();
                String suPass= (String) uPass.getText();
                //LogUser(suEmail, suPass);
            }
        });*/
    }

    public void LogUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            TextView regAvv =  findViewById(R.id.txtError);
                            regAvv.setText("Errore.");
                            //updateUI(null);
                        }
                    }
                });
    }

    public void login() {
        EditText username = findViewById(R.id.tvUserLog);
        String userText = username.getText().toString();

        EditText password = findViewById(R.id.tvPassLog);
        String pswText = password.getText().toString();

        if (userText.isEmpty() || pswText.isEmpty()) {
            Toast.makeText(getBaseContext(), "Username o password non inseriti.", Toast.LENGTH_SHORT).show();
        } else {
            //prende -N55MsYH_YtheROQ3Y11 non l'username
            dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {//.child("username")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(userText)) {//perche controlla nei child sotto l'username, che non esistono?
                        final String getPsw = snapshot.child(userText).child("password").getValue(String.class);
                        if (getPsw.equals(pswText)) {
                            loggedUser = userText;
                            Toast.makeText(getBaseContext(), "Login effettuato.", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Password non corretta.", Toast.LENGTH_SHORT).show();
                            password.setText("");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Username non corretto.", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}