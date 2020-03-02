package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;

import org.json.JSONException;
import org.json.JSONObject;

import Controlador.UserVolley;
import Controlador.sync;
import Modelo.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener {

    UserVolley volley = new UserVolley(this);

    TextView logear, red_registro;
    EditText username, clave;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        componentes_visuales();

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        boolean logeado = preferences.getBoolean("logeado", false);
        if  (logeado){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void componentes_visuales(){

        logear = findViewById(R.id.btn_logear);
        red_registro = findViewById(R.id.btn_registrarse);
        logear.setOnClickListener(this);
        red_registro.setOnClickListener(this);
        username = findViewById(R.id.username_login);
        clave = findViewById(R.id.clave_login);

    }

    private void logeado(){

        final String usern = username.getText().toString();
        String pas = clave.getText().toString();

        if (usern.equals("") || pas.equals("")){
            Toast.makeText(this, "Debes ingresar datos, para que puedas ingresar", Toast.LENGTH_SHORT).show();
        } else {
            try {

                volley.Logear(usern, pas, new sync() {
                    @Override
                    public void response(JSONObject json) {

                        try {
                            String informacion = json.getString("information");
                            boolean suceso = json.getBoolean("success");
                            if (suceso){
                                JSONObject persona = json.getJSONObject("data");
                                user = new Usuario();
                                user.setId(persona.getString("user_id"));
                                user.setNombre(persona.getString("nombre"));
                                user.setUsername(persona.getString("username"));
                                user.setDescripcion(persona.getString("descripcion"));
                                user.setFoto(persona.getString("foto_perfil"));
                                user.setCelular(persona.getString("celular"));
                                user.setExternal(persona.getString("external_id"));
                                user.setCorreo(persona.getString("correo"));
                                user.setEstado(persona.getString("status"));
                                GuardarLogeado();
                                Toast.makeText(Login.this, informacion, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                Toast.makeText(Login.this, informacion, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Log.e("no se pudo obtener", "No se pudo obtener dato del json");
                            e.printStackTrace();
                        }

                    }
                });

            } catch (NullPointerException ex){
                Log.e("otro click", "Mete otro click, dio null");
                ex.printStackTrace();
            }
        }
    }

    private void GuardarLogeado(){

        SharedPreferences sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getId());
        editor.putString("nombre", user.getNombre());
        editor.putString("username", user.getUsername());
        editor.putString("descripcion", user.getDescripcion());
        editor.putString("celular", user.getCelular());
        editor.putString("correo", user.getCorreo());
        editor.putString("external", user.getExternal());
        editor.putString("estado", user.getEstado());
        editor.putString("foto", user.getFoto());
        editor.putBoolean("logeado", true);
        editor.apply();
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.btn_logear:

                logeado();

                break;
            case R.id.btn_registrarse:
                intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                break;
        }

    }
}
