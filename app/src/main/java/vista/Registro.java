package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import Controlador.DatePickerFragment;
import Controlador.UserVolley;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText correo, username, clave, celular;
    TextView red_login, registrarse;

    UserVolley volley = new UserVolley(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cargar_view();
    }

    private void cargar_view(){

        correo = findViewById(R.id.correo);
        username = findViewById(R.id.username);
        clave = findViewById(R.id.clave);
        celular = findViewById(R.id.telefono);
        red_login = findViewById(R.id.btn_redirigir_login);
        registrarse = findViewById(R.id.btn_enviar_registro);
        red_login.setOnClickListener(this);
        registrarse.setOnClickListener(this);

    }

    /*private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = year + "/" + (month+1) + "/" + day;
                nacimiento.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }*/

    private boolean Registrado(){

        String email = correo.getText().toString();
        String usern = username.getText().toString();
        String pas = clave.getText().toString();
        String cel = celular.getText().toString();

        if (email.equals("") || usern.equals("") || pas.equals("") || cel.equals("")){
            Toast.makeText(this, "Debes llenar todos los campos primero", Toast.LENGTH_SHORT).show();
        } else {
            try {

                JSONObject respuesta = volley.Registrarse(email, usern, pas, cel);
                try {
                    String etiqueta = respuesta.getString("title");
                    String mensaje = respuesta.getString("msg");

                    if (etiqueta.equals("registrado")){

                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                        return true;

                    } else {

                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    Log.e("no se pudo", "obtener atributos del json");
                    e.printStackTrace();
                }


            } catch (NullPointerException ex){
                ex.printStackTrace();
                Log.e("Mi loco", "Dele otro click");
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.btn_redirigir_login:
                intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                break;

            case R.id.btn_enviar_registro:
                if (Registrado()){

                    intent = new Intent(Registro.this, Login.class);
                    startActivity(intent);

                }
                break;
        }

    }
}
