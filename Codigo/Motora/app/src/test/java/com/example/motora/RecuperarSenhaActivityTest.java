package com.example.motora;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecuperarSenhaActivityTest {

    @Mock
    RecuperarSenhaActivity recuperarSenhaActivity = mock(RecuperarSenhaActivity.class);

    @Mock
    View v;

    @Mock
    Context context;
    @Mock
    ProgressBar progressBar;
    @Mock
    Button button;
    @Mock
    EditText edit;

    @Mock
    FirebaseAuth firebaseAuth;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recuperarSenhaActivity.startActivity(new Intent(recuperarSenhaActivity, RecuperarSenhaActivity.class));
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void voltarRecuperarSenha() {
        doCallRealMethod().when(recuperarSenhaActivity).voltarRecuperarSenha(v);
        recuperarSenhaActivity.voltarRecuperarSenha(v);
        verify(recuperarSenhaActivity).voltarRecuperarSenha(v);
    }

    @Test
    public void CampoEmailPreenchido(){
        //doCallRealMethod().when(recuperarSenhaActivity).decision("teste@teste.com");
        recuperarSenhaActivity.campoEmailRec = edit;
        recuperarSenhaActivity.campoEmailRec.setText("teste@teste.com");
        recuperarSenhaActivity.decision("teste@teste.com");
        verify(recuperarSenhaActivity).decision("teste@teste.com");
        verify(recuperarSenhaActivity).resetarSenha();
    }

    @Test
    public void CampoEmailNaoPreenchido(){
        //doCallRealMethod().when(recuperarSenhaActivity).decision("teste@teste.com");
        recuperarSenhaActivity.campoEmailRec = edit;
        recuperarSenhaActivity.decision("");
        verify(recuperarSenhaActivity).decision("");
        assertEquals("Este campo não deve ficar vazio", recuperarSenhaActivity.campoEmailRec.getError());
    }

    @Test
    public void finalizarComSucesso(){
        doCallRealMethod().when(recuperarSenhaActivity).finalizar(true);
        recuperarSenhaActivity.finalizar(true);
        verify(recuperarSenhaActivity).finalizar(true);
    }

    @Test
    public void finalizarComFalha(){
        doCallRealMethod().when(recuperarSenhaActivity).finalizar(false);
        recuperarSenhaActivity.progressBar = progressBar;
        recuperarSenhaActivity.btnRecSen = button;
        recuperarSenhaActivity.finalizar(false);
        verify(recuperarSenhaActivity).finalizar(false);
    }

//    @Test
//    public void recuperarSenha(){
//        doCallRealMethod().when(recuperarSenhaActivity).resetarSenha();
//        recuperarSenhaActivity.progressBar = progressBar;
//        recuperarSenhaActivity.btnRecSen = button;
//        recuperarSenhaActivity.mAuth = firebaseAuth.getInstance();
//        recuperarSenhaActivity.resetarSenha();
//        verify(recuperarSenhaActivity).resetarSenha();
//    }




}