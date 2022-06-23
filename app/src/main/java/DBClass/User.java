package DBClass;

public class User {
    public String username;
    public String email;
    public String password;
    public String telefono;
    //foto

    User(){}
    public User(String username, String email, String password, String telefono){
        this.username=username;
        this.email=email;
        this.password=password;
        this.telefono=telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nome) {
        this.username = nome;
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
