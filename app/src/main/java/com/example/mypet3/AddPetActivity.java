package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

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

        EditText edit_pnome = findViewById(R.id.txtPNome);
        EditText edit_ppos = findViewById(R.id.txtPPos);
        EditText edit_pspecie = findViewById(R.id.txtPSpecie);
        EditText edit_pdescr = findViewById(R.id.txtPDescr);
        Button buttSub = findViewById(R.id.btnSubmit);
        DBPet PetDB = new DBPet();
        buttSub.setOnClickListener(v-> {
            Pet bestia = new Pet(edit_pnome.getText().toString(), edit_pdescr.getText().toString(),
                    edit_ppos.getText().toString(), edit_pspecie.getText().toString());

            PetDB.add(bestia).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Pet inserito correttamente.", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}