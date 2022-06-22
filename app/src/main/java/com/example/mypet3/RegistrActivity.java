package com.example.mypet3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import DBClass.DBUser;
import DBClass.Pet;
import DBClass.User;


public class RegistrActivity extends AppCompatActivity {



    public boolean regSucc = false;
    FirebaseAuth mAuth;

    public boolean getRegSucc() {
        return regSucc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        mAuth = FirebaseAuth.getInstance();


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
                    //TODO cambia textbox in login
                    regSucc=true;

                    //CreateUser(user.email, user.password);

                    Intent UserInt4 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(UserInt4);
                }).addOnFailureListener(er -> {
                    Toast.makeText(RegistrActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    TextView PetNameD =  findViewById(R.id.txtError);
                    PetNameD.setText("Errore:"+er.getMessage());
                });
            }
            else{
                Toast.makeText(RegistrActivity.this, "Le password non corrispondono.", Toast.LENGTH_SHORT).show();
                TextView PetNameD =  findViewById(R.id.txtError);
                PetNameD.setText("Le password non corrispondono.");
            }
        });
    }

     public void CreateUser(String email, String password){
         mAuth.createUserWithEmailAndPassword(email,password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             //Log.d(TAG, "createUserWithEmail:success");
                             Toast.makeText(RegistrActivity.this,"Registrazione avvenuta con successo.",
                                     Toast.LENGTH_SHORT).show();
                             FirebaseUser user = mAuth.getCurrentUser();
                             //updateUI(user); non va boh
                         } else {
                             //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                             Toast.makeText(RegistrActivity.this,"Authentication failed."+task.getException().getMessage(),
                                     Toast.LENGTH_SHORT).show();
                             //updateUI(user); non va boh
                         }
                     }
                 });
     }
}