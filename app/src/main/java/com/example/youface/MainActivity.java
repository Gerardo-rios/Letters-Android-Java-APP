package com.example.youface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Controlador.AdaptadorHomePosts;
import Controlador.PostVolley;
import Interfaces.sync;
import Modelo.Post;
import vista.Perfil;
import vista.Subir;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    AdaptadorHomePosts adapter;
    PostVolley volley = new PostVolley(this);
    List<Post> lista;
    String nlik, ncom;

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

        recycler = findViewById(R.id.recicler_home);
        cargar_posts();

    }

    private void cargar_posts(){

        volley.ListarPosts(new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    boolean s = json.getBoolean("success");
                    if (s){
                        JSONArray data = json.optJSONArray("data");
                        lista = new ArrayList<>();
                        for (int i=0; i<data.length(); i++){
                            lista.add(new Post(data.getJSONObject(i)));
                        }
                        //conteo();
                        CargarRecycler();
                    } else {
                        Toast.makeText(MainActivity.this, "No se pueden cargar las noticias", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("ERROR", "NO SE PUDO OBTENER DATA");
                    e.printStackTrace();
                }

            }
        });

    }

    private void CargarRecycler(){

        adapter = new AdaptadorHomePosts(lista, this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

    }
/*
    private void conteo(){

        for (int i = 0; i < lista.size(); i++){
            final Post pos = lista.get(i);
            volley.ContarLikes_Coments(pos, new sync() {
                @Override
                public void response(JSONObject json) {

                    try {
                        JSONArray a = json.getJSONArray("like");
                        JSONArray b = json.getJSONArray("coment");
                        String nu = a.getJSONObject(0).getString("likes");
                        String ne = b.getJSONObject(0).getString("comentarios");
                        pos.setN_likes(nu);
                        pos.setN_coments(ne);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("nose pudo", "obtener string");
                    }

                }
            });

        }
    }

*/


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.button_mensaje, menu);

        return true;
    }*/
}
