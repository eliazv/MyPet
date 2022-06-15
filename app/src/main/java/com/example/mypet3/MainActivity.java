package com.example.mypet3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mypet3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//R.layout.activity_main);
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener( item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.camera:
                    replaceFragment(new CamFragment());
                    break;
            }

            return true;
        });


        Button buttMap = (Button) findViewById(R.id.btnMap);
        buttMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.act_map);
                Intent MapInt = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(MapInt);
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


        ImageButton buttCam = (ImageButton) findViewById(R.id.btnCam);
        buttCam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent CamInt = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(CamInt);
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}