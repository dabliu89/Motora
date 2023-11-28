package com.example.motora.model;

import java.util.HashMap;
import java.util.Map;

public class AvaliacaoResultado {
    String aluno;
    String titulo;
    String tipo;
    HashMap<String, String> campos;
    String message;
    String data;
    String id;

    String professor;

    int image;

    public AvaliacaoResultado(String aluno, String titulo, HashMap<String, String> campos, String message, String data, int image) {
        this.aluno = aluno;
        this.titulo = titulo;
        this.campos = campos;
        this.message = message;
        this.data = data;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipoTeste) {
        this.tipo = tipoTeste;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String tituloTeste) {
        this.titulo = tituloTeste;
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "AvaliacaoResultado{" +
                "aluno='" + aluno + '\'' +
                ", tituloTeste='" + titulo + '\'' +
                ", campos='" + campos + '\'' +
                ", message='" + message + '\'' +
                ", tipoTeste='" + tipo + '\'' +
                ", professor='" + professor + '\'' +
                '}';
    }

    public static AvaliacaoResultado stringToObject(String string){
        HashMap<String, String> map = new HashMap<>();
        AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();

        string=string.replace("=", " ");
        string=string.replace("AvaliacaoResultado{", "");
        string=string.replace("}", "");
        String[] split = string.split("'");

        avaliacaoResultado.setAluno(split[1]);
        avaliacaoResultado.setTitulo(split[3]);
        avaliacaoResultado.setMessage(split[7]);
        avaliacaoResultado.setTipo(split[9]);
        avaliacaoResultado.setProfessor(split[11]);

        return avaliacaoResultado;
    }
}
