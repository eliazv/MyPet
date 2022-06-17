package DBClass;

public class Pet {
    public String nome;
    public String descr;
    public String posiz;
    public String specie;

    Pet(){}
    public Pet(String nome, String descr, String posiz, String specie){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
        this.specie=specie;
    }

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

    public String getSpecie() {
        return specie;
    }

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
