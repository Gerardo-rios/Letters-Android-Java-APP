package Controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.youface.MainActivity;
import com.example.youface.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import Interfaces.sync;
import Modelo.Post;
import vista.Comentar;


public class AdaptadorHomePosts extends RecyclerView.Adapter<AdaptadorHomePosts.ViewHolderPosts>{

    List<Post>lista;
    Context context;
    boolean likeo;

    public AdaptadorHomePosts(List<Post> posts, Context contexto){
        this.lista = posts;
        this.context = contexto;
    }

    @NonNull
    @Override
    public ViewHolderPosts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);

        return new ViewHolderPosts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPosts holder, final int position) {

        holder.contenido_post.setText(lista.get(position).getContenido());

        String fp = "Publicado el: " + lista.get(position).getCreado().replaceAll(" ", " A las: ");
        holder.fecha_post.setText(fp);

        holder.nombre_user.setText(lista.get(position).getUser_name());

        holder.btn_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comentar.class);
                intent.putExtra("post_id", lista.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likear(lista.get(position).getId());
                if (likeo){
                    holder.btn_like.setImageResource(R.drawable.ic_corazon_rojo);
                } else {
                    holder.btn_like.setImageResource(R.drawable.ic_corazon_black);
                    deslikear(lista.get(position).getId());
                }
            }
        });

        Picasso.get().load(ip.public_images() + lista.get(position).getUser_foto()).into(holder.foto_user);

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolderPosts extends RecyclerView.ViewHolder {
        //datos usuario
        ImageButton foto_user;
        TextView nombre_user;
        //datos post
        ImageButton btn_like, btn_coment;
        TextView contenido_post, fecha_post;


        public ViewHolderPosts(@NonNull View itemView) {
            super(itemView);

            foto_user = itemView.findViewById(R.id.foto_usuario_post_i);
            nombre_user = itemView.findViewById(R.id.txt_nombre_user_post_i);
            btn_like = itemView.findViewById(R.id.btn_like_i);
            btn_coment = itemView.findViewById(R.id.btn_coment_i);
            contenido_post = itemView.findViewById(R.id.post_indirecta_i);
            fecha_post = itemView.findViewById(R.id.txt_fecha_post_i);

        }
    }

    private void likear(String postid){
        PostVolley sw = new PostVolley(context);
        SharedPreferences preferences = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        String userid = preferences.getString("id", "");
        sw.Likear(postid, userid, new sync() {
            @Override
            public void response(JSONObject json) {

                try {
                    if  (json.getString("msg").equals("yasta")){
                        likeo = false;
                    } else {
                        likeo = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void deslikear(String postid){
        PostVolley sw = new PostVolley(context);
        SharedPreferences preferences = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        String userid = preferences.getString("id", "");
        sw.Deslikear(postid, userid, new sync() {
            @Override
            public void response(JSONObject json) {

                likeo = false;

            }
        });


    }




}
