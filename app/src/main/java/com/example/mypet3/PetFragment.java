package com.example.mypet3;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

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

    public QRGEncoder qrgEncoder;
    ImageView qrIV, petImg;
    StorageReference storageReference;
    FirebaseStorage storage;
    String idPet;
    String nomePet, user;

    public PetFragment(){

    }
    public PetFragment(String idPet){
        this.idPet=idPet;
    }
    //TODO da usare il seguente
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


        //---Set Data

        //prendi dal db
        
        TextView PetNameD =  view.findViewById(R.id.tvNamePetFr);
        PetNameD.setText(idPet);




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