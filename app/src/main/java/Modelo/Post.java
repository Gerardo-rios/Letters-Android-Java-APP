package Modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {

    private String id;
    private String contenido;
    private String creado;
    private String modificado;
    private String n_coments;
    private String n_likes;
    private String user_id;
    private String user_name;
    private String user_foto;

    public Post(JSONObject objectJSON){
        try {
            this.setId(objectJSON.getString("post_id"));
            this.setContenido(objectJSON.getString("contenido"));
            this.setCreado(objectJSON.getString("created_at"));
            this.setUser_id(objectJSON.getString("user_id"));
            this.setUser_name(objectJSON.getString("username"));
            this.setUser_foto(objectJSON.getString("foto_perfil"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("malardobro", "No se pudo obtener los datos");
        }
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_foto() {
        return user_foto;
    }

    public void setUser_foto(String user_foto) {
        this.user_foto = user_foto;
    }

    public String getN_coments() {
        return n_coments;
    }

    public void setN_coments(String n_coments) {
        this.n_coments = n_coments;
    }

    public String getN_likes() {
        return n_likes;
    }

    public void setN_likes(String n_likes) {
        this.n_likes = n_likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
        this.modificado = modificado;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
