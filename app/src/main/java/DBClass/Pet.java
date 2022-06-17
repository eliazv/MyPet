package DBClass;

public class Pet {
    public String nome;
    public String descr;
    public String posiz;

    Pet(){}
    Pet(String nome, String descr, String posiz){
        this.nome=nome;
        this.descr=descr;
        this.posiz=posiz;
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

    public String getPosiz() {
        return posiz;
    }

    public void setPosiz(String posiz) {
        this.posiz = posiz;
    }
}
