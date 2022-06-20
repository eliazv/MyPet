package DBClass;

public class User {
    public String nome;
    public String cognome;
    public String email;
    public String password;
    public String telefono;
    //foto

    User(){}
    public User(String nome, String cognome, String email, String password, String telefono){
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.telefono=telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
