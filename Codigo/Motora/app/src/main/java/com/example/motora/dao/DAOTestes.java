package com.example.motora.dao;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class DAOTestes {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<String> getListasFirestore(String collection, String field){
        CollectionReference tipos = db.collection(collection);
        Query query = tipos.orderBy("nome");
        ArrayList<String> listTipos = new ArrayList<String>();

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> temp = new ArrayList<>();
                for (DocumentSnapshot document : value.getDocuments()) {
                    temp.add(document.getString(field));
                }
                listTipos.addAll((Collection<String>) temp);
            }
        });

        return listTipos;
    }
}
