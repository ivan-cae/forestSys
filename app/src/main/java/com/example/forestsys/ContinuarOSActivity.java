package com.example.forestsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ContinuarOSActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Button iniciarColeta;
    private Button tracarRota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuar_os);

        iniciarColeta = findViewById(R.id.botao_iniciar_coleta);
        tracarRota = findViewById(R.id.botao_tracar_rota);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Fulano");

        drawer = findViewById(R.id.drawer_layout_continuar);
        NavigationView navigationView = findViewById(R.id.nav_view_continuar);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        iniciarColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ContinuarOSActivity.this, IniciarColetaActivity.class);
                startActivity(it);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                Intent it1 = new Intent(this, DashboardActivity.class);
                startActivity(it1);
                break;

            case R.id.os:
                Intent it2 = new Intent(this, MainActivity.class);
                startActivity(it2);
                break;

            case R.id.config:
                Toast.makeText(this, "Clicou nas configs", Toast.LENGTH_LONG).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
