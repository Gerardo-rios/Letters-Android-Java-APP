package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import Modelo.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener {

    UserVolley volley = new UserVolley(this);

    TextView logear, red_registro;
    EditText username, clave;
    public static Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        componentes_visuales();
    }

    private void componentes_visuales(){

        logear = findViewById(R.id.btn_logear);
        red_registro = findViewById(R.id.btn_registrarse);
        logear.setOnClickListener(this);
        red_registro.setOnClickListener(this);
        username = findViewById(R.id.username_login);
        clave = findViewById(R.id.clave_login);

    }

    private boolean logeado(){

        String usern = username.getText().toString();
        String pas = clave.getText().toString();

        if (usern.equals("") || pas.equals("")){
            Toast.makeText(this, "Debes ingresar datos, para que puedas ingresar", Toast.LENGTH_SHORT).show();
        } else {
            try {

                JSONObject response = volley.Logear(usern, pas);

                try {
                    String informacion = response.getString("information");
                    boolean suceso = response.getBoolean("success");
                    JSONObject persona = response.getJSONObject("data");
                    if (suceso){

                        user = new Usuario();
                        user.setId(persona.getString("user_id"));
                        user.setNombre(persona.getString("nombre"));
                        user.setUsername(persona.getString("username"));
                        user.setDescripcion(persona.getString("descripcion"));
                        user.setFecha_nacimiento(persona.getString("fecha_nacimiento"));
                        user.setFoto(persona.getString("foto_perfil"));
                        user.setCelular(persona.getString("celular"));
                        user.setCorreo(persona.getString("correo"));
                        user.setEstado(persona.getString("status"));
                        Toast.makeText(this, informacion, Toast.LENGTH_SHORT).show();
                        return true;
                    } else {

                        Toast.makeText(this, informacion, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Log.e("no se pudo obtener", "No se pudo obtener dato del json");
                    e.printStackTrace();
                }

            } catch (NullPointerException ex){
                Log.e("otro click", "Mete otro click, dio null");
                ex.printStackTrace();
            }
        }

        return false;
    }


    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.btn_logear:
                if (logeado()){
                    intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_registrarse:
                intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                break;
        }

    }
}
