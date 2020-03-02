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
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Controlador.FollowVolley;
import Controlador.GridAdapter;
import Controlador.PostVolley;
import Controlador.UserVolley;
import Controlador.sync;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    ImageView foto_perfil;
    TextView n_posts, n_seguidores, n_seguidos, nombre, descripcion;
    Button editar;
    GridView fotos_posts;
    GridAdapter adapter;
    FollowVolley voly = new FollowVolley(this);
    PostVolley pvol = new PostVolley(this);
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

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

        obtener_componentes_visuales();
        cargarGrid();
        DatosdeUser();
        seguidores();
        seguidos();
        n_posteos();
    }


    private void obtener_componentes_visuales(){

        foto_perfil = findViewById(R.id.foto_perfil);
        n_posts = findViewById(R.id.num_publi);
        n_seguidores = findViewById(R.id.num_seguidores);
        n_seguidos = findViewById(R.id.num_seguidos);
        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        fotos_posts = findViewById(R.id.fotos_usuario);
        editar = findViewById(R.id.btn_editar_perfil);
        editar.setOnClickListener(this);

    }

    private void DatosdeUser(){

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        userid = preferences.getString("id", "");
        nombre.setText(preferences.getString("nombre", "Edita tu nombre"));
        this.setTitle(preferences.getString("username", ""));
        String descri = preferences.getString("descripcion", "Pon una bio").replaceAll("/", "\n");
        descripcion.setText(descri);
    }


    private void cargarGrid(){

        //ArrayList<String> url_foto = new ArrayList<>();

        ArrayList<Integer> fotos = new ArrayList<>();

        fotos.add(R.drawable.mascaras);
        fotos.add(R.drawable.message);
        fotos.add(R.drawable.calendario);
        fotos.add(R.drawable.casa);

        adapter = new GridAdapter(this, fotos);
        fotos_posts.setAdapter(adapter);

        fotos_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Perfil.this, PostDetallesActivity.class);
                intent.putExtra("Recurso", adapter.getItem(position).toString());
                startActivity(intent);

            }
        });

    }

    private void seguidores(){

        voly.Contar_Seguidores(userid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    JSONArray a = json.getJSONArray("a");
                    String nu = a.getJSONObject(0).getString("numero_seguidores");
                    String l = nu + "\n" + "Seguidores";
                    n_seguidores.setText(l);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("nose pudo", "obtener string");
                }

            }
        });

    }

    private void seguidos(){

        voly.Contar_Seguidos(userid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    JSONArray a = json.getJSONArray("a");
                    String nu = a.getJSONObject(0).getString("numero_seguidos");
                    String l = nu + "\n" + "Seguidos";
                    n_seguidos.setText(l);
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
                    JSONArray a = json.getJSONArray("p");
                    String nu = String.valueOf(a.length());
                    String l = nu + "\n" + "Posts";
                    n_posts.setText(l);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("nose pudo", "obtener string");
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
