package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DBClass.Pet;

public class PetTabFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference dbRef;
    PetAdapter myAdapter;
    ArrayList<Pet> petList;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton buttAdd = (FloatingActionButton)view.findViewById(R.id.btnAdd);
        if(loggedUser!=""){
            buttAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Da HomeFragment a AddPetActivity
                    startActivity( new Intent(getActivity().getApplicationContext(), PetAddActivity.class));
                }
            });
        }
        else{
            buttAdd.setVisibility(View.GONE);
        }

        recyclerView = view.findViewById(R.id.petRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        petList = new ArrayList<>();
        myAdapter = new PetAdapter(petList,getActivity());
        recyclerView.setAdapter(myAdapter);



        if (petList != null) {
            petList.clear();
        }

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        dbRef.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Pet p = sn.getValue(Pet.class);
                    petList.add(p);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);
        return view;
    }


}