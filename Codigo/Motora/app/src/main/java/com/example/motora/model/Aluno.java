package com.example.motora.model;

public class Aluno  extends Usuario{

    private static int CONTADOR = 0;

    private String uid;
    private String nome;
    private String email;
    private String senha;


    public Aluno(){}

    public Aluno(String id, String nome, String senha) {
        this.uid = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String id) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + uid +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}