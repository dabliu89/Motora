package com.example.motora.dao;

import androidx.annotation.Nullable;

import com.example.motora.model.Aluno;
import com.example.motora.model.Usuario;
import com.google.android.gms.tasks.Task;
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

public class DAOUsuario {

    private DatabaseReference databaseReference;

    public DAOUsuario(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Usuario.class.getSimpleName());
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

