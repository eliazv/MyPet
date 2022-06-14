package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Button btnMap = (Button) findViewById(R.id.button);
        btnMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                setContentView(R.layout.act_map);
            }
        }); */
    }
}