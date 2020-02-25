package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youface.MainActivity;
import com.example.youface.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView logear, red_registro;
    EditText username, clave;


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


    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.btn_logear:
                intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_registrarse:
                intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                break;
        }

    }
}
