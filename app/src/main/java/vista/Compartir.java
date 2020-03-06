package vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import Controlador.PostVolley;
import Interfaces.sync;

public class Compartir extends AppCompatActivity implements View.OnClickListener{

    ImageView img;
    Button btn;
    EditText txt;
    String pat_foto;
    Bitmap bitmap;
    PostVolley volley = new PostVolley(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);
        componentes();
        pat_foto = getIntent().getStringExtra("BitmapImage");
        Log.d("pat", pat_foto);
        bitmap = BitmapFactory.decodeFile(pat_foto);
        img.setImageBitmap(rotar(bitmap));

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
                hacer_post();
                break;
        }

    }

    private void hacer_post(){

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");
        String desc = txt.getText().toString();
        if (!id.equals("") && !desc.equals("")){

            volley.Postear(id, desc, new sync() {
                @Override
                public void response(JSONObject json) {
                    String title = "";
                    String msg = "";
                    try {
                        title = json.getString("title");
                        msg = json.getString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("NO", "no se pudo obtener json");
                    }
                    if (title.equals("subido")){
                        Toast.makeText(Compartir.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Compartir.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Compartir.this, msg, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            Toast.makeText(this, "Falta algun dato", Toast.LENGTH_SHORT).show();
        }

    }

    private int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } return 0;

    }

    private Bitmap rotar(Bitmap bit){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(pat_foto);
        } catch (IOException e) {
            Log.e("no se pudo", "exif");
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationD = exifToDegrees(orientation);
        Matrix matrix = new Matrix();
        if (orientation != 0f) {
            matrix.preRotate(rotationD);
        }
        Bitmap adjustedBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        adjustedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        return adjustedBitmap;
    }
}
