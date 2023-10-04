package com.example.motora.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.MainActivity;
import com.example.motora.R;
import com.example.motora.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentHomeBinding binding;
    private Spinner tipoAvaliacao;
    private Spinner avaliacao;
    private Spinner aluno;
    private ArrayList<String> tiposList = new ArrayList<String>();

    private ArrayList<String> avaliacoesList = new ArrayList<String>();
    private ArrayList<String> alunosList = new ArrayList<String>();

    ArrayAdapter<String> tiposAdapter;
    ArrayAdapter<String> avaliacoesAdapter;
    ArrayAdapter<String> alunosAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tiposAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, tiposList);
        avaliacoesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, avaliacoesList);
        alunosAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, alunosList);

        initView(root);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView(View root){
        tipoAvaliacao = root.findViewById(R.id.tipoAvaliacao);
        avaliacao = root.findViewById(R.id.avaliacao);
        aluno = root.findViewById(R.id.aluno);
        db = FirebaseFirestore.getInstance();

        tiposList = new ArrayList<String>();
        avaliacoesList = new ArrayList<String>();
        alunosList = new ArrayList<String>();

        getListasFirestore("TiposTestes", "nome", tiposAdapter, tiposList);
        getListasFirestore("Avaliacoes", "titulo", avaliacoesAdapter, avaliacoesList);
        getListasFirestoreFilter("Usuario", "papel", "Aluno", alunosAdapter, alunosList);

        setUpSpinners(tipoAvaliacao, tiposAdapter);
        setUpSpinners(avaliacao, avaliacoesAdapter);
        setUpSpinners(aluno, alunosAdapter);
    }

    private void setUpSpinners(Spinner spinner, ArrayAdapter adapter){
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListasFirestore(String collection, String field, ArrayAdapter adapter, ArrayList<String> list){
        CollectionReference tipos = db.collection(collection);
        Query query = tipos.orderBy(field);
        ArrayList<String> temp = new ArrayList<String>();

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    adapter.notifyDataSetChanged();
                    list.add(document.getString(field));
                }
            }
        });
    }

    private void getListasFirestoreFilter(String collection, String field, String filtro, ArrayAdapter adapter, ArrayList<String> list){
        CollectionReference tipos = db.collection(collection);
        Query query = tipos.whereEqualTo(field, filtro);
        ArrayList<String> temp = new ArrayList<String>();

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    adapter.notifyDataSetChanged();
                    list.add(document.getString("nome"));
                }
            }
        });
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}