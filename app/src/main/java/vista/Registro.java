package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youface.MainActivity;
import com.example.youface.R;

import Controlador.DatePickerFragment;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText correo, username, clave, celular, nacimiento;
    TextView red_login, registrarse;

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
        nacimiento = findViewById(R.id.nacimiento);
        nacimiento.setOnClickListener(this);
        red_login = findViewById(R.id.btn_redirigir_login);
        registrarse = findViewById(R.id.btn_enviar_registro);
        red_login.setOnClickListener(this);
        registrarse.setOnClickListener(this);

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                nacimiento.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.nacimiento:
                showDatePickerDialog();
                break;

            case R.id.btn_redirigir_login:
                intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                break;

            case R.id.btn_enviar_registro:
                intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                break;
        }

    }
}
