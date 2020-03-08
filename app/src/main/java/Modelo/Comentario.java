package Modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Comentario {

    private String id;
    private String contenido;
    private String creado;
    private String modificado;
    private String post_id;
    private String user_id;
    private String usname;
    private String usfoto;


    public Comentario(JSONObject objectJSON){
        try {
            this.setId(objectJSON.getString("coment_id"));
            this.setContenido(objectJSON.getString("contenido"));
            this.setCreado(objectJSON.getString("created_at"));
            this.setModificado(objectJSON.getString("updated_at"));
            this.setUser_id(objectJSON.getString("user_id"));
            this.setUsname(objectJSON.getString("username"));
            this.setUsfoto(objectJSON.getString("foto_perfil"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("malardobro", "No se pudo obtener los datos");
        }
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getUsfoto() {
        return usfoto;
    }

    public void setUsfoto(String usfoto) {
        this.usfoto = usfoto;
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
