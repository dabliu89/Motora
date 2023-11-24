package com.example.motora.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.motora.model.Teste;
import com.example.motora.model.classificadores.ClassificadorApFRS;
import com.example.motora.model.AvaliacaoResultado;
import com.example.motora.model.Teste;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class DAOTestes {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<Teste> t = new ArrayList<Teste>();

    public DAOTestes(){super();}


    public static ArrayList<Teste> getTestes(ArrayList<Teste> list) {
        CollectionReference testes = db.collection("Avaliacoes");
        Query query = testes.orderBy("titulo");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    Teste ob = document.toObject(Teste.class);
                    ob.setId(document.getId());
                    list.add(ob);
                }
            }
        });

        return list;
    }

    public static void getTesteFirebase(String testeId){
        DocumentReference testes = db.collection("Avaliacoes").document(testeId);

        Source source = Source.CACHE;


        testes.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Teste ob = document.toObject(Teste.class);
                    ob.setId(document.getId());
                    t.add(ob);
                }
                else{
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });
    }
//
//    private void setT(Teste teste){
//        t.add(teste);
//        Log.d(TAG, t.toString());
//    }
    private void setT(Teste teste){
        t.add(teste);
        Log.d(TAG, t.toString());
    }

    public static void createNewAvaliacao(ClassificadorApFRS resultado){
        db.collection("AvaliacoesResultados").add(resultado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        ;
    }
}
