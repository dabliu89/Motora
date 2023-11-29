package com.example.motora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.motora.Util.ConfiguraBD;
import com.example.motora.dao.DAOUsuario;
import com.example.motora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import android.graphics.Paint;
import android.widget.TextView;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText campoEmail, campoSenha;
    Button botaoAcessar;

    private FirebaseAuth auth;

    private static Context context;

    public static boolean active = false;

    private static int openCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.textViewFazerCadastro);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        SpannableString spannableString = new SpannableString(textView.getText());
        int corSublinhado = Color.parseColor("#5D9C76");
        ForegroundColorSpan corSpan = new ForegroundColorSpan(corSublinhado);
        spannableString.setSpan(corSpan, 0, textView.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);

        context = getBaseContext();


        auth = ConfiguraBD.FirebaseAutenticacao();

        inicializarComponentes();
    }

    public boolean validarAutenticacao(View v){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                boolean result = DAOUsuario.authenticate(usuario);
                updateUI(result);
                return true;
            }else{
                showMessage("Preencha o campo Senha");
                return false;
            }
        }else{
            showMessage("Preencha o campo Email");
            return false;
        }
    }

    private static void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static boolean updateUI(boolean successfulLog){

        if(successfulLog){
            showMessage("Login realizado com sucesso!");
            abrirHome();
        }else {
            showMessage("Espere um pouco... (Se demorar demais, então é porque o login não foi realizado)");
        }
        return true;
    }

    public static void abrirHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void abrirVisaoAluno(){
        Intent intent = new Intent(context, VisaoAluno.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void cadastrar(View v){
        Intent intent = new Intent(this, CadastroActivity.class);
        this.startActivity(intent);
    }

    public void recuperarSenha(View v){
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        this.startActivity(intent);
    }

    protected void onStart(){
        super.onStart();
        active = true;

        FirebaseUser usuarioAuth = auth.getCurrentUser();
        if(usuarioAuth != null){
            //DAOUsuario.getPapelUser(usuarioAuth.getUid());
            abrirHome();
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        active=false;
    }

    public boolean inicializarComponentes(){
        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenhaLogin);
        botaoAcessar = findViewById(R.id.buttonLogar);

        return true;
    }

    public EditText getCampoEmail() {
        return campoEmail;
    }

    public boolean setCampoEmail(EditText campoEmail) {
        this.campoEmail = campoEmail;
        return true;
    }

    public EditText getCampoSenha() {
        return campoSenha;
    }

    public void setCampoSenha(EditText campoSenha) {
        this.campoSenha = campoSenha;
    }
}