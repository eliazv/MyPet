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



public class RegistrActivity extends AppCompatActivity {

    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");


        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        Button buttReg = findViewById(R.id.btnRegistr);
        buttReg.setOnClickListener(view -> register());
        /*
        DBUser userdb = new DBUser();
        buttReg.setOnClickListener(v-> {
            if(edit_upass.getText().toString().equals(edit_upassconf.getText().toString())){
                //TODO controllo email duplicate
                User user = new User(edit_uUser.getText().toString(),
                        edit_uemail.getText().toString(), edit_upass.getText().toString(), edit_utel.getText().toString());

                userdb.add(user).addOnSuccessListener(suc -> {
                    Toast.makeText(RegistrActivity.this, "Utente inserito correttamente.", Toast.LENGTH_SHORT).show();


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
        });*/
    }


    public void register(){
        EditText username = findViewById(R.id.tvUsername);
        String userText = username.getText().toString();

        EditText password = findViewById(R.id.txtPass);
        String pswText = password.getText().toString();

        EditText tel = findViewById(R.id.txtTel);
        String telText = password.getText().toString();

        EditText email = findViewById(R.id.txtEmail);
        String emailText = email.getText().toString();

        EditText passconf = findViewById(R.id.txtPassConf);
        String passconfText = passconf.getText().toString();

        if(userText.isEmpty() || pswText.isEmpty() || emailText.isEmpty() || passconfText.isEmpty() || telText.isEmpty()){
            Toast.makeText(getBaseContext(), "Compila tutti i campi.", Toast.LENGTH_SHORT).show();
        } else {
            dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(userText)){
                        Toast.makeText(getBaseContext(), "Username già esistente.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!passconfText.equals(pswText)) {
                        Toast.makeText(getBaseContext(), "Le password non corrispondono.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbRef.child("User").child(userText).child("password").setValue(pswText);
                        dbRef.child("User").child(userText).child("email").setValue(emailText);
                        dbRef.child("User").child(userText).child("telefono").setValue(telText);

                        Toast.makeText(getApplicationContext(), "Account creato con successo!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistrActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}