package Controlador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.youface.R;

import java.util.List;


import Modelo.Post;


public class AdaptadorHomePosts extends RecyclerView.Adapter<AdaptadorHomePosts.ViewHolderPosts>{

    List<Post>lista;

    public AdaptadorHomePosts(List<Post> posts){
        this.lista = posts;
    }

    @NonNull
    @Override
    public ViewHolderPosts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);

        return new ViewHolderPosts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPosts holder, int position) {

        holder.contenido_post.setText(lista.get(position).getContenido());

        String fp = "Publicado el: " + lista.get(position).getCreado().replaceAll(" ", " A las: ");
        holder.fecha_post.setText(fp);

        holder.nombre_user.setText(lista.get(position).getUser_name());

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
        ImageButton btn_opciones_post, btn_like, btn_coment;
        TextView contenido_post, fecha_post;


        public ViewHolderPosts(@NonNull View itemView) {
            super(itemView);

            foto_user = itemView.findViewById(R.id.foto_usuario_post_i);
            nombre_user = itemView.findViewById(R.id.txt_nombre_user_post_i);
            btn_opciones_post = itemView.findViewById(R.id.btn_opciones_post_i);
            btn_like = itemView.findViewById(R.id.btn_like_i);
            btn_coment = itemView.findViewById(R.id.btn_coment_i);
            contenido_post = itemView.findViewById(R.id.post_indirecta_i);
            fecha_post = itemView.findViewById(R.id.txt_fecha_post_i);

        }
    }




}
