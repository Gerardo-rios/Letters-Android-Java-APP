package vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.youface.R;

import vista.Fragmentos.camara_fragmento;
import vista.Fragmentos.galeria_fragmento;


public class Subir extends AppCompatActivity implements View.OnClickListener, camara_fragmento.OnFragmentInteractionListener, galeria_fragmento.OnFragmentInteractionListener {

    FrameLayout container;
    Button galeria, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir);
        componentes();
        foto.setActivated(true);
    }

    private void componentes(){
        container = findViewById(R.id.contenedor);
        galeria = findViewById(R.id.btn_gallery);
        foto = findViewById(R.id.btn_camera);
        galeria.setOnClickListener(this);
        foto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_camera:
                foto.setTextColor(Color.parseColor("#ffffff"));
                galeria.setTextColor(Color.parseColor("#9E9E9E"));
                camara_fragmento fragmento1 = new camara_fragmento();
                FragmentTransaction transaccion1 = getSupportFragmentManager().beginTransaction();
                transaccion1.replace(R.id.contenedor, fragmento1);
                transaccion1.commit();
                break;
            case R.id.btn_gallery:
                foto.setTextColor(Color.parseColor("#9E9E9E"));
                galeria.setTextColor(Color.parseColor("#ffffff"));
                galeria_fragmento fragmento2 = new galeria_fragmento();
                FragmentTransaction transaccion2 = getSupportFragmentManager().beginTransaction();
                transaccion2.replace(R.id.contenedor, fragmento2);
                transaccion2.commit();
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_gallery_fragment, menu);

        return true;

    }
}
