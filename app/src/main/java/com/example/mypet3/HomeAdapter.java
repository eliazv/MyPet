package com.example.mypet3;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DBClass.Pet;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    ArrayList<Pet> list;

    Activity activity;

    public HomeAdapter( ArrayList<Pet> list, Activity activity) {

        this.list = list;
        this.activity=activity;
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
        /*
        String imagePath = currentCardItem.getImg();
        Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                .getIdentifier(imagePath, "drawable", activity.getPackageName()));
        holder.img.setImageDrawable(drawable);*/
        holder.nome.setText(currentCardItem.getNome());
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
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.tvPName);
            descr = itemView.findViewById(R.id.tvPDescr);
            img = itemView.findViewById(R.id.imageViewPet);
        }

    }
}
