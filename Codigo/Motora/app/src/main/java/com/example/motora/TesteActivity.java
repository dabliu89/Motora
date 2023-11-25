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

import com.example.motora.dao.DAOTestes;
import com.example.motora.model.AvaliacaoResultado;
import com.example.motora.model.Teste;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TesteActivity extends AppCompatActivity {

    public String nomeTeste;
    String alunoId;

    private Teste teste;

    private Button salvar;

    private FirebaseFirestore db;

    TextView textTitle;

    private DAOTestes daoTestes = new DAOTestes();

    private ArrayList<TextView> labels = new ArrayList<>();
    private ArrayList<EditText> boxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        textTitle = findViewById(R.id.textTitleAval);

        alunoId = this.getIntent().getExtras().get("alunoId").toString();

        DAOTestes.getTesteFirebase(this.getIntent().getExtras().get("testeId").toString());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(DAOTestes.t.size() == 0){
            textTitle.setText(DAOTestes.t.size()+"true");
            this.recreate();
        }
        else{
            textTitle.setText(DAOTestes.t.size()+"");

            teste = DAOTestes.t.get(DAOTestes.t.size()-1);

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

                    for(int i=0;i<labels.size();i++){
                        map.put(labels.get(i).getText().toString().toLowerCase(), boxes.get(i).getText().toString());
                    }
                    resultado.setCampos(map);
                    resultado.setTitulo(teste.getTitulo());
                    resultado.setTipo(teste.getTipo());
                    DAOTestes.createNewAvaliacao(resultado);

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


}