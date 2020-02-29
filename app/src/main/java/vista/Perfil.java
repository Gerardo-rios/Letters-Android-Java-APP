package vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import Controlador.GridAdapter;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    ImageView foto_perfil;
    TextView n_posts, n_seguidores, n_seguidos, nombre, descripcion;
    Button editar;
    GridView fotos_posts;
    GridAdapter adapter;

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

    private void cargarGrid(){

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


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
