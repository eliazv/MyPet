package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import DBClass.Pet;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    ArrayList<Pet> list;
    ArrayList<Pet> fullList;

    Activity activity;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    public HomeAdapter( ArrayList<Pet> list, Activity activity) {

        this.list = list;
        this.fullList = new ArrayList<>(list);
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

        /*
        String imagePath = currentCardItem.getImg();
        Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                .getIdentifier(imagePath, "drawable", activity.getPackageName()));
        holder.img.setImageDrawable(drawable);*/

        holder.nome.setText(currentCardItem.getNome());
        holder.descr.setText(currentCardItem.getDescrizione());
        holder.proprietario.setText(currentCardItem.getProprietario());

        switch (currentCardItem.getSpecie()){
            case "Cane":
                holder.icon.setImageResource(R.mipmap.ic_dog_foreground);
                break;
            case "Gatto":
                holder.icon.setImageResource(R.mipmap.ic_cat_foreground);
                break;
            case "Criceto":
                holder.icon.setImageResource(R.mipmap.ic_ham_foreground);
                break;
            case "Cavallo":
                holder.icon.setImageResource(R.mipmap.ic_horse_foreground);
                break;
            case "Uccellino":
                holder.icon.setImageResource(R.mipmap.ic_bird_foreground);
                break;
            case "Coniglio":
                holder.icon.setImageResource(R.mipmap.ic_rabbit_foreground);
                break;
            case "Tartaruga":
                holder.icon.setImageResource(R.mipmap.ic_turtle_foreground);
                break;
            case "Maialino":
                holder.icon.setImageResource(R.mipmap.ic_pig_foreground);
                break;
            case "Pesce":
                holder.icon.setImageResource(R.mipmap.ic_fish2_foreground);
                break;
            default:
                holder.icon.setImageResource(R.mipmap.ic_pig_foreground);
        }
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
        TextView proprietario;
        ImageView img, icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.tvPName);
            descr = itemView.findViewById(R.id.tvPDescr);
            img = itemView.findViewById(R.id.imageViewPet);
            icon = itemView.findViewById(R.id.ivPetIcon);
            proprietario =  itemView.findViewById(R.id.tvPUser);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    if (fm != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new PetFragment(nome.getText().toString(), proprietario.getText().toString()));//TODO passare l'utente
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
            ArrayList<Pet> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pet item : fullList) {
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
