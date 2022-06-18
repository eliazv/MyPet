package com.example.mypet3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DBClass.Pet;

public class HomeFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //da riempire
        ArrayList<Pet> listaPet = new ArrayList<>();

        Query getPetData = FirebaseDatabase.getInstance().getReference("Pet");
        Query getPetData2 = FirebaseDatabase.getInstance().getReference().child("Pet");
        Query getPetName = getPetData.orderByChild("Nome");

        getPetName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Pet p = sn.getValue(Pet.class);
                    //aggiungi a lista?
                    listaPet.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        //TextView PetName = (TextView) getView().findViewById(R.id.info_text);
        //PetName.setText("a");

        return view;
    }



}