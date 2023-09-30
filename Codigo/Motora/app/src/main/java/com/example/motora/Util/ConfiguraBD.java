package com.example.motora.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguraBD {
    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAutenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}