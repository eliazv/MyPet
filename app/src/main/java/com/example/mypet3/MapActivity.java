package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);

        Button buttPets = (Button) findViewById(R.id.btnPets);
        buttPets.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent MainInt = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainInt);
            }
        });

        ImageButton buttUser = (ImageButton) findViewById(R.id.btnUser);
        buttUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent UserInt = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(UserInt);
            }
        });
    }

}