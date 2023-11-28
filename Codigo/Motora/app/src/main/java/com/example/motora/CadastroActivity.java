package com.example.motora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motora.Util.ConfiguraBD;
import com.example.motora.dao.DAOUsuario;
import com.example.motora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autenticacao;
    EditText campoNome, campoEmail, campoSenha, campoConfSenha, campoIdade, campoNomeProfRes;
    TextView tvTemConta, tvNome, tvEmail, tvSenha, tvConfSenha, tvIdade, tvNomeProfRes;
    DAOUsuario dao;
    FirebaseFirestore db;
    String nome, email, senha, confSenha, nomeProfRes;
    int idade;
    String[] papel = {"Professor", "Aluno"};
    String[] genero = {"Masculino", "Feminino"};
    String escolha = "", escolha2 = "";
    LinearLayout llPrincipal;
    TextInputLayout textInputLayoutPapel;
    AutoCompleteTextView autoCompleteTextViewPapel;
    ArrayAdapter<String> adapterItemsPapel;

    TextInputLayout textInputLayoutGenero;
    AutoCompleteTextView autoCompleteTextViewGenero;
    ArrayAdapter<String> adapterItemsGenero;

    LinearLayout linearLayoutIG;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        TextView textView = findViewById(R.id.textViewFazerLogin);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        SpannableString spannableString = new SpannableString(textView.getText());
        int corSublinhado = Color.parseColor("#5D9C76");
        ForegroundColorSpan corSpan = new ForegroundColorSpan(corSublinhado);
        spannableString.setSpan(corSpan, 0, textView.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);

        dao = new DAOUsuario();

        db = FirebaseFirestore.getInstance();

        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextE_mail);
        campoSenha = findViewById(R.id.editTextSenha);
        campoConfSenha = findViewById(R.id.editTextConfirmarSenha);
        campoIdade = findViewById(R.id.editTextIdade);
        campoNomeProfRes = findViewById(R.id.editTextNomeDoProfessorRes);

        llPrincipal = findViewById(R.id.linearLayoutPrincipal);
        tvTemConta = findViewById(R.id.textViewTemConta);
        tvNome = findViewById(R.id.textViewNome);
        tvEmail = findViewById(R.id.textViewEmail);
        tvSenha = findViewById(R.id.textViewSenha);
        tvConfSenha = findViewById(R.id.textViewConfSenha);
        tvIdade = findViewById(R.id.textViewIdade);
        tvNomeProfRes = findViewById(R.id.textViewNomeDoProfessorRes);

        linearLayoutIG = findViewById(R.id.linearLayoutIG);

        textInputLayoutPapel = findViewById(R.id.textInputLayoutPapel);
        textInputLayoutGenero = findViewById(R.id.textInputLayoutGenero);

        autoCompleteTextViewPapel = findViewById(R.id.auto_complete_text);
        adapterItemsPapel = new ArrayAdapter<>(this, R.layout.list_item_layout, papel);

        autoCompleteTextViewGenero = findViewById(R.id.auto_complete_text_genero);
        adapterItemsGenero = new ArrayAdapter<>(this, R.layout.list_item_layout, genero);

        autoCompleteTextViewPapel.setAdapter(adapterItemsPapel);
        autoCompleteTextViewGenero.setAdapter(adapterItemsGenero);
        //autoCompleteTextViewPapel.setDropDownVerticalOffset(0);
        textInputLayoutPapel.setHintEnabled(false);
        textInputLayoutGenero.setHintEnabled(false);

        autoCompleteTextViewPapel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //textInputLayoutPapel.setHintEnabled(false);
                String item = adapterView.getItemAtPosition(i).toString();
                escolha = item;
                if ("Professor".equals(item)) {
                    llPrincipal.setBackgroundColor(Color.BLACK);
                    tvTemConta.setTextAppearance(R.style.EstiloProfessor);
                    tvNome.setTextAppearance(R.style.EstiloProfessor);
                    campoNome.setTextAppearance(R.style.EstiloProfessor);
                    tvEmail.setTextAppearance(R.style.EstiloProfessor);
                    campoEmail.setTextAppearance(R.style.EstiloProfessor);
                    tvSenha.setTextAppearance(R.style.EstiloProfessor);
                    campoSenha.setTextAppearance(R.style.EstiloProfessor);
                    tvConfSenha.setTextAppearance(R.style.EstiloProfessor);
                    campoConfSenha.setTextAppearance(R.style.EstiloProfessor);
                    //autoCompleteTextViewPapel.setTextAppearance(R.style.CustomAutoCompleteTextViewStyleWhite);
                    autoCompleteTextViewPapel.setTextAppearance(R.style.EstiloProfessor);

                    tvNomeProfRes.setVisibility(View.GONE);
                    campoNomeProfRes.setVisibility(View.GONE);
                    linearLayoutIG.setVisibility(View.GONE);

                    mudarLayoutPapel(item);
                    atualizarCorStatusBar(true);
                    //autoCompleteTextViewPapel.setTextAppearance(R.style.CustomAutoCompleteTextViewStyleWhite);

                    textInputLayoutPapel.setEndIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                } else if ("Aluno".equals(item)) {
                    llPrincipal.setBackgroundColor(Color.WHITE);
                    tvTemConta.setTextAppearance(R.style.EstiloNormal);
                    tvNome.setTextAppearance(R.style.EstiloAluno);
                    campoNome.setTextAppearance(R.style.EstiloAluno);
                    tvEmail.setTextAppearance(R.style.EstiloAluno);
                    campoEmail.setTextAppearance(R.style.EstiloAluno);
                    tvSenha.setTextAppearance(R.style.EstiloAluno);
                    campoSenha.setTextAppearance(R.style.EstiloAluno);
                    tvConfSenha.setTextAppearance(R.style.EstiloAluno);
                    campoConfSenha.setTextAppearance(R.style.EstiloAluno);
                    tvIdade.setTextAppearance(R.style.EstiloAluno);
                    campoIdade.setTextAppearance(R.style.EstiloAluno);
                    tvNomeProfRes.setTextAppearance(R.style.EstiloAluno);
                    campoNomeProfRes.setTextAppearance(R.style.EstiloAluno);
                    //autoCompleteTextViewPapel.setTextAppearance(R.style.CustomAutoCompleteTextViewStyleBlack);
                    autoCompleteTextViewPapel.setTextAppearance(R.style.EstiloAluno);

                    tvNomeProfRes.setVisibility(View.VISIBLE);
                    campoNomeProfRes.setVisibility(View.VISIBLE);
                    linearLayoutIG.setVisibility(View.VISIBLE);

                    mudarLayoutPapel(item);
                    atualizarCorStatusBar(false);
                    //autoCompleteTextViewPapel.setTextAppearance(R.style.CustomAutoCompleteTextViewStyleBlack);

                    textInputLayoutPapel.setEndIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));
                }
                Toast.makeText(CadastroActivity.this, "Opção escolhida: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewGenero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //textInputLayoutPapel.setHintEnabled(false);
                String item = adapterView.getItemAtPosition(i).toString();
                escolha2 = item;
                Toast.makeText(CadastroActivity.this, "Opção escolhida: "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mudarLayoutPapel(String item){
        if ("Aluno".equals(item)) {
            adapterItemsPapel = new ArrayAdapter<>(this, R.layout.list_item_layout_black, papel);
            autoCompleteTextViewPapel.setAdapter(adapterItemsPapel);
            autoCompleteTextViewPapel.setDropDownBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_dropdown_background_black));
        } else if ("Professor".equals(item)) {
            adapterItemsPapel = new ArrayAdapter<>(this, R.layout.list_item_layout, papel);
            autoCompleteTextViewPapel.setAdapter(adapterItemsPapel);
            autoCompleteTextViewPapel.setDropDownBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_dropdown_background_white));
        }
    }

    private void atualizarCorStatusBar(boolean isProfessor) {
        Window window = getWindow();
        View decor = window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Se a versão do Android é Lollipop ou superior
            if (isProfessor) {
                window.setStatusBarColor(getResources().getColor(R.color.cor_status_bar_professor));
                decor.setSystemUiVisibility(0);
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.cor_status_bar_aluno));
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public void termos(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);

        builder.setTitle("Termos de Uso");
        builder.setMessage("Política de Segurança do Motora\n" +
                "\n" +
                "Última Atualização: 28/11/2023\n" +
                "\n" +
                "Bem-vindo ao Motora!\n" +
                "\n" +
                "Esta Política de Segurança descreve como tratamos a segurança das informações coletadas ou recebidas quando você usa nosso aplicativo e explica as medidas que tomamos para proteger essas informações.\n" +
                "\n" +
                "Coleta de Informações:\n" +
                "\n" +
                "O Motora pode coletar informações limitadas e relevantes necessárias para fornecer e melhorar nossos serviços. Isso pode incluir informações do dispositivo, como modelo, sistema operacional e versão do aplicativo.\n" +
                "\n" +
                "Uso de Informações:\n" +
                "\n" +
                "As informações coletadas são utilizadas para operar, manter e melhorar a segurança do aplicativo. Elas podem ser usadas para personalizar sua experiência e fornecer atualizações relevantes.\n" +
                "\n" +
                "Compartilhamento de Informações:\n" +
                "\n" +
                "Não compartilhamos informações pessoais identificáveis com terceiros, a menos que seja necessário para fornecer nossos serviços ou quando exigido por lei.\n" +
                "\n" +
                "Segurança de Dados:\n" +
                "\n" +
                "Implementamos medidas de segurança para proteger suas informações contra acesso não autorizado, alteração, divulgação ou destruição não autorizada. Isso inclui a criptografia de dados sensíveis.\n" +
                "\n" +
                "Atualizações da Política de Segurança:\n" +
                "\n" +
                "Podemos fazer alterações nesta Política de Segurança periodicamente. A data da última atualização será indicada no início do documento. Recomendamos que você reveja esta política regularmente.\n" +
                "\n" +
                "Contato:\n" +
                "\n" +
                "Se você tiver dúvidas ou preocupações sobre nossa Política de Segurança, entre em contato conosco em equipemotora@hotmail.com.\n" +
                "Ao utilizar o Motora, você concorda com os termos desta Política de Segurança.\n" +
                "\n" +
                "Obrigado por confiar no Motora!\n" +
                "\n" +
                "Ass: Equipe de Desenvolvimento do Motora");
        builder.setCancelable(false);

        builder.setPositiveButton("Concordo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                validarCampos();
            }
        }).setNegativeButton("Não Concordo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    public void validarCampos(){
        nome = campoNome.getText().toString();
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();
        confSenha = campoConfSenha.getText().toString();
        if(campoIdade.getText().toString().equals("") || campoIdade.getText().toString().equals(null)){
            idade = 0;
        }else{
            idade = Integer.parseInt(campoIdade.getText().toString());
        }
        nomeProfRes = campoNomeProfRes.getText().toString();

        if(verNome(nome) && verIdade() && verGenero() && verProfRes()){
            if(!email.isEmpty()){
                if(senha.length() >= 8){
                    if(!confSenha.equals(senha)){
                        Toast.makeText(this, "Preencha o campo Confirme sua senha corretamente", Toast.LENGTH_SHORT).show();
                    }else{

                        if(!escolha.isEmpty() && (escolha.equals("Professor") || escolha.equals("professor"))){
                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            usuario.setPapel(escolha);
                            usuario.setIdade(idade);
                            usuario.setGenero(null);
                            usuario.setNomeProfRes(null);
                        }else if(!escolha.isEmpty() && (escolha.equals("Aluno") || escolha.equals("aluno"))){
                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            usuario.setPapel(escolha);
                            usuario.setIdade(idade);
                            usuario.setGenero(escolha2);
                            usuario.setNomeProfRes(nomeProfRes);
                        }else{
                            Toast.makeText(this, "Por favor selecione o papel que deseja representar nesta aplicação", Toast.LENGTH_SHORT).show();
                        }

                        cadastrarUsuario();
                    }
                }else{
                    Toast.makeText(this, "Preencha o campo Senha com no mínimo 8 dígitos e no máximo 16", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha o campo E-mail", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o campo Nome", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verNome(String nomes) {
        for(int i = 0; i < nomes.length(); ++i) {
            char ch = nomes.charAt(i);

            if(i < nomes.length()-1){
                char ch1 = nomes.charAt(i + 1);

                if (ch == ' ' && ch1 == ' ') {
                    return false;
                }
            }
        }

        if (nomes.length() == 0) {
            return false;
        } else {
            for (int i = 0; i < nomes.length(); ++i) {
                char ch = nomes.charAt(i);

                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= 'á' && ch <= 'ú') || (ch >= 'Á' && ch <= 'Ú') || ch == 'ã' || ch == 'õ' || ch == ' ') || (i == 0 && ch == ' ')) {
                    Toast.makeText(this, "Preencha o(s) campo(s) de nome apropriadamente (Verifique espaços indesejados)", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }

    private boolean verIdade(){
        if(escolha.equals("Professor")){
            return true;
        } else if (escolha.equals("Aluno")) {
            if(idade > 17 || idade < 6){
                Toast.makeText(CadastroActivity.this, "Este App foi desenvolvido para atender a professores de educação física e alunos com a idade entre 6 e 17 anos", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private boolean verGenero(){
        if(escolha.equals("Professor")){
            return true;
        } else if (escolha.equals("Aluno")) {
            if(escolha2.isEmpty()){
                return false;
            }
        }

        return true;
    }

    private boolean verProfRes(){
        if(escolha.equals("Professor")){
            return true;
        } else if (escolha.equals("Aluno")) {
            verNome(nomeProfRes);
        }

        return true;
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguraBD.FirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar o usuário", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = autenticacao.getCurrentUser();
                    updateUI(user);

                }else{
                    String excecao;

                    try{
                        throw Objects.requireNonNull(task.getException());
                    }catch(FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um email válido";
                    }catch(FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta já existe";
                    }catch(Exception e){
                        excecao = "Erro ao cadastrar usuário " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Map<String, Object> doc = new HashMap<>();
            doc.put("id", user.getUid());
            doc.put("nome", usuario.getNome());
            doc.put("email", usuario.getEmail());
            doc.put("senha", usuario.getSenha());
            doc.put("papel", usuario.getPapel());
            doc.put("idade", usuario.getIdade());
            doc.put("genero", usuario.getGenero());
            doc.put("prof_responsavel", usuario.getNomeProfRes());

            db.collection("Usuario").document(user.getUid()).set(doc)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CadastroActivity.this, "Dados armazenados...", Toast.LENGTH_SHORT).show();
                            abrirHome();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CadastroActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Usuário nulo", Toast.LENGTH_SHORT).show();
        }


    }

    private void abrirHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void voltarCadastro(View v){
        finish();
    }

}