package DBClass;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Pet {
    public String nome;
    public String descr;
    public String posiz;
    public String specie;
    public String propr;
    public Image imgPet;
    public QRGEncoder qrgEncoder;





    Pet(){}
    //senza user email
    public Pet(String nome, String descr, String posiz, String specie){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
        this.specie=specie;
    }


    public Pet(String nome, String descr, String posiz, String specie, String propr){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
        this.specie=specie;
        this.propr=propr;
    }
/*
    public Pet(String nome, String descr, String posiz, String specie, User user){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
        this.specie=specie;
        this.proprietario=user;
        String qrdata= "Nome: "+nome+", Proprietario: "+proprietario.username
                +", Telefono: "+proprietario.telefono+", Abitazione: "+posiz+", Descrizione: "+descr;
        this.qrgEncoder = new QRGEncoder(qrdata,null, QRGContents.Type.TEXT, 10);
    }*/



    public String getPropr() {return propr;}

    public void setPropr(String email) {this.propr = email;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getSpecie() {return specie;}

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getPosiz() {
        return posiz;
    }

    public void setPosiz(String posiz) {
        this.posiz = posiz;
    }
}
