package com.example.retosucsm;

public class Usuario {
    String Nombres;



    String Apellidos;
    String id;
    String Codigo;
    String Correo;
    String Password;
    public Usuario(){}
    public Usuario(String nombres, String apellidos, String id, String codigo, String correo, String password) {
        Nombres = nombres;
        Apellidos = apellidos;
        this.id = id;
        Codigo = codigo;
        Correo = correo;
        Password = password;
    }



    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}