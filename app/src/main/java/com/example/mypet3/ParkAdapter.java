package com.example.mypet3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import DBClass.Park;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.MyViewHolder> {

    ArrayList<Park> list;
    ArrayList<Park> fullList;

    Activity activity;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    public ParkAdapter(ArrayList<Park> list, Activity activity) {

        this.list = list;
        this.fullList = new ArrayList<>(list);
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context->parent.getContext()
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_park, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Park currentCardItem = list.get(position);

        //---Per IMG
        storage = FirebaseStorage.getInstance();
        try {
            storageReference = storage.getReference().child(currentCardItem.getImg());//currentCardItem.getNome()+loggedUser
            File file = File.createTempFile(currentCardItem.getNome()+currentCardItem.getProprietario(), "jpeg");//??
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.img.setImageBitmap(bitmap);
            });
        } catch (Exception e){
            e.getMessage();
        }

        holder.nome.setText(currentCardItem.getNome());
        holder.descr.setText(currentCardItem.getDescrizione());
        holder.indirizzo.setText(currentCardItem.getIndirizzo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Park getSelectedPark(int position){
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView descr;
        TextView indirizzo;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.tvParkName);
            descr = itemView.findViewById(R.id.tvParkDescr);
            img = itemView.findViewById(R.id.ivPark);
            indirizzo =  itemView.findViewById(R.id.tvParkPos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    if (fm != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new ParkFragment(nome.getText().toString()));
                        ft.commit();
                    }
                }
            });
        }

    }

    //----Search menu----
    public Filter getFilter() {
        return Searched_Filter;
    }
    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Park> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Park item : fullList) {
                    if (item.getNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
