package com.example.motora.model;

import static org.junit.Assert.*;

import com.google.firebase.firestore.DocumentReference;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Map;

public class TesteTest {

    Teste teste = new Teste();

    @Mock
    DocumentReference documentReference;
    @Mock
    Map<String, String> campos;

    @Test
    public void setTitulo(){
        teste.setTitulo("Titulo");
        assertEquals("Titulo", teste.getTitulo());
    }

    @Test
    public void setId(){
        teste.setId("1234");
        assertEquals("1234", teste.getId());
    }

    @Test
    public void setTtpo(){
        teste.setTipo(documentReference.toString());
        assertEquals(documentReference.toString(), teste.getTipo());
    }

    @Test
    public void setCampos(){
        teste.setCampos(campos);
        assertEquals(campos, teste.getCampos());
    }

    @Test
    public void createTesteCompleto(){
        teste = new Teste(documentReference.toString(), "Titulo");

        assertEquals(documentReference, teste.getTipo());
        assertEquals("Titulo", teste.getTitulo());
    }

    @Test
    public void testeToString(){
        String testeToStringShouldBe = "Teste{" +
                "id='" + "123456" + '\'' +
                ", tipo='" + teste.getTipo() + '\'' +
                ", titulo='" + teste.getTitulo() + '\'' +
                ", campos=" + teste.getCampos() +
                '}';
        teste = new Teste(documentReference.toString(), "Titulo");
        teste.setId("123456");

        assertEquals("Titulo", teste.toString());
    }
}