package com.example.motora.model.classificadores;

import com.example.motora.model.Teste;

public class ClassificadorAntropometria extends Teste {

    private String aluno;

    public ClassificadorAntropometria(){super();}

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String direcionadorTeste (String teste, String genero, int idade, double resultado) {
        if (teste.equals("Percentual de gordura")) {
            return percentualGordura(genero,resultado);
        }
        else if (teste.equals("Circunferência da cintura")) {
            return circunferenciaDaCintura(genero,idade,resultado);
        }
        return "";
    }

    public String percentualGordura(String genero, double percentualGordura) {
        if (genero.equals("masculino")) {
            if (percentualGordura <= 6.0) {
                return "Excessivamente Baixa";
            } else if (percentualGordura >= 6.01 && percentualGordura <= 10.0) {
                return "Baixa";
            } else if (percentualGordura >= 10.01 && percentualGordura <= 20.0) {
                return "Adequada";
            } else if (percentualGordura >= 20.01 && percentualGordura <= 25.0) {
                return "Moderadamente alta";
            } else if (percentualGordura >= 25.01 && percentualGordura <= 31.0) {
                return "Alta";
            } else {
                return "Excessivamente alta";
            }
        } else if (genero.equals("feminino")) {
            if (percentualGordura <= 12.0) {
                return "Excessivamente Baixa";
            } else if (percentualGordura >= 12.01 && percentualGordura <= 15.0) {
                return "Baixa";
            } else if (percentualGordura >= 15.01 && percentualGordura <= 25.0) {
                return "Adequada";
            } else if (percentualGordura >= 25.01 && percentualGordura <= 30.0) {
                return "Moderadamente alta";
            } else if (percentualGordura >= 30.01 && percentualGordura <= 36.0) {
                return "Alta";
            } else {
                return "Excessivamente alta";
            }
        }
        return "Classificação não encontrada";
    }

    public String circunferenciaDaCintura(String genero, int idade, double circunferenciaCintura) {
        if (genero.equals("masculino")) {
            if (idade == 11 && circunferenciaCintura >= 72.4) {
                return "Elevado";
            } else if (idade == 12 && circunferenciaCintura >= 74.7) {
                return "Elevado";
            } else if (idade == 13 && circunferenciaCintura >= 76.9) {
                return "Elevado";
            } else if (idade == 14 && circunferenciaCintura >= 79.0) {
                return "Elevado";
            } else if (idade == 15 && circunferenciaCintura >= 81.1) {
                return "Elevado";
            } else if (idade == 16 && circunferenciaCintura >= 83.1) {
                return "Elevado";
            } else if (idade == 17 && circunferenciaCintura >= 84.9) {
                return "Elevado";
            } else {
                return "Normal";
            }
        } else if (genero.equals("feminino")) {
            if (idade == 11 && circunferenciaCintura >= 71.8) {
                return "Elevado";
            } else if (idade == 12 && circunferenciaCintura >= 73.8) {
                return "Elevado";
            } else if (idade == 13 && circunferenciaCintura >= 75.6) {
                return "Elevado";
            } else if (idade == 14 && circunferenciaCintura >= 77.0) {
                return "Elevado";
            } else if (idade == 15 && circunferenciaCintura >= 78.3) {
                return "Elevado";
            } else if (idade == 16 && circunferenciaCintura >= 79.1) {
                return "Elevado";
            } else if (idade == 17 && circunferenciaCintura >= 79.8) {
                return "Elevado";
            } else {
                return "Normal";
            }
        }
        return "Classificação não encontrada";
    }

}
