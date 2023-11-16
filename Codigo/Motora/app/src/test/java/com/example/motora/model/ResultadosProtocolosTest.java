package com.example.motora.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultadosProtocolosTest {
    @Test
    public void testDirecionadorTesteImcSaude() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "IMC", "masculino", 6, 18));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "IMC", "feminino", 10, 19));
    }

    @Test
    public void testDirecionadorTesteCorridaCaminhadaSeisMinutos() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "Corrida de 6 minutos", "masculino", 12, 965));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "Corrida de 6 minutos", "feminino", 9, 744));
    }

    @Test
    public void testDirecionadorTesteRce() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "RCE", "masculino", 10, 0.45));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "RCE", "feminino", 15, 0.52));
    }

    @Test
    public void testDirecionadorTesteSentarEAlcancar() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "Sentar e Alcançar", "masculino", 8, 31.0));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "Sentar e Alcançar", "feminino", 14, 38.0));
    }

    @Test
    public void testDirecionadorTesteAbdominaisEmUmMinuto() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "Abdominais em Um Minuto", "masculino", 12, 41));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "Abdominais em Um Minuto", "feminino", 9, 18));
    }

    @Test
    public void testDirecionadorTesteArremessoDeMedicineball() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "Arremesso de Medicineball", "masculino", 14, 380.0));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "Arremesso de Medicineball", "feminino", 10, 150.0));
    }

    @Test
    public void testDirecionadorTesteCorridaDeVinteMetros() {
        ResultadosProtocolos resultados = new ResultadosProtocolos();
        assertEquals("ZONA DE RISCO À SAÚDE", resultados.direcionadorTeste("Aptidão Física", "Corrida de 20 metros", "masculino", 17, 3.95));
        assertEquals("ZONA SAUDÁVEL", resultados.direcionadorTeste("Aptidão Física", "Corrida de 20 metros", "feminino", 11, 4.5));
    }
}