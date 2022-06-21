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
        View v = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pet pipi = list.get(position);
        holder.nome.setText(pipi.getNome());
        holder.descr.setText(pipi.getDescr());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, descr; //+ image
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.lblPName);
            descr = itemView.findViewById(R.id.txtDescr1);
        }

    }
}
