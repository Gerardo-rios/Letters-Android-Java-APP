package vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.youface.R;

import vista.Fragmentos.camara_fragmento;
import vista.Fragmentos.galeria_fragmento;

public class foto_perfil_nueva extends AppCompatActivity implements View.OnClickListener, camara_fragmento.OnFragmentInteractionListener, galeria_fragmento.OnFragmentInteractionListener{

    FrameLayout container;
    Button galeria, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_perfil_nueva);
        componentes();
        galeria.setPressed(true);
    }

    private void componentes(){
        container = findViewById(R.id.contenedor_perf);
        galeria = findViewById(R.id.btn_gallery_perf);
        foto = findViewById(R.id.btn_camera_perf);
        galeria.setOnClickListener(this);
        foto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_camera_perf:
                foto.setTextColor(Color.parseColor("#ffffff"));
                galeria.setTextColor(Color.parseColor("#9E9E9E"));
                camara_fragmento fragmento1 = new camara_fragmento();
                FragmentTransaction transaccion1 = getSupportFragmentManager().beginTransaction();
                transaccion1.replace(R.id.contenedor_perf, fragmento1);
                transaccion1.commit();
                break;
            case R.id.btn_gallery_perf:
                foto.setTextColor(Color.parseColor("#9E9E9E"));
                galeria.setTextColor(Color.parseColor("#ffffff"));
                galeria_fragmento fragmento2 = new galeria_fragmento();
                FragmentTransaction transaccion2 = getSupportFragmentManager().beginTransaction();
                transaccion2.replace(R.id.contenedor_perf, fragmento2);
                transaccion2.commit();
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.otro_siguiente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.seguir_edit_foto:

                Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
    */

}
