package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PetFragment extends Fragment {


    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference dbRef;

    ImageView qrIV, petImg;
    String nomePet, user="e";

    public PetFragment(){}

    public PetFragment(String nomePet, String user){
        this.nomePet=nomePet;
        this.user=user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pet, container, false);


        //-----Set Data
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");
        dbRef.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(nomePet+" - "+user)) {//TODO metti user al posto di e
                    String getDescr = snapshot.child(nomePet+" - "+user).child("descrizione").getValue(String.class);
                    TextView descr =  view.findViewById(R.id.tvDescrPetFr);
                    descr.setText(getDescr);

                    String getCasa = snapshot.child(nomePet+" - "+user).child("indirizzo").getValue(String.class);
                    TextView casa =  view.findViewById(R.id.tvCasaPetFr);
                    casa.setText(getCasa);

                    String getImg = snapshot.child(nomePet+" - "+user).child("img").getValue(String.class);
                    //TODO prendere da storage e settare img

                } else {
                    Toast.makeText(getContext(), "Utente non trovato.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(user)) {
                    String getTel = snapshot.child(user).child("telefono").getValue(String.class);
                    TextView telefono =  view.findViewById(R.id.tvTelPetFr);
                    telefono.setText(getTel);
                } else {
                    Toast.makeText(getContext(), "Utente non trovato.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView PetNameD =  view.findViewById(R.id.tvNamePetFr);
        PetNameD.setText(nomePet);

        TextView tvUser =  view.findViewById(R.id.tvPadronePetFr);
        tvUser.setText(user);




        //-----Set imageView
        storage = FirebaseStorage.getInstance();
        petImg = view.findViewById(R.id.imgPetProfile);
        try {
            storageReference = storage.getReference().child("image/" + "nuovoe" + ".jpeg");

            File file = File.createTempFile(LoginActivity.loggedUser, "jpeg");
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                petImg.setImageBitmap(bitmap);
            });
        } catch (Exception e){
            e.getMessage();
        }



        //-------QRCode
        qrIV = view.findViewById(R.id.qrimg);
        String myText ="ciao";
        MultiFormatWriter mWriter = new MultiFormatWriter();

        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            qrIV.setImageBitmap(mBitmap);//Setting generated QR code to imageView
        } catch (WriterException e) {
            e.printStackTrace();
        }


        return view;
    }



}