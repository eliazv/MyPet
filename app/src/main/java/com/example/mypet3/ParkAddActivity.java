package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet3.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class ParkAddActivity extends AppCompatActivity {
    private static final int PERMISSION_CAMERA = 1;
    private static final int PERMISSION_GALLERY = 2;

    DatabaseReference dbRef;
    EditText nome, descr, posiz;
    ImageView imgPark;
    String idPark, specieD;

    Uri imageUri ;
    StorageReference storageReference;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_add);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myPark---android-app-default-rtdb.firebaseio.com/");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ImageButton buttClose = (ImageButton) findViewById(R.id.btnClose);
        buttClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deve tornare in dietro, non una specifica acrtivity ma la precedente
                onBackPressed();
            }
        });


        nome = findViewById(R.id.txtNomePark);
        String nomeText = nome.getText().toString();

        posiz = findViewById(R.id.txtPosPark);
        String posizText = posiz.getText().toString();

        descr = findViewById(R.id.txtDescrPark);
        String descrText = descr.getText().toString();

        imgPark =findViewById(R.id.imgParkAdd);

        Button btnSelectPhoto = findViewById(R.id.btnAddParkImage);
        btnSelectPhoto.setOnClickListener(v -> selectPhoto());

        Button buttAdd = findViewById(R.id.btnAddPark);
        buttAdd.setOnClickListener(view -> addPark());



    }

    public void addPark(){
        EditText nome = findViewById(R.id.txtNomePark);
        String nomeText = nome.getText().toString();

        EditText posiz = findViewById(R.id.txtPosPark);
        String posizText = posiz.getText().toString();

        EditText descr = findViewById(R.id.txtDescrPark);
        String descrText = descr.getText().toString();


        if(posizText.isEmpty() || nomeText.isEmpty() || descrText.isEmpty()){
            Toast.makeText(getBaseContext(), "Compila tutti i campi.", Toast.LENGTH_SHORT).show();
        } else {
            dbRef.child("Park").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    idPark= nomeText;
                    if (snapshot.hasChild(idPark)){
                        Toast.makeText(getBaseContext(), "Hai già un Park con questo nome.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbRef.child("Park").child(idPark).child("nome").setValue(nomeText);
                        dbRef.child("Park").child(idPark).child("descrizione").setValue(descrText);
                        dbRef.child("Park").child(idPark).child("indirizzo").setValue(posizText);
                        dbRef.child("Park").child(idPark).child("img").setValue("park/" + nome.getText().toString() + ".jpeg");
                        uploadPicture();

                        Toast.makeText(getApplicationContext(), "Park aggiunto con successo!", Toast.LENGTH_SHORT).show();
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


    private void requestPermissionPhoto(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
    }

    private void requestPermissionGallery(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GALLERY);
    }

    private void selectPhoto() {
        String options[] = {"Camera", "Galleria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleziona foto da: ");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionPhoto();
                } else {
                    pickFromCamera();
                }
            } else if (which == 1) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionGallery();
                } else {
                    pickFromGallery();
                }
            }
        });
        builder.create().show();
    }

    private void pickFromCamera(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, PERMISSION_CAMERA);
    }

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PERMISSION_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PERMISSION_GALLERY) {
                imageUri = data.getData();
                imgPark.setImageURI(imageUri);

            }
            if (requestCode == PERMISSION_CAMERA) {
                Bundle photo = data.getExtras();
                Bitmap bitmap = (Bitmap) photo.get("data");
                imgPark.setImageBitmap(bitmap);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null);

                imageUri = Uri.parse(path);
            }
        }
    }

    private void uploadPicture() {
        StorageReference riversRef = storageReference.child("park/" + nome.getText().toString() + ".jpeg");
        riversRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {})
                .addOnFailureListener(e -> {});
    }

}