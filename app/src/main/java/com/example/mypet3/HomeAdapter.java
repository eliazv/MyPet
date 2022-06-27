package com.example.mypet3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DBClass.Pet;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    ArrayList<Pet> list;

    public HomeAdapter(Context context, ArrayList<Pet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context->parent.getContext()
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pet currentCardItem = list.get(position);
        holder.nome.setText(currentCardItem.getNome());//
        holder.descr.setText(currentCardItem.getDescrizione());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Pet getSelectedPet(int position){
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView descr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.tvPName);
            descr = itemView.findViewById(R.id.tvPDescr);
        }

    }
}
