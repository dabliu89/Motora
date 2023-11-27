package com.example.motora.ui.home;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.MainActivity;
import com.example.motora.R;
import com.example.motora.TesteActivity;
import com.example.motora.dao.DAOTestes;
import com.example.motora.dao.DAOUsuario;
import com.example.motora.databinding.FragmentHomeBinding;
import com.example.motora.model.Aluno;
import com.example.motora.model.Teste;
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
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentHomeBinding binding;
    private Spinner tipoAvaliacao;
    private Spinner avaliacao;
    private Spinner aluno;

    private Button next;
    private ArrayList<String> tiposList = new ArrayList<String>();

    private ArrayList<Teste> avaliacoesList = new ArrayList<Teste>();
    private ArrayList<Aluno> alunosList = new ArrayList<Aluno>();


    ArrayAdapter<String> tiposAdapter;
    ArrayAdapter</*Teste*/String> avaliacoesAdapter;
    ArrayAdapter</*Aluno*/String> alunosAdapter;

    DAOUsuario daoUsuario = new DAOUsuario();

    View root;

    private static final String TAG = "ViewDatabase";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "Iniciou o fragment");
        Toast.makeText(getContext(), "Fragment iniciado", Toast.LENGTH_SHORT).show();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initView(root);

        /*tiposAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, tiposList);
        avaliacoesAdapter = new ArrayAdapter<Teste>(this.getActivity(), android.R.layout.simple_spinner_item, avaliacoesList);
        alunosAdapter = new ArrayAdapter<Aluno>(this.getActivity(), android.R.layout.simple_spinner_item, alunosList);*/

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        //initView(root);
    }

    private void initView(View root){

        tipoAvaliacao = root.findViewById(R.id.tipoAvaliacao);
        avaliacao = root.findViewById(R.id.avaliacao);
        aluno = root.findViewById(R.id.aluno);
        next = root.findViewById(R.id.buttonIrParaForm);

        db = FirebaseFirestore.getInstance();

        tiposList = new ArrayList<String>();

        avaliacoesList = new ArrayList<>();
        avaliacoesList = DAOTestes.getTestes(avaliacoesList);
        //avaliacoesAdapter.notifyDataSetChanged();

        alunosList = new ArrayList<>();
        alunosList = daoUsuario.getListAlunos(alunosList);
        //alunosAdapter.notifyDataSetChanged();

        ArrayList<String> avalList = new ArrayList<>();
        ArrayList<String> aluList = new ArrayList<>();

        for(int i = 0; i < avaliacoesList.size(); ++i){
            avalList.add(avaliacoesList.get(i).toString());
        }

        for(int i = 0; i < alunosList.size(); ++i){
            aluList.add(alunosList.get(i).toString());
        }

        tiposAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, tiposList);
        avaliacoesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, /*avaliacoesList*/avalList);
        alunosAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, /*alunosList*/aluList);

        getListasFirestore("TiposTestes", "nome", tiposAdapter, tiposList);
        getListasFirestore("Avaliacoes", "titulo", avaliacoesAdapter, /*avaliacoesList*/avalList);
        getListasFirestore("Usuario", "nome", /*"Aluno",*/ alunosAdapter, /*alunosList*/aluList);

        // Após preencher as listas, adicione as chamadas notifyDataSetChanged
        tiposAdapter.notifyDataSetChanged();
        avaliacoesAdapter.notifyDataSetChanged();
        alunosAdapter.notifyDataSetChanged();

        setUpSpinners(tipoAvaliacao, tiposAdapter);
        setUpSpinners(avaliacao, avaliacoesAdapter);
        setUpSpinners(aluno, alunosAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), TesteActivity.class);
                intent.putExtra("alunoId", alunosList.get(aluno.getSelectedItemPosition()).getId());
                intent.putExtra("testeId", avaliacoesList.get(avaliacao.getSelectedItemPosition()).getId());
                intent.putExtra("tipoTeste", tiposList.get(tipoAvaliacao.getSelectedItemPosition()));
                startActivity(intent);
            }
        });
    }

    private void setUpSpinners(Spinner spinner, ArrayAdapter adapter){
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
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
                    list.add(document.getString(field));
                    adapter.notifyDataSetChanged();
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