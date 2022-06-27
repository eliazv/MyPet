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

import DBClass.DBPet;
import DBClass.Pet;

public class AddPetActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    DBPet pushPetInDB;

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

/*
        //pushPetInDB = new DBPet();
        pushPetInDB.add(newPet).addOnSuccessListener(suc -> {
            Toast.makeText(getApplicationContext(), "Pet inserito correttamente.", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }).addOnFailureListener(er -> {
            Toast.makeText(getApplicationContext(), "ERR: " + er.getMessage(), Toast.LENGTH_SHORT).show();
        });*/

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
                        dbRef.child("Pet").child(idPet).child("descr").setValue(descrText);
                        dbRef.child("Pet").child(idPet).child("posiz").setValue(posizText);
                        dbRef.child("Pet").child(idPet).child("proprietario").setValue(loggedUser);

                        Pet newPet = new Pet();//nomeText,descrText,posizText,specieText,loggedUser);
                        newPet.setNome(nomeText);
                        newPet.setDescr(descrText);
                        newPet.setProprietario(loggedUser);
                        newPet.setSpecie(specieText);
                        newPet.setPosiz(posizText);

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