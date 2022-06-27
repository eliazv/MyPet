package DBClass;

public class Pet {
    public String nome;
    public String descrizione;
    public String indirizzo;
    public String specie;
    public String proprietario;
    //public Image imgPet;
    //public QRGEncoder qrgEncoder;


 public Pet(){}


/*
  public Pet(String nome, String descr, String posiz, String specie, String propr){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
        this.specie=specie;
        this.proprietario =propr;
    }

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



    public String getProprietario() {return proprietario;}

    public void setProprietario(String email) {this.proprietario = email;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getSpecie() {return specie;}

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
