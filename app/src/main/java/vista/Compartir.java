package vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.youface.R;

public class Compartir extends AppCompatActivity implements View.OnClickListener{

    ImageView img;
    Button btn;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);
        componentes();
        Bitmap foto = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        img.setImageBitmap(foto);
    }

    private void componentes(){
        img = findViewById(R.id.imagen_lista_subir);
        btn = findViewById(R.id.btn_terminar_post);
        txt = findViewById(R.id.descripcion_foto);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_terminar_post:



                break;
        }

    }
}
