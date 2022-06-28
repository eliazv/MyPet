package DBClass;

public class Pet {
    public String nome;
    public String descrizione;
    public String indirizzo;
    public String specie;
    public String proprietario;
    public String img;


    public Pet(){}

    public String getImg() {  return img;    }
    public void setImg(String img) { this.img = img;   }

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
