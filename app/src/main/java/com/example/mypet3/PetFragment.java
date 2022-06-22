package com.example.mypet3;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PetFragment extends Fragment {

    //public QRGEncoder qrgEncoder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
        //View view = inflater.inflate(R.layout.fragment_pet, container, false);

        ImageView imgqr= view.findViewById(R.id.qrimg);

        qrgEncoder = new QRGEncoder("ciao",null, QRGContents.Type.TEXT, 10);
        //Bitmap bitmap = qrgEncoder.getBitmap();
        //imgqr.setImageBitmap(bitmap);*/


        return inflater.inflate(R.layout.fragment_pet, container, false);
    }
}