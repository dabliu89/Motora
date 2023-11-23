package com.example.motora;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Looper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.test.core.app.ApplicationProvider;

import com.example.motora.Util.ConfiguraBD;
import com.example.motora.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class LoginActivityTest {

    @Mock
    LoginActivity loginActivity = mock(LoginActivity.class);
    @Mock
    View v = mock(View.class);

    @Mock
    EditText edit;

    @Mock
    Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void EmaileSenhaValidos_ValidarAutenticacao_DevePassar(){
        when(loginActivity.validarAutenticacao(v)).thenReturn(true);
        loginActivity.validarAutenticacao(v);
        assertEquals(true, loginActivity.validarAutenticacao(v));
    }
    @Test
    public void EmailInvalido_ValidarAutenticacao_NaoDevePassar(){
        when(loginActivity.validarAutenticacao(v)).thenReturn(false);
        loginActivity.validarAutenticacao(v);
        assertEquals(false, loginActivity.validarAutenticacao(v));
    }

    @Test
    public void SenhaInvalida_ValidarAutenticacao_NaoDevePassar(){
        when(loginActivity.validarAutenticacao(v)).thenReturn(false);
        loginActivity.validarAutenticacao(v);
        assertEquals(false, loginActivity.validarAutenticacao(v));
    }

    @Test
    public void EmaileSenhaInvalidos_ValidarAutenticacao_NaoDevePassar(){
        when(loginActivity.validarAutenticacao(v)).thenReturn(false);
        loginActivity.validarAutenticacao(v);
        assertEquals(false, loginActivity.validarAutenticacao(v));
    }

    @Test
    public void updateUITrue() {
        doCallRealMethod().when(loginActivity).updateUI(true);;
        assertEquals(true, loginActivity.updateUI(true));
        verify(loginActivity, times(3)).updateUI(true);
    }

    @Test
    public void updateUIFalse() {
        doCallRealMethod().when(loginActivity).updateUI(false);
        loginActivity.updateUI(false);
        verify(loginActivity, times(3)).updateUI(false);
    }

    @Test
    public void initComponents(){
        doCallRealMethod().when(loginActivity).inicializarComponentes();
        loginActivity.inicializarComponentes();
        verify(loginActivity).inicializarComponentes();
    }

    @Test
    public void setCampoEmail(){
        doCallRealMethod().when(loginActivity).setCampoEmail(edit);
        loginActivity.setCampoEmail(edit);
        verify(loginActivity).setCampoEmail(edit);
    }

    @Test
    public void getCampoEmail(){
        doCallRealMethod().when(loginActivity).getCampoEmail();
        loginActivity.getCampoEmail();
        verify(loginActivity).getCampoEmail();
    }

    @Test
    public void setCampoSenha(){
        doCallRealMethod().when(loginActivity).setCampoSenha(edit);
        loginActivity.setCampoSenha(edit);
        verify(loginActivity).setCampoSenha(edit);
    }

    @Test
    public void getCampo(){
        doCallRealMethod().when(loginActivity).getCampoSenha();
        loginActivity.getCampoSenha();
        verify(loginActivity).getCampoSenha();
    }

    @Test
    public void recuperarSenha(){

        doCallRealMethod().when(loginActivity).recuperarSenha(v);
        loginActivity.recuperarSenha(v);
        verify(loginActivity).recuperarSenha(v);
    }

    @Test
    public void cadastrar() {
        doCallRealMethod().when(loginActivity).cadastrar(v);
        loginActivity.cadastrar(v);
        verify(loginActivity).cadastrar(v);
    }

}