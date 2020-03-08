package vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youface.MainActivity;
import com.example.youface.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Controlador.FollowVolley;
import Controlador.GridAdapter;
import Controlador.PostVolley;
import Controlador.ip;
import Interfaces.sync;
import Modelo.Post;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    ImageView foto_perfil;
    TextView n_posts, n_seguidores, n_seguidos, nombre, descripcion;
    Button editar;
    GridView texts_posts;
    GridAdapter adapter;
    FollowVolley voly = new FollowVolley(this);
    PostVolley pvol = new PostVolley(this);
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cargarNav();
        obtener_componentes_visuales();
        DatosdeUser();
        seguidores();
        n_posteos();
    }

    private void cargarNav(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;

                switch (item.getItemId()){

                    case R.id.navigation_inicio:

                        intent = new Intent(Perfil.this, MainActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.navigation_subir:

                        intent = new Intent(Perfil.this, Subir.class);
                        startActivity(intent);

                        break;

                    case R.id.navigation_Yo:

                        break;

                }

                return false;
            }
        });
    }

    private void obtener_componentes_visuales(){

        foto_perfil = findViewById(R.id.foto_perfil);
        n_posts = findViewById(R.id.num_publi);
        n_seguidores = findViewById(R.id.num_seguidores);
        n_seguidos = findViewById(R.id.num_seguidos);
        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        texts_posts = findViewById(R.id.fotos_usuario);
        editar = findViewById(R.id.btn_editar_perfil);
        editar.setOnClickListener(this);

    }

    private void DatosdeUser(){

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        userid = preferences.getString("id", "");
        nombre.setText(preferences.getString("nombre", "Edita tu nombre"));
        this.setTitle(preferences.getString("username", ""));
        String descri = preferences.getString("descripcion", "Pon una bio");
        descripcion.setText(descri);
        String foto = preferences.getString("foto", "default_user.pbg");
        Picasso.get().load(ip.public_images() + foto).into(foto_perfil);
    }


    private void cargarGrid(ArrayList<String> postes, final ArrayList<String> posteid){

        adapter = new GridAdapter(this, postes);
        texts_posts.setAdapter(adapter);

        texts_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Perfil.this, PostDetallesActivity.class);
                intent.putExtra("Recurso", adapter.getItem(position).toString());
                intent.putExtra("Posteid", posteid.get(position).toString());
                startActivity(intent);

            }
        });

    }

    private void seguidores(){

        voly.Contar_S(userid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    JSONArray a = json.getJSONArray("seguidores");
                    JSONArray b = json.getJSONArray("seguidos");
                    String nu = a.getJSONObject(0).getString("numero_seguidores");
                    String ne = b.getJSONObject(0).getString("numero_seguidos");
                    String l = nu + "\n" + "Seguidores";
                    String x = ne + "\n" + "Seguidos";
                    n_seguidores.setText(l);
                    n_seguidos.setText(x);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("nose pudo", "obtener string");
                }

            }
        });

    }


    private void n_posteos(){

        pvol.List_Posts_User(userid, new sync() {
            @Override
            public void response(JSONObject json) {
                try {
                    if (json.getBoolean("success")){
                        JSONArray a = json.getJSONArray("data");
                        String nu = String.valueOf(a.length());
                        String l = nu + "\n" + "Posts";
                        n_posts.setText(l);
                        ArrayList<String> posts = new ArrayList<>();
                        ArrayList<String> posteid = new ArrayList<>();
                        for (int i=0; i<a.length(); i++){
                            JSONObject j = a.getJSONObject(i);
                            posts.add(j.getString("contenido"));
                            posteid.add(j.getString("post_id"));
                        }
                        cargarGrid(posts, posteid);
                    } else {
                        Log.e("e", "Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error al obtener posts", "No se pudo obtener valores");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_editar_perfil:

                Intent i = new Intent(Perfil.this, editar_perfil.class);
                startActivity(i);

                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logosalir, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:

                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", "");
                editor.putString("nombre", "");
                editor.putString("username", "");
                editor.putString("descripcion", "");
                editor.putString("celular", "");
                editor.putString("correo", "");
                editor.putString("external", "");
                editor.putString("estado", "");
                editor.putString("foto", "");
                editor.putBoolean("logeado", false);
                editor.apply();

                Intent intent = new Intent(Perfil.this, Login.class);
                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
