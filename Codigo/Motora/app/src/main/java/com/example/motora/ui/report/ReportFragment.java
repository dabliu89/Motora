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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportFragment extends Fragment {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    FirebaseUser user;

    TextInputEditText campoUserReport;
    Button enviarReport;

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        campoUserReport = binding.textInputEditTextReport;

        enviarReport = binding.buttonEnviarReport;

        enviarReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI();
            }
        });

        return root;
    }

    private void updateUI() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
