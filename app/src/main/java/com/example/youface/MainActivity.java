package com.example.youface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import vista.Perfil;
import vista.Subir;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;

                switch (item.getItemId()){

                    case R.id.navigation_inicio:


                        break;
                    case R.id.navigation_subir:

                        intent = new Intent(MainActivity.this, Subir.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_Yo:

                        intent = new Intent(MainActivity.this, Perfil.class);
                        startActivity(intent);
                        break;

                }

                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.button_mensaje, menu);

        return true;
    }
}
