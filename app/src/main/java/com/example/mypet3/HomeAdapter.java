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

    Activity activity;

    private FirebaseStorage storage;
    private StorageReference storageReference;

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

        storage = FirebaseStorage.getInstance();
        //storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mypet---android-app.appspot.com/");
        try {
            storageReference = storage.getReference().child("image/" + "nuovoe" + ".jpeg");//currentCardItem.getNome()+loggedUser
            //storageReference = storageReference.child("image/" + currentCardItem.getNome()+loggedUser + ".jpeg");
            File file = File.createTempFile(LoginActivity.loggedUser, "jpeg");
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.img.setImageBitmap(bitmap);
            });
        } catch (Exception e){
            e.getMessage();
        }

        //Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                //.getIdentifier(imagePath, "drawable", activity.getPackageName()));
        //holder.img.setImageDrawable(drawable);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    if (fm != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new PetFragment());
                        ft.commit();
                    }
                }
            });
        }

    }
}
