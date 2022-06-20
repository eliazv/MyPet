package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });

        //txt non modificabili inizialmente
        EditText EditName = (EditText) findViewById(R.id.txtPNome);
        EditName.setEnabled(false);

        EditText EditSurname = (EditText) findViewById(R.id.txtPPos);
        EditSurname.setEnabled(false);

        EditText EditEmail = (EditText) findViewById(R.id.txtPSpecie);
        EditEmail.setEnabled(false);

        EditText EditPhone = (EditText) findViewById(R.id.txtPDescr);
        EditPhone.setEnabled(false);

        Button buttEdit = (Button) findViewById(R.id.btnSubmit);
        buttEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //cambia nome tasto
                if(buttEdit.getText()!="Salva"){
                    //TODO salvataggio dei dati
                    buttEdit.setText("Salva");
                    //cambia stato
                    EditName.setEnabled(true);
                    EditSurname.setEnabled(true);
                    EditEmail.setEnabled(true);
                    EditPhone.setEnabled(true);
                }
                else{
                    buttEdit.setText("Modifica");
                    //cambia stato
                    EditName.setEnabled(false);
                    EditSurname.setEnabled(false);
                    EditEmail.setEnabled(false);
                    EditPhone.setEnabled(false);
                }
            }
        });

        Button buttLogin = (Button) findViewById(R.id.btnLogin);
        buttLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent UserInt2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(UserInt2);
            }
        });
    }
}