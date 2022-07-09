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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
    static PetAdapter petAdapter;
    ArrayList<Pet> petList;
    ArrayList<Pet> petFiltered;


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
        petAdapter = new PetAdapter(petList,getActivity());
        recyclerView.setAdapter(petAdapter);



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
                petAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        MenuItem searchItem = getActivity().findViewById(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                petFiltered = new ArrayList<>();
                for (Pet item : petList){
                    if(item.getNome().toLowerCase().contains(query.toLowerCase())){
                        petFiltered.add(item);
                    }
                }
                //AllMuseumCardAdapter adapterMuseumFiltered = new AllMuseumCardAdapter(listenerMuseum, museumFiltered, getActivity());
                //recyclerViewMuseum.setAdapter(adapterMuseumFiltered);
                //adapterMuseumFiltered.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("newText",newText);
                //petAdapter.getFilter().filter(newText);
                petFiltered = new ArrayList<>();
                for (Pet item : petList){
                    if(item.getNome().toLowerCase().contains(newText.toLowerCase())){
                        petFiltered.add(item);
                    }
                }
                //AllMuseumCardAdapter adapterMuseumFiltered = new AllMuseumCardAdapter(listenerMuseum, museumFiltered, getActivity());
                //recyclerViewMuseum.setAdapter(adapterMuseumFiltered);
                //adapterMuseumFiltered.notifyDataSetChanged();

                if(newText == ""){
                    recyclerView.setAdapter(petAdapter);
                    petAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            recyclerView.setAdapter(petAdapter);
            petAdapter.notifyDataSetChanged();
            return false;
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);
        return view;
    }




}