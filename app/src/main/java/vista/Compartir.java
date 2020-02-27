package vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.youface.R;

public class Compartir extends AppCompatActivity {

    ImageView img;
    Button btn;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);
        componentes();
    }

    private void componentes(){
        img = findViewById(R.id.imagen_lista_subir);
        btn = findViewById(R.id.btn_terminar_post);
        txt = findViewById(R.id.descripcion_foto);
    }

}
