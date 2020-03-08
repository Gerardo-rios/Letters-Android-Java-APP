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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youface.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import Controlador.UserVolley;
import Controlador.ip;
import Interfaces.sync;

public class editar_perfil extends AppCompatActivity implements View.OnClickListener{

    ImageView foto_p;
    TextView btn_editar_foto, correo, cell;
    EditText nombre, bio, username;
    UserVolley volley = new UserVolley(this);
    String ide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        components();
        this.setTitle("Editar Perfil");
        cargarDatos();
    }

    private void components(){

        foto_p = findViewById(R.id.foto_edit_profile);
        btn_editar_foto = findViewById(R.id.btn_editar_foto_perfil);
        correo = findViewById(R.id.lbl_correo_cargar);
        cell = findViewById(R.id.lbl_celular_cargar);
        nombre = findViewById(R.id.txt_nombre_edit);
        bio = findViewById(R.id.txt_edit_bio);
        username = findViewById(R.id.lbl_username_edit);
        btn_editar_foto.setOnClickListener(this);
    }

    private void cargarDatos() {

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        ide = preferences.getString("id", "");
        nombre.setText(preferences.getString("nombre", ""));
        username.setText(preferences.getString("username", ""));
        bio.setText(preferences.getString("descripcion", ""));
        Picasso.get().load(ip.public_images() + preferences.getString("foto", "default_user.png")).into(foto_p);
        cell.setText(preferences.getString("celular", ""));
        correo.setText(preferences.getString("correo", ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.boton_listo, menu);

        return true;
    }

    public void guardar_cambios(){

        String name = nombre.getText().toString();
        String desc = bio.getText().toString();

        if (name.equals("") || desc.equals("")){
            Toast.makeText(this, "Por favor ingresa datos e intente nuevamente", Toast.LENGTH_SHORT).show();
        } else {
            if (ide.equals("")){
                Toast.makeText(this, "CHUGCHA EL ID", Toast.LENGTH_SHORT).show();
            } else {
                volley.EditarDatos(ide, name, desc, new sync() {
                    @Override
                    public void response(JSONObject json) {

                        try {
                            String msg = json.getString("msg");
                            String title = json.getString("title");

                            if  (title.equals("actualizado")){
                                try {
                                    JSONObject usr = json.getJSONObject("user");

                                    SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", usr.getString("user_id"));
                                    editor.putString("nombre", usr.getString("nombre"));
                                    editor.putString("username", usr.getString("username"));
                                    editor.putString("descripcion", usr.getString("descripcion"));
                                    editor.putString("celular", usr.getString("celular"));
                                    editor.putString("correo", usr.getString("correo"));
                                    editor.putString("external", usr.getString("external_id"));
                                    editor.putString("estado", usr.getString("status"));
                                    editor.putString("foto", usr.getString("foto_perfil"));
                                    editor.putBoolean("logeado", true);
                                    editor.apply();
                                    Toast.makeText(editar_perfil.this, msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(editar_perfil.this, Perfil.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    Log.e("no se pudo obtener dato", "nel");
                                    e.printStackTrace();
                                }

                            } else {

                                Toast.makeText(editar_perfil.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            Log.e("malardo bro", "No se obtubo msg");
                            e.printStackTrace();
                        }

                    }
                });
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.listo_edit:

                guardar_cambios();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_editar_foto_perfil:

                Intent i = new Intent(editar_perfil.this, foto_perfil_nueva.class);
                startActivity(i);

                break;
        }

    }
}
