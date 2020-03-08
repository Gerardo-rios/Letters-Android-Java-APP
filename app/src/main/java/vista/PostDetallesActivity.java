package vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youface.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Controlador.AdaptadoComentarios;
import Controlador.PostVolley;
import Controlador.ip;
import Interfaces.sync;
import Modelo.Comentario;

public class PostDetallesActivity extends AppCompatActivity {

    TextView post, nl, nc;
    String posteid;
    PostVolley sw = new PostVolley(this);
    ImageView foto_perfil;
    ImageView coment;
    List<Comentario> list_coments;
    AdaptadoComentarios adapter;
    RecyclerView recicler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallespost);

        post = findViewById(R.id.post_indirecta);
        //foto_perfil = findViewById(R.id.foto_usuario_post);
        nl = findViewById(R.id.txt_numero_likes);
        nc = findViewById(R.id.txt_num_comentarios_post);
        coment = findViewById(R.id.btn_coment);
        recicler = findViewById(R.id.coments_re_pe);

        post.setText(getIntent().getExtras().get("Recurso").toString());
        posteid = getIntent().getStringExtra("Posteid");

        conteo();
        //user();
        listar();
        coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetallesActivity.this, Comentar.class);
                intent.putExtra("post_id", posteid);
                startActivity(intent);
            }
        });
    }

    private void conteo(){

        sw.ContarLikes_Coments(posteid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    JSONArray a = json.getJSONArray("like");
                    JSONArray b = json.getJSONArray("coment");
                    String nu = a.getJSONObject(0).getString("likes");
                    String ne = b.getJSONObject(0).getString("comentarios");
                    nl.setText("Tienes " + nu);
                    nc.setText("Hay " + ne);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("nose pudo", "obtener string");
                }


            }
        });

    }
/*
    private void user(){

        sw.ObtenerPoste(posteid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    if (json.getBoolean("success")){

                        JSONArray a = json.getJSONArray("data");
                        JSONObject j = a.getJSONObject(0);
                        PostDetallesActivity.this.setTitle(j.getString("username"));
                        String url_foto = ip.host() + j.getString("foto_perfil");
                        Picasso.get().load(url_foto).resize(300, 300).into(foto_perfil);
                    } else {
                        Toast.makeText(PostDetallesActivity.this, "Ocurrio un error inesperado", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("nose pudo", "obtener datos JSON en obtener poste");
                    e.printStackTrace();
                }

            }
        });

    }
*/
    public void listar(){

        sw.ListarComents(posteid, new sync() {
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
                        Toast.makeText(PostDetallesActivity.this, "Ocurrio un error, intentalo mas tarde", Toast.LENGTH_SHORT).show();
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
