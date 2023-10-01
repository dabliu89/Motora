package com.example.motora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenhaActivity extends AppCompatActivity {

    Button btnRecSen;
    EditText campoEmailRec;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        btnRecSen = findViewById(R.id.buttonRecuperarSenha);
        campoEmailRec = findViewById(R.id.editTextE_mailRecuperarSenha);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnRecSen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = campoEmailRec.getText().toString().trim();
                if(!TextUtils.isEmpty(strEmail)){
                    resetarSenha();
                }else{
                    campoEmailRec.setError("Este campo não ficar vazio");
                }
            }
        });
    }

    private void resetarSenha(){
        progressBar.setVisibility(View.VISIBLE);
        btnRecSen.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RecuperarSenhaActivity.this, "Um link para alterar sua senha foi mandado para o e-mail registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecuperarSenhaActivity.this, "Error: - "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        btnRecSen.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void voltarRecuperarSenha(View v) {
        finish();
    }
}