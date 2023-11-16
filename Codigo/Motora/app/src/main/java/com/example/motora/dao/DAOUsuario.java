package com.example.motora.dao;

import static androidx.test.InstrumentationRegistry.getContext;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.motora.LoginActivity;
import com.example.motora.Util.ConfiguraBD;
import com.example.motora.model.Aluno;
import com.example.motora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DAOUsuario {

    private DatabaseReference databaseReference;
    private static FirebaseAuth auth = ConfiguraBD.FirebaseAutenticacao();;

    static boolean successfulLog;

    public DAOUsuario(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Usuario.class.getSimpleName());
    }

    public static boolean authenticate(Usuario usuario){
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    successfulLog = true;
                }else{
                    String excecao;
                    try{
                        throw Objects.requireNonNull(task.getException());
                    }catch(FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não cadastrado";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou senha incorreto";
                    }catch(Exception e){
                        excecao = "Erro ao logar o usuário " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), excecao, Toast.LENGTH_SHORT).show();
                    successfulLog = false;
                }
            }
        });

        return successfulLog;
    }

    public Task<Void> add(Usuario usuario){
        return databaseReference.push().setValue(usuario);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key){
        return databaseReference.child(key);
    }

    public ArrayList<Aluno> getListAlunos(ArrayList<Aluno> alunos){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference tipos = db.collection("Usuario");
        com.google.firebase.firestore.Query query = tipos.whereEqualTo("papel", "Aluno");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    Aluno ob = document.toObject(Aluno.class);
                    ob.setUid(document.getId().toString());
                    alunos.add(ob);
                }
            }
        });


        return alunos;
    }
}

