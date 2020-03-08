package Controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youface.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Modelo.Comentario;

public class AdaptadoComentarios extends RecyclerView.Adapter<AdaptadoComentarios.ViewHolderComents>{

    List<Comentario> comentarios;

    public AdaptadoComentarios(List<Comentario> lista){
        this.comentarios = lista;
    }

    @NonNull
    @Override
    public ViewHolderComents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, null);

        return new ViewHolderComents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComents holder, int position) {

        holder.usn.setText(comentarios.get(position).getUsname());
        holder.content.setText(comentarios.get(position).getContenido());
        Picasso.get().load(ip.host() + comentarios.get(position).getUsfoto()).resize(50, 50).into(holder.fpu);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public static class ViewHolderComents extends RecyclerView.ViewHolder {

        ImageView fpu;
        TextView usn, content;

        public ViewHolderComents(@NonNull View itemView) {
            super(itemView);
            fpu = itemView.findViewById(R.id.foto_perfil_comentario);
            usn = itemView.findViewById(R.id.lbl_username_comentario);
            content = itemView.findViewById(R.id.lbl_contenido_comentario);
        }
    }

}
