package com.example.motora.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.CadastroActivity;
import com.example.motora.databinding.FragmentReportBinding;
import com.example.motora.ui.perfil.PerfilViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportFragment extends Fragment {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    FirebaseUser user;
    private String key, papel;

    TextView textViewSugerir;
    TextInputEditText campoUserReport, campoUserSugestao;
    Button enviarReport, enviarSugestao;

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        textViewSugerir = binding.textViewSugerir;

        campoUserReport = binding.textInputEditTextReport;
        campoUserSugestao = binding.textInputEditTextSugestao;

        enviarReport = binding.buttonEnviarReport;
        enviarSugestao = binding.buttonEnviarSugestao;

        enviarReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerReport();
            }
        });

        enviarSugestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerSugestao();
            }
        });

        verifProfessor();

        return root;
    }

    private void verifProfessor(){

        key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Usuario").document(key).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){

                            DocumentSnapshot documentSnapshot = task.getResult();

                            if(documentSnapshot != null && documentSnapshot.exists()){
                                papel = documentSnapshot.getString("papel");

                                if(papel.equals("Aluno") || papel.equals("aluno")){
                                    textViewSugerir.setVisibility(textViewSugerir.GONE);
                                    campoUserSugestao.setVisibility(campoUserSugestao.GONE);
                                    enviarSugestao.setVisibility(enviarSugestao.GONE);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void fazerReport() {
        if (user != null) {

            if(!campoUserReport.getText().toString().equals("Reporte aqui") && !campoUserReport.getText().toString().equals("")){
                Map<String, Object> doc = new HashMap<>();
                doc.put("id", user.getUid());
                doc.put("report", Objects.requireNonNull(campoUserReport.getText()).toString());
                doc.put("email", user.getEmail());

                db.collection("Reports").document(user.getUid()).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Report feito com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(getContext(), "Não esqueça de incluir um report", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Erro em identificar o usuário", Toast.LENGTH_SHORT).show();
        }

    }

    private void fazerSugestao() {
        if (user != null) {

            if(!campoUserReport.getText().toString().equals("Escreva-nos alguma possível melhoria que gostaria de ver aqui.") && !campoUserReport.getText().toString().equals("")){
                Map<String, Object> doc = new HashMap<>();
                doc.put("id", user.getUid());
                doc.put("sugestao", Objects.requireNonNull(campoUserSugestao.getText()).toString());
                doc.put("email", user.getEmail());

                db.collection("Sugestoes").document(user.getUid()).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Agradecemos pela atenção", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(getContext(), "Não esqueça de incluir uma sugestão", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Erro em identificar o usuário", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
