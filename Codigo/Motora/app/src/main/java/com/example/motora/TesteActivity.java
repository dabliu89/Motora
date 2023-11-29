package com.example.motora;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motora.dao.DAOTestes;
import com.example.motora.model.Aluno;
import com.example.motora.model.AvaliacaoResultado;
import com.example.motora.model.Teste;
import com.example.motora.model.classificadores.ClassificadorAntropometria;
import com.example.motora.model.classificadores.ClassificadorApFRS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TesteActivity extends AppCompatActivity {

    public String nomeTeste;
    String alunoId;

    private Teste teste;

    private Button salvar;

    FirebaseUser user;
    private FirebaseFirestore db;
    String nomeProcurado;
    ClassificadorAntropometria classificadorAntropometria = new ClassificadorAntropometria();
    ClassificadorApFRS classificadorApFRS = new ClassificadorApFRS();

    TextView textTitle;

    private DAOTestes daoTestes = new DAOTestes();

    private ArrayList<TextView> labels = new ArrayList<>();
    private ArrayList<EditText> boxes = new ArrayList<>();

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        user = FirebaseAuth.getInstance().getCurrentUser();

        db = FirebaseFirestore.getInstance();

        textTitle = findViewById(R.id.textTitleAval);

        alunoId = this.getIntent().getExtras().get("alunoId").toString();


        DAOTestes.getTesteFirebase(this.getIntent().getExtras().get("testeId").toString());
        bundle = this.getIntent().getBundleExtra("bundle");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(DAOTestes.t.size() == 0 || bundle == null){
            textTitle.setText(DAOTestes.t.size()+"true");
            this.recreate();
        }
        else{
            textTitle.setText(DAOTestes.t.size()+"");
            //teste = DAOTestes.t.get(DAOTestes.t.size()-1);
            //teste = DAOTestes.getTesteFirebase(this.getIntent().getExtras().get("testeId").toString());
            teste = new Teste();
            teste.setId(this.getIntent().getExtras().get("testeId").toString());
            teste.setTipo(this.getIntent().getExtras().get("tipoTeste").toString());
            teste.setTitulo(this.getIntent().getExtras().get("tituloTeste").toString());

            Map<String, String> camposRecebidos = (Map<String, String>) bundle.getSerializable("camposTeste");
            teste.setCampos(camposRecebidos);

            teste.setVideo(this.getIntent().getExtras().get("videoTeste").toString());

            WebView videoTutorial = findViewById(R.id.videoTutorial);
            String iFrame = "<iframe width=\"100%\" height=\"100%\" src=\""+teste.getVideo()+"\" title=\"Como Calcular O IMC (Índice De Massa Corporal) + Tabela De Referência | Dicas De Nutrição\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
            videoTutorial.loadData(iFrame, "text/html", "utf-8");
            videoTutorial.getSettings().setJavaScriptEnabled(true);
            videoTutorial.setWebChromeClient(new WebChromeClient());
            //getFieldsFromFirebase();

            textTitle.setText(teste.getTitulo());
            salvar = findViewById(R.id.buttonSalvar);

            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    AvaliacaoResultado resultado = new AvaliacaoResultado();
                    resultado.setAluno(alunoId);

                    String labelText;
                    String boxText;

                    for(int i=0;i<labels.size();i++){
                        labelText = labels.get(i).getText().toString();
                        boxText = boxes.get(i).getText().toString();

                        if (labelText.isEmpty() || boxText.isEmpty()) {
                            Toast.makeText(TesteActivity.this, "Não deixe campos de informação vazios", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        map.put(labels.get(i).getText().toString().toLowerCase(), boxes.get(i).getText().toString());
                    }

                    resultado.setProfessor(user.getUid());
                    resultado.setCampos(map);
                    resultado.setTitulo(teste.getTitulo());
                    resultado.setTipo(teste.getTipo());
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    resultado.setData(date);

                    CollectionReference collectionReference = db.collection("Usuario");
                    Query query = collectionReference.whereEqualTo("id", alunoId);

                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentSnapshot document : value.getDocuments()) {
                                Aluno ob = document.toObject(Aluno.class);
                                ob.setUid(document.getId().toString());
                                String message = "";
                                double calculo = 0;
                                for(String key : resultado.getCampos().keySet()){
                                    calculo = Double.parseDouble(resultado.getCampos().get(key));
                                }

                                if(resultado.getTitulo().equals("IMC")) {
                                    calculo = ClassificadorApFRS.imcCalc(Float.parseFloat(resultado.getCampos().get("massa")), Float.parseFloat(resultado.getCampos().get("altura")));
                                }
                                if(resultado.getTitulo().equals("RCE")) {
                                    calculo = ClassificadorApFRS.rCE(Float.parseFloat(resultado.getCampos().get("cintura")), Float.parseFloat(resultado.getCampos().get("estatura")));
                                }

                                if(resultado.getTipo().equals("Antropometria")){
                                    message = classificadorAntropometria.direcionadorTeste(resultado.getTitulo(), ob.getGenero(), ob.getIdade(), calculo);
                                }
                                if(resultado.getTipo().equals("ApFRS")){
                                    message = classificadorApFRS.direcionadorTeste(resultado.getTitulo(), ob.getGenero(), ob.getIdade(), calculo);
                                }

                                resultado.setMessage(message);
                                DAOTestes.createNewAvaliacao(resultado);
                            }
                        }
                    });

                    Toast.makeText(TesteActivity.this, "Sucesso em cadastrar novo teste", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TesteActivity.this, MainActivity.class));
                }
            });

            generateForm();
        }
    }

    private void generateForm(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.container);
        Map<String, String> campos = teste.getCampos();

        for(String key : campos.keySet()){
            if(!key.equals("status")){
                linearLayout.addView(createLabel(key));
                linearLayout.addView(createBox(campos.get(key)));
            }

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

    public void voltar(View v){
        finish();
    }

}