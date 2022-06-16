package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

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
        EditText EditName = (EditText) findViewById(R.id.txtName);
        EditName.setEnabled(false);

        EditText EditSurname = (EditText) findViewById(R.id.txtSurname);
        EditSurname.setEnabled(false);

        EditText EditEmail = (EditText) findViewById(R.id.txtEmail);
        EditEmail.setEnabled(false);

        EditText EditPhone = (EditText) findViewById(R.id.txtPhone);
        EditPhone.setEnabled(false);

        Button buttEdit = (Button) findViewById(R.id.bntEdit);
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
    }
}