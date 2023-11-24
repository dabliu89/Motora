package com.example.motora.model;

import java.util.HashMap;

public class AvaliacaoResultado {
    String aluno;
    String tituloTeste;
    HashMap<String, String> campos;
    String message;
    String data;

    int image;

    public AvaliacaoResultado(String aluno, String tituloTeste, HashMap<String, String> campos, String message, String data, int image) {
        this.aluno = aluno;
        this.tituloTeste = tituloTeste;
        this.campos = campos;
        this.message = message;
        this.data = data;
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AvaliacaoResultado(){
        super();
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getTituloTeste() {
        return tituloTeste;
    }

    public void setTituloTeste(String tituloTeste) {
        this.tituloTeste = tituloTeste;
    }

    public HashMap<String, String> getCampos() {
        return campos;
    }

    public void setCampos(HashMap<String, String> campos) {
        this.campos = campos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "AvaliacaoResultado{" +
                "aluno='" + aluno + '\'' +
                ", tituloTeste='" + tituloTeste + '\'' +
                ", campos='" + campos + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
