package com.example.mypet3;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Cambia da fragment ad activity
        FloatingActionButton buttAdd = (FloatingActionButton)view.findViewById(R.id.btnAdd);
        buttAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //da frag a frag
                /*
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fr_lay, new AddPetFragment());
                fr.commit();*/

                Intent AddInt = new Intent(getActivity().getApplicationContext(), AddPetActivity.class);
                startActivity(AddInt);
            }
        });
        return view;
    }



}