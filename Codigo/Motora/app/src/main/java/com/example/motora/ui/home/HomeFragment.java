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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private String meuNome, meuPapel, key;
    ArrayAdapter<String> tiposAdapter;
    ArrayAdapter<Teste/*String*/> avaliacoesAdapter;
    ArrayAdapter<Aluno/*String*/> alunosAdapter;

    //DAOUsuario daoUsuario = new DAOUsuario();

    private String id, nome, email, senha, genero, papel, profResp;
    private long idade;
    ArrayList<Aluno> alunos;

    private String idAva, tipo, titulo, video;
    private Map<String, String> campos;

    Teste ob2;

    View root;

    private static final String TAG = "ViewDatabase";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initView(root);

        /*if (!tiposList.isEmpty()) {
            loadAvaliacoes(tiposList.get(0));
        }*/

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
        //avaliacoesList = DAOTestes.getTestes(avaliacoesList);
        //avaliacoesAdapter.notifyDataSetChanged();

        key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Usuario").document(key).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                meuNome = documentSnapshot.getString("nome");
                                meuPapel = documentSnapshot.getString("papel");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        alunosList = new ArrayList<>();
        //alunosList = /*daoUsuario*/DAOUsuario.getListAlunos(alunosList, meuNome);
        //alunosAdapter.notifyDataSetChanged();

        ArrayList<String> avalList = new ArrayList<>();
        ArrayList<String> aluList = new ArrayList<>();

        for(int i = 0; i < avaliacoesList.size(); ++i){
            avalList.add(avaliacoesList.get(i).toString());
        }

        tiposAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, tiposList);
        avaliacoesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, avaliacoesList/*avalList*/);
        alunosAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, alunosList/*aluList*/);

        getListasFirestore("TiposTestes", "nome", tiposAdapter, tiposList);
        getListasFirestore("Avaliacoes", "titulo", avaliacoesAdapter, /*avaliacoesList*/avalList);
        getListasFirestore("Usuario", "nome", /*"Aluno",*/ alunosAdapter, /*alunosList*/aluList);

        tiposAdapter.notifyDataSetChanged();
        avaliacoesAdapter.notifyDataSetChanged();
        alunosAdapter.notifyDataSetChanged();

        setUpSpinners(tipoAvaliacao, tiposAdapter);
        setUpSpinners(avaliacao, avaliacoesAdapter);
        setUpSpinners(aluno, alunosAdapter);

        //loadAvaliacoes(tiposList.get(0));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meuPapel.equals("Professor")){
                    Intent intent = new Intent(HomeFragment.this.getActivity(), TesteActivity.class);
                    intent.putExtra("alunoId", alunosList.get(aluno.getSelectedItemPosition()).getId());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("camposTeste", (Serializable) avaliacoesList.get(avaliacao.getSelectedItemPosition()).getCampos());

                    intent.putExtra("testeId", avaliacoesList.get(avaliacao.getSelectedItemPosition()).getId());
                    intent.putExtra("tituloTeste", avaliacoesList.get(avaliacao.getSelectedItemPosition()).getTitulo());
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("videoTeste", avaliacoesList.get(avaliacao.getSelectedItemPosition()).getVideo());

                    intent.putExtra("tipoTeste", tiposList.get(tipoAvaliacao.getSelectedItemPosition()));
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Funcionalidade disponível apenas a professores", Toast.LENGTH_SHORT).show();
                }
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
                if(spinner == tipoAvaliacao){
                    loadAvaliacoes(tiposList.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void loadAvaliacoes(String tipoSelecionado) {
        avaliacoesList.clear();
        avaliacoesAdapter.notifyDataSetChanged();

        db.collection("Avaliacoes")
                .whereEqualTo("tipo", tipoSelecionado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Teste ob2 = document.toObject(Teste.class);
                                ob2.setId(document.getId());
                                avaliacoesList.add(ob2);
                            }

                            avaliacoesAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Erro ao acessar o banco de dados", task.getException());
                        }
                    }
                });
    }

    private void getListasFirestore(String collection, String field, ArrayAdapter adapter, ArrayList<String> list){
        CollectionReference tipos = db.collection(collection);

        if(collection.equals("TiposTestes")){
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
        }else if(collection.equals("Avaliacoes")){
            Query query = tipos.orderBy(field);
            ArrayList<String> temp = new ArrayList<String>();

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    tipos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    idAva = document.getId();
                                    campos = (Map<String, String>) document.get("campos");
                                    tipo = document.getString("tipo");
                                    titulo = document.getString("titulo");
                                    video = document.getString("video");

                                    ob2 = document.toObject(Teste.class);
                                    ob2.setId(idAva);
                                    ob2.setCampos(campos);
                                    ob2.setTipo(tipo);
                                    ob2.setTitulo(titulo);
                                    ob2.setVideo(video);

                                    avaliacoesList.add(ob2);

                                    //list.add(nomeUsuario);
                                    adapter.notifyDataSetChanged();

                                    //loadAvaliacoes(tiposList.get(0));
                                }
                            } else {
                                Log.w(TAG, "Erro ao acessar o banco de dados", task.getException());
                            }
                        }
                    });
                }
            });
        }else{

            alunos = new ArrayList<>();
            tipos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String nomeUsuario = document.getString("nome");
                            //String idUsuario = document.getString("id");
                            String nomeProf = document.getString("prof_responsavel");

                            if(nomeProf != null && nomeProf.equals(meuNome)){
                                email = document.getString("email");
                                genero = document.getString("genero");
                                id = document.getString("id");
                                idade = document.getLong("idade");
                                nome = document.getString("nome");
                                papel = document.getString("papel");
                                profResp = document.getString("prof_responsavel");
                                senha = document.getString("senha");

                                Aluno ob = document.toObject(Aluno.class);
                                ob.setEmail(email);
                                ob.setGenero(genero);
                                ob.setUid(id);
                                ob.setId(id);
                                ob.setIdade((int) idade);
                                ob.setNome(nome);
                                ob.setPapel(papel);
                                ob.setNomeProfRes(profResp);
                                ob.setSenha(senha);

                                alunosList.add(ob);

                                //list.add(nomeUsuario);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        Log.w(TAG, "Erro ao acessar o banco de dados", task.getException());
                    }
                }
            });

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}