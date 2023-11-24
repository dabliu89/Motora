package com.example.motora.ui.avaliacoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.R;
import com.example.motora.databinding.FragmentAvaliacoesBinding;
import com.example.motora.model.AvaliacaoResultado;

import java.util.ArrayList;

public class AvaliacoesFragment extends Fragment {

    private FragmentAvaliacoesBinding binding;

    ListAvaliacoesAdapter listAdapter;
    ArrayList<AvaliacaoResultado> avaliacoesResultados;
    AvaliacaoResultado avalResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AvaliacoesViewModel avaliacoesViewModel =
                new ViewModelProvider(this).get(AvaliacoesViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentAvaliacoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        avaliacoesResultados = new ArrayList<AvaliacaoResultado>();

        for(int i=0; i<=8;i++){
            avalResult = new AvaliacaoResultado();
            avalResult.setAluno("Maria Eduarda");
            avalResult.setData("22/11/2023");
            avalResult.setImage(R.drawable.aptidao_icon);
            avalResult.setTituloTeste("Abdominais por Minuto");

            avaliacoesResultados.add(avalResult);
        }

        listAdapter = new ListAvaliacoesAdapter(this.getContext(), avaliacoesResultados);
        binding.listaAvaliacoes.setAdapter(listAdapter);
        binding.listaAvaliacoes.setClickable(true);

        binding.listaAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AvaliacoesFragment.this.getContext(), ResultadosDetalhadosActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}