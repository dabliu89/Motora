package com.example.motora.ui.avaliacoes;

import android.app.FragmentTransaction;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.R;
import com.example.motora.dao.DAOTestes;
import com.example.motora.databinding.FragmentAvaliacoesBinding;
import com.example.motora.model.AvaliacaoResultado;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class AvaliacoesFragment extends Fragment {

    private static int startController = 0;

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


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        avaliacoesResultados = new ArrayList<AvaliacaoResultado>();

        listAdapter = new ListAvaliacoesAdapter(this.getContext(), avaliacoesResultados);
        binding.listaAvaliacoes.setAdapter(listAdapter);
        binding.listaAvaliacoes.setClickable(true);

        binding.listaAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AvaliacoesFragment.this.getContext(), ResultadosDetalhadosActivity.class);
                intent.putExtra("avalResult", avaliacoesResultados.get(i).toString());
                intent.putExtra("avalCampos", avaliacoesResultados.get(i).getCampos().toString());
                startActivity(intent);
            }
        });
        DAOTestes.getResultados(avaliacoesResultados, listAdapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}