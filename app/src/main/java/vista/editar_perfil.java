package vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youface.R;

public class editar_perfil extends AppCompatActivity implements View.OnClickListener{

    ImageView foto_p;
    TextView btn_editar_foto, correo, cell;
    EditText nombre, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        components();
    }

    private void components(){

        foto_p = findViewById(R.id.foto_edit_profile);
        btn_editar_foto = findViewById(R.id.btn_editar_foto_perfil);
        correo = findViewById(R.id.lbl_correo_cargar);
        cell = findViewById(R.id.lbl_celular_cargar);
        nombre = findViewById(R.id.txt_nombre_edit);
        bio = findViewById(R.id.txt_edit_bio);
        btn_editar_foto.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.boton_listo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.listo_edit:
                //terminar edit

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
                //necesito enviar el user igual xd
                break;
        }

    }
}
