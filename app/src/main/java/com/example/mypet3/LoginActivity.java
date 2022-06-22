package com.example.mypet3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica activity ma la precedente
                onBackPressed();
            }
        });

        Button buttReg = (Button) findViewById(R.id.btnRegist);
        buttReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent UserInt3 = new Intent(getApplicationContext(), RegistrActivity.class);
                startActivity(UserInt3);
            }
        });

        Button buttLogin = (Button) findViewById(R.id.btnLogin);
        buttLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                TextView uEmail =  findViewById(R.id.txtEmailLog);
                TextView uPass =  findViewById(R.id.txtPassLog);
                String suEmail= (String) uEmail.getText();
                String suPass= (String) uPass.getText();
                //LogUser(suEmail, suPass);*/
            }
        });
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
}