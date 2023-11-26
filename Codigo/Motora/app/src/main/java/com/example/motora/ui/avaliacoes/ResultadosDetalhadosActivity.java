package com.example.motora.ui.avaliacoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.motora.R;
import com.example.motora.dao.DAOTestes;
import com.example.motora.model.AvaliacaoResultado;

import java.util.Locale;

public class ResultadosDetalhadosActivity extends AppCompatActivity {

    TextView tituloTeste;

    TextView nomeAluno;

    TextView resultadosAvaliacao;

    ImageView detailedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_detalhados);

        AvaliacaoResultado avaliacaoResultado = AvaliacaoResultado.stringToObject(this.getIntent().getExtras().get("avalResult").toString());

        tituloTeste = findViewById(R.id.testeTitulo);
        nomeAluno = findViewById(R.id.nomeDoAluno);
        resultadosAvaliacao = findViewById(R.id.resultadosAvaliacao);
        detailedImage = findViewById(R.id.detailedImage);

        tituloTeste.setText(avaliacaoResultado.getTitulo());
        nomeAluno.setText((avaliacaoResultado.getAluno()));

        String textoResultados = this.getIntent().getExtras().get("avalCampos").toString().replace("{", "");
        textoResultados = textoResultados.replace("}", "");
        textoResultados = textoResultados.replace(",", "\n");
        textoResultados = textoResultados.toUpperCase(Locale.ROOT);
        textoResultados = textoResultados + "\n" + avaliacaoResultado.getMessage();

        if(!avaliacaoResultado.getMessage().equals("ZONA SAUDÁVEL")){
            detailedImage.setBackgroundColor(ContextCompat.getColor(this, R.color.ActionBarTitleColor));
        }

        if(avaliacaoResultado.getTipo().equals("ApFRS")){
            detailedImage.setImageResource(R.drawable.aptidao_icon);
        } else {
            detailedImage.setImageResource(R.drawable.antropometria_icon);
        }

        resultadosAvaliacao.setText(textoResultados);

    }


}