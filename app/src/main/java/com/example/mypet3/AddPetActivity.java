package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mypet3.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DBClass.DBPet;
import DBClass.Pet;

public class AddPetActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText nome, descr, posiz, specie;

    ActivityMainBinding binding;
    Uri imageUri ;
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


        nome = findViewById(R.id.txtNome);
        String nomeText = nome.getText().toString();

        posiz = findViewById(R.id.txtPos);
        String posizText = posiz.getText().toString();

        specie = findViewById(R.id.txtPos);
        String specieText = specie.getText().toString();

        descr = findViewById(R.id.txtDescr);
        String descrText = descr.getText().toString();


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

    public void placeAutocomplete(){
        Places.initialize(getApplicationContext(), "AIzaSyAB5lMHHIXdT8E4jkf7i5rpuoKATAEoYOM");

        posiz.setFocusable(false);
        posiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(AddPetActivity.this);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place= Autocomplete.getPlaceFromIntent(data);
            posiz.setText(place.getAddress());

        }
        else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }
        /*if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.imageView.setImageURI(imageUri);

        }*/
    }


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

}