package com.example.motora.model.classificadores;

import com.example.motora.model.Teste;

public class ClassificadorApFRS extends Teste {

    private String aluno;

    public ClassificadorApFRS(){super();}

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

        public String direcionadorTeste (String teste, String genero, int idade, double resultado) {
                if (teste.equals("IMC")) {
                        return imcSaude(genero,idade,resultado);
                }
                else if (teste.equals("Corrida de 6 minutos")) {
                        return corridaCaminhadaSeisMinutos(genero,idade, (int) resultado);
                }
                else if (teste.equals("RCE")) {
                        return rce(genero,idade,resultado);
                }
                else if (teste.equals("Sentar e Alcançar")) {
                        return sentarEAlcancar(genero,idade,resultado);
                }
                else if (teste.equals("Abdominais em Um Minuto")) {
                        return abdominaisEmUmMinuto(genero,idade, (int) resultado);
                }
                else if (teste.equals("Arremesso de Medicineball")) {
                        return arremessoDeMedicineball(genero,idade,resultado);
                }
                else if (teste.equals("Corrida de 20 metros")) {
                        return corridaDeVinteMetros(genero,idade,resultado);
                }
                return "";
        }

        public static double imcCalc(float massa, float altura){
            return massa / (altura * altura);
        }

        public static double rCE(float cintura, float estatura){
            return cintura/estatura;
        }

        public String imcSaude(String genero, int idade, double resultado) {

                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado > 17.7) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado > 17.8) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado > 19.2) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado > 19.3) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado > 20.7) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado > 22.1) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado > 22.2) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if ((idade == 13 || idade == 14) && resultado > 22.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado > 23.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado > 24.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado > 25.4) {
                                return "ZONA DE RISCO À SAÚDE";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade == 6 && resultado > 17.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado > 17.1) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado > 18.2) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado > 19.1) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado > 20.9) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if ((idade >= 11 && idade <= 14) && resultado > 22.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado > 22.4) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado > 24.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado > 24.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        }
                }

                return "ZONA SAUDÁVEL";
        }

        public String corridaCaminhadaSeisMinutos(String genero, int idade, int resultado) {
                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado < 675) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 730) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 768) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 820) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 856) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 930) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 966) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 995) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 1060) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 1130) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado < 1190) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado < 1190) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade == 6 && resultado < 630) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 683) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 715) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 745) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 790) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 840) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 900) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 940) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 985) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 1005) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado < 1070) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado < 1110) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                }
                return "";
        }

        public String rce(String genero, int idade, double resultado) {
                if (resultado < 0.50) {
                        return "ZONA DE RISCO À SAÚDE";
                } else {
                        return "ZONA SAUDÁVEL";
                }
        }

        public String sentarEAlcancar(String genero, int idade, double resultado) {
                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado < 29) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 29) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 32.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 29) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 29.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 29.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 29.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 26.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 30.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 31) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado < 34.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado < 34) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade == 6 && resultado < 40.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 40.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 39.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 35) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 36.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 34.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 39.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 38.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 38.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 38.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado < 39.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado < 39.5) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                }
                return "ZONA SAUDÁVEL";
        }

        public String abdominaisEmUmMinuto(String genero, int idade, int resultado) {
                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado < 18) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 18) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 24) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 26) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 31) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 37) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if ((idade == 12 || idade == 13) && resultado < 42) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 43) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 45) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade >= 16 && idade <= 17 && resultado < 46) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade >= 6 && idade <= 8 && resultado < 18) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 20) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 26) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 30) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade >= 12 && idade <= 13 && resultado < 33) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade >= 14 && idade <= 17 && resultado < 34) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                }
                return "ZONA SAUDÁVEL";
        }

        public String arremessoDeMedicineball(String genero, int idade, double resultado) {
                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado < 147.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 168.7) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 190.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 210.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 232.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 260.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 290.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 335.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 400.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 440.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado < 480.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado < 500.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade == 6 && resultado < 125.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado < 140.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado < 158.1) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado < 175.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado < 202.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado < 228.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado < 260.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado < 280.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado < 290.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado < 306.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade >= 16 && idade <= 17 && resultado < 315.0) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                }
                return "ZONA SAUDÁVEL";
        }

        public String corridaDeVinteMetros(String genero, int idade, double resultado) {
                if (genero.equals("masculino")) {
                        if (idade == 6 && resultado > 4.81) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado > 4.52) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado > 4.31) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado > 4.25) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado > 4.09) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado > 4.00) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado > 3.88) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado > 3.72) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado > 3.54) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado > 3.40) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 16 && resultado > 3.28) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 17 && resultado > 3.22) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                } else if (genero.equals("feminino")) {
                        if (idade == 6 && resultado > 5.22) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 7 && resultado > 4.88) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 8 && resultado > 4.66) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 9 && resultado > 4.58) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 10 && resultado > 4.44) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 11 && resultado > 4.36) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 12 && resultado > 4.28) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 13 && resultado > 4.17) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 14 && resultado > 4.16) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade == 15 && resultado > 4.07) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else if (idade >= 16 && idade <= 17 && resultado > 3.91) {
                                return "ZONA DE RISCO À SAÚDE";
                        } else {
                                return "ZONA SAUDÁVEL";
                        }
                }
                return "ZONA SAUDÁVEL";
        }

}
