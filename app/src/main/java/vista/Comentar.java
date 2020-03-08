package vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youface.MainActivity;
import com.example.youface.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Controlador.AdaptadoComentarios;
import Controlador.AdaptadorHomePosts;
import Controlador.PostVolley;
import Interfaces.sync;
import Modelo.Comentario;
import Modelo.Post;

public class Comentar extends AppCompatActivity {

    RecyclerView recicler;
    Button btncomentar;
    EditText contenido;
    PostVolley sw = new PostVolley(this);
    List<Comentario> list_coments;
    AdaptadoComentarios adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentar);
        recicler = findViewById(R.id.comentarios_recicler);
        btncomentar = findViewById(R.id.btn_comentar);
        contenido = findViewById(R.id.txt_comentar_contenido);
        btncomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentar();
            }
        });
        listar();
    }

    private String DatosdeUser(){
        String userid;
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        userid = preferences.getString("id", "");
        return userid;
    }

    private void comentar(){

        Intent intent = getIntent();
        String userid = DatosdeUser();
        String postid = intent.getStringExtra("post_id");
        String content = contenido.getText().toString();

        if (userid.equals("") || postid.equals("") || content.equals("")){

            Toast.makeText(this, "Faltan datos para poder comentar", Toast.LENGTH_SHORT).show();

        } else {

            sw.Comentar(postid, userid, content, new sync() {
                @Override
                public void response(JSONObject json) {

                    try {
                        if (json.getString("title").equals("comentado")){
                            contenido.setText("");
                            Toast.makeText(Comentar.this, "Comentario Guardado", Toast.LENGTH_SHORT).show();
                            listar();
                        } else {
                            Toast.makeText(Comentar.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("JSON", "error en obtener title");
                        e.printStackTrace();
                    }

                }
            });

        }

    }

    public void listar(){

        Intent intent = getIntent();
        String idp = intent.getStringExtra("post_id");

        sw.ListarComents(idp, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    boolean s = json.getBoolean("success");
                    if (s){
                        JSONArray data = json.optJSONArray("data");
                        list_coments = new ArrayList<>();
                        for (int i=0; i<data.length(); i++){
                            list_coments.add(new Comentario(data.getJSONObject(i)));
                        }
                        //conteo();
                        CargarRecycler();
                    } else {
                        Toast.makeText(Comentar.this, "Ocurrio un error, intentalo mas tarde", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("ERROR", "NO SE PUDO OBTENER DATA");
                    e.printStackTrace();
                }

            }
        });

    }

    private void CargarRecycler(){

        adapter = new AdaptadoComentarios(list_coments);
        recicler.setLayoutManager(new LinearLayoutManager(this));
        recicler.setAdapter(adapter);

    }





}
