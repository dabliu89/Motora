package com.example.motora.ui.avaliacoes;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SearchView;
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


//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        AvaliacoesViewModel avaliacoesViewModel =
//                new ViewModelProvider(this).get(AvaliacoesViewModel.class);
//
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//
//        binding = FragmentAvaliacoesBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        return root;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentAvaliacoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchView searchView = root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Se desejar lidar com a submissão do texto de pesquisa
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Chama a função de busca quando o texto é alterado
                buscarAvaliacoesPorAluno(newText);
                return true;
            }
        });

        listAdapter = new ListAvaliacoesAdapter(getContext(), avaliacoesResultados);
        binding.listaAvaliacoes.setAdapter(listAdapter);

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Verifique se avaliacoesResultados é nulo e, se for, inicialize-o
        if (avaliacoesResultados == null) {
            avaliacoesResultados = new ArrayList<>();
        }

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


    // Função para buscar avaliações pelo nome do aluno
    private void buscarAvaliacoesPorAluno(String nomeAluno) {
        ArrayList<AvaliacaoResultado> resultadosFiltrados = new ArrayList<>();

        for (AvaliacaoResultado resultado : avaliacoesResultados) {
            if (resultado.getAluno().toLowerCase().contains(nomeAluno.toLowerCase())) {
                resultadosFiltrados.add(resultado);
            }
        }

        // Limpa o adaptador e adiciona os resultados filtrados
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