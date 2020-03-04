package Controlador;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import Interfaces.sync;

public class PostVolley {

    private String server = ip.host();
    private String postear = "/post/postear";
    private String listar = "/post/listar_todos";
    private String posts_user = "/post/listar_user_posts";

    Context context;

    public PostVolley (Context contexto){
        this.context = contexto;
    }

    public void List_Posts_User(String id, final sync sincro){

        String url = server + posts_user + "?identificador=" + id;
        JSONObject json = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincro.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAL", error.getMessage());
                Toast.makeText(context, "No se pudo obtener datos, intentalo mas tarde", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);

    }

    public void Postear(String id, File foto, String descripcion, final sync sincro){

        String url = server.concat(postear);
        JSONObject json = new JSONObject();

        try {
            json.put("identificador", id);
            json.put("foto", foto);
            json.put("descripcion", descripcion);
        } catch (JSONException e) {
            Log.e("Error json", "No se pudo enviar parametros");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincro.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MALSISIMO", error.getMessage());
                Toast.makeText(context, "No se pudo obtener datos, intentalo mas tarde", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);

    }

}
