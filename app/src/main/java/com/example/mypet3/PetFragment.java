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
    ImageView qrIV;
    StorageReference storageReference;
    FirebaseStorage storage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pet, container, false);


        //imageView
        /*
        String fileName = idPet;
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName+ ".jpeg");

        storage = FirebaseStorage.getInstance();

        imgPet = findViewById(R.id.imgProfilePhoto);
        try {
            storageReference = storage.getReference().child("image/" + LoginActivity.loggedUser + ".jpeg");

            File file = File.createTempFile(LoginActivity.loggedUser, "jpeg");
            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imgPet.setImageBitmap(bitmap);
            });
        } catch (Exception e){
            e.getMessage();
        }*/



        //----------------QRCode
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