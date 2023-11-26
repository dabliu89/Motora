package com.example.motora;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.motora.ui.perfil.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.motora.databinding.ActivityMainBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.example.motora.Util.ConfiguraBD;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements PerfilFragment.OnAccountDeletedListener{

    private ActivityMainBinding binding;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_avaliacoes, R.id.navigation_perfil, R.id.navigation_report)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        auth = ConfiguraBD.FirebaseAutenticacao();
    }

    public void deslogar(View v){
        try{
            auth.signOut();
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAccountDeleted() {
        // Feche o fragmento de perfil
        finish();
    }
}