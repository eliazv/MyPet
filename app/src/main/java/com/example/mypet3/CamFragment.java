package com.example.mypet3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;


public class CamFragment extends Fragment {
        private CodeScanner mCodeScanner;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            final Activity activity = getActivity();
            View view = inflater.inflate(R.layout.fragment_cam, container, false);
            CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(activity, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();

                            //splitta...
                            String[] separated = result.getText().split(";");
                            //da dopo Nome: a prima di ;
                            String nomePet=separated[0].substring(6);
                            //da dopo Proprietario: a prima di ;
                            String nomeUser=separated[1].substring(15);

                            //reinderizza a petFr
                            Context context = view.getContext();
                            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                            if (fm != null) {
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.frame_layout, new PetFragment(nomePet, nomeUser));//TODO passare l'utente
                                ft.commit();
                            }

                            //stampa testo
                            TextView QRText =  view.findViewById(R.id.txtQr);
                            QRText.setText(result.getText());

                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mCodeScanner.startPreview();
        }

        @Override
        public void onPause() {
            mCodeScanner.releaseResources();
            super.onPause();
        }
    }
