package com.example.motora.ui.avaliacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.motora.R;
import com.example.motora.dao.DAOTestes;
import com.example.motora.model.AvaliacaoResultado;

import java.util.Locale;

public class ResultadosDetalhadosActivity extends AppCompatActivity {

    TextView tituloTeste;

    TextView nomeAluno;

    TextView resultadosAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_detalhados);

        AvaliacaoResultado avaliacaoResultado = AvaliacaoResultado.stringToObject(this.getIntent().getExtras().get("avalResult").toString());

        tituloTeste = findViewById(R.id.testeTitulo);
        nomeAluno = findViewById(R.id.nomeDoAluno);
        resultadosAvaliacao = findViewById(R.id.resultadosAvaliacao);

        tituloTeste.setText(avaliacaoResultado.getTitulo());
        nomeAluno.setText((avaliacaoResultado.getAluno()));

        String textoResultados = this.getIntent().getExtras().get("avalCampos").toString().replace("{", "");
        textoResultados = textoResultados.replace("}", "");
        textoResultados = textoResultados.toUpperCase(Locale.ROOT);
        textoResultados = textoResultados + "\n" + avaliacaoResultado.getMessage();

        resultadosAvaliacao.setText(textoResultados);

    }


}