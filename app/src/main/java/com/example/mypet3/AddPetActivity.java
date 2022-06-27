package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet3.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import DBClass.DBPet;
import DBClass.Pet;

public class AddPetActivity extends AppCompatActivity {

    DatabaseReference dbRef;

    ActivityMainBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });


        Button buttImg =  findViewById(R.id.btnAddPetImage);
        buttImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //selectImage();

                //uploadImage();//non qui ma in aggiungi
            }
        });


        Button buttAdd = findViewById(R.id.btnAddPet);
        buttAdd.setOnClickListener(view -> addPet());

    }

    public void addPet(){
        EditText nome = findViewById(R.id.txtNome);
        String nomeText = nome.getText().toString();

        EditText posiz = findViewById(R.id.txtPos);
        String posizText = posiz.getText().toString();

        EditText specie = findViewById(R.id.txtPos);
        String specieText = specie.getText().toString();

        EditText descr = findViewById(R.id.txtDescr);
        String descrText = descr.getText().toString();

/*
        //pushPetInDB = new DBPet();
        pushPetInDB.add(newPet).addOnSuccessListener(suc -> {
            Toast.makeText(getApplicationContext(), "Pet inserito correttamente.", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }).addOnFailureListener(er -> {
            Toast.makeText(getApplicationContext(), "ERR: " + er.getMessage(), Toast.LENGTH_SHORT).show();
        });*/

        if(posizText.isEmpty() || nomeText.isEmpty() || specieText.isEmpty()  || descrText.isEmpty()){
            Toast.makeText(getBaseContext(), "Compila tutti i campi.", Toast.LENGTH_SHORT).show();
        } else {
            dbRef.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String idPet= nomeText+" - "+loggedUser;
                    if (snapshot.hasChild(idPet)){
                        Toast.makeText(getBaseContext(), "Hai già un pet con questo nome.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbRef.child("Pet").child(idPet).child("nome").setValue(nomeText);
                        dbRef.child("Pet").child(idPet).child("specie").setValue(specieText);
                        dbRef.child("Pet").child(idPet).child("descrizione").setValue(descrText);
                        dbRef.child("Pet").child(idPet).child("indirizzo").setValue(posizText);
                        dbRef.child("Pet").child(idPet).child("proprietario").setValue(loggedUser);

                        Pet newPet = new Pet();//nomeText,descrText,posizText,specieText,loggedUser);
                        newPet.setNome(nomeText);
                        newPet.setDescrizione(descrText);
                        newPet.setProprietario(loggedUser);
                        newPet.setSpecie(specieText);
                        newPet.setIndirizzo(posizText);

                        Toast.makeText(getApplicationContext(), "Pet aggiunto con successo!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
/*
    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ITALY);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        //textview
        //TextView PetNameD =  findViewById(R.id.tvImageName);
        //PetNameD.setText(fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //binding.firebaseimage.setImageURI(null);
                        //binding.imageView4.setImageURI(null);
                        Toast.makeText(getApplicationContext(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to Upload",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            //binding.imageView4.setImageURI(imageUri);

        }
    }*/
}