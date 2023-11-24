package com.example.motora;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motora.dao.DAOTestes;
import com.example.motora.model.Teste;
import com.example.motora.model.classificadores.ClassificadorAntropometria;
import com.example.motora.model.classificadores.ClassificadorApFRS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TesteActivity extends AppCompatActivity {

    public String nomeTeste;
    String alunoId;

    private Teste teste;

    private Button salvar;

    private FirebaseFirestore db;
    String nomeProcurado;
    ClassificadorAntropometria classificadorAntropometria = new ClassificadorAntropometria();
    ClassificadorApFRS classificadorApFRS = new ClassificadorApFRS();

    private DAOTestes daoTestes = new DAOTestes();

    private ArrayList<TextView> labels = new ArrayList<>();
    private ArrayList<EditText> boxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        TextView textTitle = findViewById(R.id.textTitleAval);
        nomeTeste = this.getIntent().getExtras().get("testName").toString();

        alunoId = this.getIntent().getExtras().get("alunoName").toString().split(",")[1];
        alunoId = alunoId.split("=")[1];

        nomeProcurado = this.getIntent().getExtras().get("alunoName").toString();

        teste = Teste.stringToObject(nomeTeste);

        WebView videoTutorial = findViewById(R.id.videoTutorial);
        String iFrame = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/e5OuLSDayxs\" title=\"Como Calcular O IMC (Índice De Massa Corporal) + Tabela De Referência | Dicas De Nutrição\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        videoTutorial.loadData(iFrame, "text/html", "utf-8");
        videoTutorial.getSettings().setJavaScriptEnabled(true);
        videoTutorial.setWebChromeClient(new WebChromeClient());
        //getFieldsFromFirebase();

        textTitle.setText(teste.getTitulo());
        salvar = findViewById(R.id.buttonSalvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<String, String>();
                ClassificadorApFRS resultado = new ClassificadorApFRS();
                resultado.setAluno(alunoId);

                for(int i=0;i<labels.size();i++){
                    map.put(labels.get(i).getText().toString().toLowerCase(), boxes.get(i).getText().toString());
                }

                db = FirebaseFirestore.getInstance();

                CollectionReference usuariosRef = db.collection("Usuarios");
                usuariosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userId = document.getId();

                                String nomeDoUsuario = document.getString("nome");

                                if (nomeDoUsuario.equals(nomeProcurado)) {
                                    if(teste.getTipo().equals("Antropometria")){
                                        classificadorAntropometria.direcionadorTeste(
                                                teste,
                                                document.getString("genero"),
                                                Integer.parseInt(document.getString("idade")),
                                                );
                                    }else if(teste.getTipo().equals("ApFRS")){
                                        classificadorApFRS.direcionadorTeste(teste,
                                                document.getString("genero"),
                                                Integer.parseInt(document.getString("idade")),
                                                );
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(TesteActivity.this, "Task: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mandarResultado(teste);

                DAOTestes.createNewAvaliacao(resultado);

                startActivity(new Intent(TesteActivity.this, MainActivity.class));
            }
        });


        generateForm();

    }

    private void generateForm(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.container);
        Map<String, String> campos = teste.getCampos();

        for(String key : campos.keySet()){
            linearLayout.addView(createLabel(key));
            linearLayout.addView(createBox(campos.get(key)));
        }


    }

    private TextView createLabel(String label){
        TextView valueTV = new TextView(this);
        valueTV.setText(label.toUpperCase());
        valueTV.setTextColor(Color.GRAY);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        labels.add(valueTV);

        return labels.get(labels.size()-1);
    }

    private EditText createBox(String type){
        EditText valueET = new EditText(this);
        valueET.setInputType(TYPE_NUMBER_FLAG_DECIMAL|TYPE_CLASS_NUMBER);
        valueET.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        boxes.add(valueET);

        return boxes.get(boxes.size()-1);
    }

    private void mandarResultado(Teste classTeste){
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", user.getUid());
        doc.put("report", Objects.requireNonNull(campoUserReport.getText()).toString());
        doc.put("email", user.getEmail());

        db.collection("AvaliacoesResultados").document(user.getUid()).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        Toast.makeText(TesteActivity.this, "Resultado cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(TesteActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}