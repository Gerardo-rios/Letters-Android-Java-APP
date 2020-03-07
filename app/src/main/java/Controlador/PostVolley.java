package Controlador;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import Interfaces.sync;
import Modelo.Post;

public class PostVolley {

    private String server = ip.host();
    private String postear = "/post/postear";
    private String listar = "/post/listar_todos";
    private String posts_user = "/post/listar_user_posts";
    private String comentar = "/comentar/";
    private String listar_coments = "/comentar/listar?postid=";
    private String contar_lc = "/post/contar?postid=";

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

    public void Postear(String id, String contenido, final sync sincro){

        String url = server.concat(postear);
        JSONObject json = new JSONObject();

        try {
            json.put("identificador", id);
            json.put("contenido", contenido);
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

    public void ListarPosts(final sync sincro){

        String url = server.concat(listar);
        JSONObject json = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
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

    public void Comentar(String postid, String userid, String contenido, final sync sincro){

        String url = server.concat(comentar);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("postid", postid);
            jsonObject.put("userid", userid);
            jsonObject.put("contenido", contenido);
        } catch (JSONException e) {
            Log.e("JSON", "No se pudo meter los datos para comentar");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sincro.response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se pudo realizar comentario", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);

    }

    public void ListarComents(String postid, final sync sincro){

        String url = server + listar_coments + postid;
        JSONObject json = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
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

    public void ContarLikes_Coments(Post poste, final sync sincro){

        String url = server + contar_lc + poste.getId();
        JSONObject json = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
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
