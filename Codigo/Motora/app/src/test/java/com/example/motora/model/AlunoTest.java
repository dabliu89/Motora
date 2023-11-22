package com.example.motora.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlunoTest {

    Aluno aluno = new Aluno();

    @Test
    public void setNome(){
        aluno.setNome("Aluno Test");
        assertEquals("Aluno Test", aluno.getNome());
    }

    @Test
    public void setEmail(){
        aluno.setEmail("aluno@aluno.com");
        assertEquals("aluno@aluno.com", aluno.getEmail());
    }

    @Test
    public void setSenha(){
        aluno.setSenha("1234");
        assertEquals("1234", aluno.getSenha());
    }

    @Test
    public void setUid(){
        aluno.setUid("1234");
        assertEquals("1234", aluno.getUid());
    }

    @Test
    public void createAlunoCompleto(){
        aluno = new Aluno("54321", "alunoTeste", "alunoteste2@aluno.com", "senha1234");

        assertEquals("54321", aluno.getUid());
        assertEquals("alunoTeste", aluno.getNome());
        assertEquals("alunoteste2@aluno.com", aluno.getEmail());
        assertEquals("senha1234", aluno.getSenha());
    }

    @Test
    public void createAlunoToStringAll(){
        String alunoToStringShouldBe = "Aluno{" +
                "id=" + "54321" +
                ", nome='" + "alunoTeste" + '\'' +
                ", email='" + "alunoteste2@aluno.com" + '\'' +
                ", senha='" + "senha1234" + '\'' +
                '}';

        aluno = new Aluno("54321", "alunoTeste", "alunoteste2@aluno.com", "senha1234");

        assertEquals(alunoToStringShouldBe, aluno.toStringAll());
        assertEquals("alunoTeste", aluno.toString());
    }


}