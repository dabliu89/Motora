package com.example.motora.ui.avaliacoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.dao.DAOTestes;
import com.example.motora.databinding.FragmentAvaliacoesBinding;
import com.example.motora.model.AvaliacaoResultado;


import java.util.ArrayList;

public class AvaliacoesFragment extends Fragment {

    private static int startController = 0;

    private FragmentAvaliacoesBinding binding;

    ListAvaliacoesAdapter listAdapter;
    ArrayList<AvaliacaoResultado> avaliacoesResultados;

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
        ArrayList<AvaliacaoResultado> avaliacoesCompleta = new ArrayList<>(avaliacoesResultados);

        listAdapter = new ListAvaliacoesAdapter(this.getContext(), avaliacoesResultados);
        binding.listaAvaliacoes.setAdapter(listAdapter);
        binding.listaAvaliacoes.setClickable(true);

        SearchView searchView = (SearchView) binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery("", false);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });

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

    private void getAvaliacoes(String nomeAluno) {
        ArrayList<AvaliacaoResultado> resultadosFiltrados = new ArrayList<>();

        for (AvaliacaoResultado resultado : avaliacoesResultados) {
            if (resultado.getAluno().toLowerCase().contains(nomeAluno.toLowerCase())) {
                resultadosFiltrados.add(resultado);
            }
        }

        listAdapter.clear();
        listAdapter.addAll(resultadosFiltrados);
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}