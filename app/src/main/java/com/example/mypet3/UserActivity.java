package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);

        mAuth = FirebaseAuth.getInstance();

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //txt non modificabili inizialmente
        EditText EditName = (EditText) findViewById(R.id.tvUsername);
        EditName.setEnabled(false);

        EditText EditSurname = (EditText) findViewById(R.id.txtCognome);
        EditSurname.setEnabled(false);

        EditText EditEmail = (EditText) findViewById(R.id.txtEmail);
        if (user != null) {
            EditEmail.setText(user.getEmail());
        }
        EditEmail.setEnabled(false);

        EditText EditPhone = (EditText) findViewById(R.id.txtTel);
        EditPhone.setEnabled(false);

        //TODO LOGIN->ESCI
        /*
        Button buttLogin = (Button) findViewById(R.id.btnLogin);
        buttLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent UserInt2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(UserInt2);
            }
        });*/

        Button buttLogout = (Button) findViewById(R.id.btnLogout);
        buttLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent UserInt2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(UserInt2);
            }
        });

    }
}