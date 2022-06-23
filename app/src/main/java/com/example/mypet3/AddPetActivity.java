package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import DBClass.DBPet;
import DBClass.Pet;

public class AddPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });

        EditText edit_pnome = findViewById(R.id.txtNome);
        EditText edit_ppos = findViewById(R.id.txtCognome);
        EditText edit_pspecie = findViewById(R.id.txtEmail);
        EditText edit_pdescr = findViewById(R.id.txtTel);
        Button buttSub = findViewById(R.id.btnRegistr);
        DBPet PetDB = new DBPet();
        buttSub.setOnClickListener(v-> {
            Pet bestia = new Pet(edit_pnome.getText().toString(), edit_pdescr.getText().toString(),
                    edit_ppos.getText().toString(), edit_pspecie.getText().toString());

            PetDB.add(bestia).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Pet inserito correttamente.", Toast.LENGTH_SHORT).show();
                Intent HomeFr = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(HomeFr);
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });


    }
}