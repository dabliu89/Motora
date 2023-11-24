package com.example.motora.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Teste {

    private String id;
    private String tipo;
    private String titulo;
    private Map<String, String> campos;

    private String video;

    public Teste(){super();}

    public Teste(String tipo, String titulo) {
        this.tipo = tipo;
        this.titulo = titulo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }

    @Override
    public String toString(){
        return titulo;
    }

    public String toStringAll() {
        return "Teste{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", campos=" + campos +
                '}';
    }

    public static Teste stringToObject(String string){
        Map<String, String> map = new HashMap<>();
        Teste teste = new Teste();

        string=string.replace("=", " ");
        string=string.replace("{", "");
        string=string.replace("}", "");
        String[] split = string.split("'");

        teste.setId(split[1]);
        teste.setTitulo(split[5]);

        String[] campos = split[6].replace(",", "").split(" ");

        for(int i = 2; i<campos.length-1; i+=2){
            map.put(campos[i], campos[i+1]);
        }
        teste.setCampos(map);

        return teste;
    }
}
