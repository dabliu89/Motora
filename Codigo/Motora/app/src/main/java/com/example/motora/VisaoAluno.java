package com.example.motora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.motora.dao.DAOTestes;
import com.example.motora.databinding.FragmentAvaliacoesBinding;
import com.example.motora.model.AvaliacaoResultado;
import com.example.motora.ui.avaliacoes.AvaliacoesFragment;
import com.example.motora.ui.avaliacoes.ListAvaliacoesAdapter;
import com.example.motora.ui.avaliacoes.ResultadosDetalhadosActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class VisaoAluno extends AppCompatActivity {

    private static int startController = 0;

    private FirebaseAuth auth;

    private FragmentAvaliacoesBinding binding;

    ListAvaliacoesAdapter listAdapter;
    ArrayList<AvaliacaoResultado> avaliacoesResultados;
    AvaliacaoResultado avalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        binding = FragmentAvaliacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onStart() {
        super.onStart();
        avaliacoesResultados = new ArrayList<AvaliacaoResultado>();

        listAdapter = new ListAvaliacoesAdapter(VisaoAluno.this, avaliacoesResultados);
        binding.listaAvaliacoes.setAdapter(listAdapter);
        binding.listaAvaliacoes.setClickable(true);

        binding.listaAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(VisaoAluno.this, ResultadosDetalhadosActivity.class);
                intent.putExtra("avalResult", avaliacoesResultados.get(i).toString());
                intent.putExtra("avalCampos", avaliacoesResultados.get(i).getCampos().toString());
                startActivity(intent);
            }
        });

        DAOTestes.getResultadosAlunoEspecifico(avaliacoesResultados, listAdapter, auth.getCurrentUser().getUid());
    }
}