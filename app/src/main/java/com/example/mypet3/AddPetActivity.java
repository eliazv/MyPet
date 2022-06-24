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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPetActivity extends AppCompatActivity {

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });

        Button buttAdd = findViewById(R.id.btnAddPet);
        buttAdd.setOnClickListener(view -> addPet());

        /*
        EditText edit_pnome = findViewById(R.id.tvUsername);
        EditText edit_ppos = findViewById(R.id.txtCognome);
        EditText edit_pspecie = findViewById(R.id.txtEmail);
        EditText edit_pdescr = findViewById(R.id.txtTel);
        Button buttSub = findViewById(R.id.btnRegistr);
        DBPet PetDB = new DBPet();
        buttSub.setOnClickListener(v-> {
            Pet newPet = new Pet(edit_pnome.getText().toString(), edit_pdescr.getText().toString(),
                    edit_ppos.getText().toString(), edit_pspecie.getText().toString());

            PetDB.add(newPet).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Pet inserito correttamente.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });*/


    }

    public void addPet(){
        EditText nome = findViewById(R.id.txtNome);
        String nomeText = nome.getText().toString();

        EditText posiz = findViewById(R.id.txtPos);
        String posizText = posiz.getText().toString();

        EditText specie = findViewById(R.id.txtPos);
        String specieText = specie.getText().toString();

        EditText descr = findViewById(R.id.txtDescr);
        String descrText = descr.getText().toString();

        if(posizText.isEmpty() || nomeText.isEmpty() || specieText.isEmpty()  || descrText.isEmpty()){
            Toast.makeText(getBaseContext(), "Compila tutti i campi.", Toast.LENGTH_SHORT).show();
        } else {
            dbRef.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String idPet= nomeText+" - "+loggedUser;
                    if (snapshot.hasChild(idPet)){
                        Toast.makeText(getBaseContext(), "Hai già un pet con questo nome.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbRef.child("Pet").child(idPet).child("nome").setValue(nomeText);
                        dbRef.child("Pet").child(idPet).child("specie").setValue(specieText);
                        dbRef.child("Pet").child(idPet).child("descrizione").setValue(descrText);
                        dbRef.child("Pet").child(idPet).child("indirizzo").setValue(posizText);
                        dbRef.child("Pet").child(idPet).child("proprietario").setValue(loggedUser);

                        Toast.makeText(getApplicationContext(), "Pet aggiunto con successo!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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