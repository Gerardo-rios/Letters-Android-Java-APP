package vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import Controlador.PostVolley;
import Interfaces.sync;
import vista.Fragmentos.camara_fragmento;
import vista.Fragmentos.galeria_fragmento;


public class Subir extends AppCompatActivity implements View.OnClickListener {

    Button subir;
    EditText caja_texto;
    TextView contador;
    PostVolley volley = new PostVolley(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir);
        cargarNav();
        componentes();
        contador.setText("Puede escribir 280 caracteres");
    }

    private void cargarNav(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;

                switch (item.getItemId()){

                    case R.id.navigation_inicio:
                        intent = new Intent(Subir.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_subir:
                        break;
                    case R.id.navigation_Yo:

                        intent = new Intent(Subir.this, Perfil.class);
                        startActivity(intent);

                        break;

                }

                return false;
            }
        });
    }

    private void componentes(){
        subir = findViewById(R.id.btn_subir_indirecta);
        caja_texto = findViewById(R.id.txt_indirecta);
        contador = findViewById(R.id.lbl_contador);
        subir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_subir_indirecta:
                postear();
                break;
        }

    }

    public void postear(){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");
        String contenido = caja_texto.getText().toString();
        if (!id.equals("") && !contenido.equals("")){

            volley.Postear(id, contenido, new sync() {
                @Override
                public void response(JSONObject json) {

                    try {
                        if  (json.getBoolean("success")){

                            Toast.makeText(Subir.this, json.getString("information"), Toast.LENGTH_SHORT).show();
                            Intent intencion = new Intent(Subir.this, MainActivity.class);
                            startActivity(intencion);

                        } else {

                            Toast.makeText(Subir.this, json.getString("information"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        Log.e("Error JSON", "no se pudo obtener datos");
                        e.printStackTrace();
                    }

                }
            });

        } else {
            Toast.makeText(this, "Falta algun dato", Toast.LENGTH_SHORT).show();
        }
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_gallery_fragment, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.seguir_post:

                Intent intento = new Intent(Subir.this, Compartir.class);
                startActivity(intento);

                break;

        }

        return super.onOptionsItemSelected(item);
    }*/
}
