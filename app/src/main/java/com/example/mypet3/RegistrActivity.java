package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import DBClass.DBUser;
import DBClass.Pet;
import DBClass.User;

public class RegistrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);


        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        EditText edit_unome = findViewById(R.id.txtNome);
        EditText edit_ucogn = findViewById(R.id.txtCognome);
        EditText edit_uemail = findViewById(R.id.txtEmail);
        EditText edit_utel = findViewById(R.id.txtTel);
        EditText edit_upass = findViewById(R.id.txtPass);
        EditText edit_upassconf = findViewById(R.id.txtPassConf);
        Button buttReg = findViewById(R.id.btnRegistr);
        DBUser userdb = new DBUser();
        buttReg.setOnClickListener(v-> {
            if(edit_upass.getText().toString().equals(edit_upassconf.getText().toString())){
                //TODO controllo email duplicate
                User user = new User(edit_unome.getText().toString(), edit_ucogn.getText().toString(),
                        edit_uemail.getText().toString(), edit_upass.getText().toString(), edit_utel.getText().toString());

                userdb.add(user).addOnSuccessListener(suc -> {
                    Toast.makeText(RegistrActivity.this, "Utente inserito correttamente.", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(RegistrActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else{
                Toast.makeText(RegistrActivity.this, "Le password non corrispondono.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}