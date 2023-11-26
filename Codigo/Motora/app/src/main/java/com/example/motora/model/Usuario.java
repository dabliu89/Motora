package com.example.motora.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;


public class Usuario {
    @Exclude
    private String key;
    private String nome, email, senha, papel, idade, genero, nomeProfRes;
    private String id;

    public Usuario(int id, String nome, String email, String senha, String papel, String idade, String genero, String nomeProfRes) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
        this.idade = idade;
        this.genero = genero;
        this.nomeProfRes = nomeProfRes;
    }

    public Usuario() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() { return nome; }

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

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNomeProfRes() {
        return nomeProfRes;
    }

    public void setNomeProfRes(String nomeProfRes) {
        this.nomeProfRes = nomeProfRes;
    }
}
