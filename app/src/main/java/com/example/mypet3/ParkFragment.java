package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.ArrayList;

import DBClass.Park;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ParkFragment extends Fragment {


    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference dbRef;

    ImageView qrIV, ParkImg;
    TextView descr, casa;
    String nomePark, getCasa, getDescr, getImg, qrText;

    public ParkFragment(){}

    public ParkFragment(String nomePark){
        this.nomePark=nomePark;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_park, container, false);


        //-----Set Data
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myPark---android-app-default-rtdb.firebaseio.com/");
        dbRef.child("Park").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(nomePark)) {
                    getDescr = snapshot.child(nomePark).child("descrizione").getValue(String.class);
                    descr =  view.findViewById(R.id.tvDescrParkFr);
                    descr.setText(getDescr);


                    //----indirizzo
                    getCasa = snapshot.child(nomePark).child("indirizzo").getValue(String.class);
                    casa =  view.findViewById(R.id.tvIndirParkFr);
                    casa.setText(getCasa);
                    casa.setPaintFlags(casa.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    casa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fm = getFragmentManager();
                            if (fm != null) {
                                FragmentTransaction ft = fm.beginTransaction();
                                //Park p = snapshot.getValue(Park.class);
                                ft.replace(R.id.frame_layout, new MapFragment(getCasa));
                                ft.commit();
                            }
                        }
                    });


                    //-----QRCode
                    qrIV = view.findViewById(R.id.qrimg);
                    qrText ="Nome: "+nomePark+"; Indirizzo: "+ getCasa +"; Descrizione: "+ getDescr+".";
                    MultiFormatWriter mWriter = new MultiFormatWriter();

                    try {
                        BitMatrix mMatrix = mWriter.encode(qrText, BarcodeFormat.QR_CODE, 400,400);
                        BarcodeEncoder mEncoder = new BarcodeEncoder();
                        Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                        qrIV.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                    //----IMG
                    getImg = snapshot.child(nomePark).child("img").getValue(String.class);
                    storage = FirebaseStorage.getInstance();
                    ParkImg = view.findViewById(R.id.imgParkProfile);
                    try {
                        storageReference = storage.getReference().child(getImg);
                        File file = File.createTempFile(nomePark, "jpeg");
                        storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            ParkImg.setImageBitmap(bitmap);
                        });
                    } catch (Exception e){
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(getContext(), "Utente non trovato.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        TextView ParkNameD =  view.findViewById(R.id.tvNameParkFr);
        ParkNameD.setText(nomePark);


        //-----Set imageView
        storage = FirebaseStorage.getInstance();
        ParkImg = view.findViewById(R.id.imgParkProfile);
        try {
            storageReference = storage.getReference().child("park/" + "nuovoe" + ".jpeg");//TODO nuovoe????

            File file = File.createTempFile(LoginActivity.loggedUser, "jpeg");
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ParkImg.setImageBitmap(bitmap);
            });
        } catch (Exception e){
            e.getMessage();
        }



        //----to Map
        TextView addr =  view.findViewById(R.id.tvIndirParkFr);
        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                if (fm != null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, new ParkFragment(nomePark));
                    ft.commit();
                }
            }
        });


        return view;
    }

}