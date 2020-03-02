package Controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.lang.reflect.Method;

public class FollowVolley {

    private String servidor = ip.host();
    private String seguir = "/";
    private String list_seguidos = "/listar_seguidos";
    private String list_seguidores = "/listar_seguidores";
    private String cont_seguidores = "/seguir/contar_seguidores";
    private String cont_seguidos = "/seguir/contar_seguidos";
    Context context;

    public FollowVolley(Context contexto){
        this.context = contexto;
    }

    public void Contar_Seguidores(String id, final sync sincro){

        String url = servidor + cont_seguidores + "?authid=" + id;
        JSONObject json = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincro.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("malado", error.getMessage());
                Toast.makeText(context, "No se pudo realizar", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public void Contar_Seguidos(String id, final sync sincro){

        String url = servidor + cont_seguidos + "?authid=" + id;
        JSONObject json = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincro.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("malado", error.getMessage());
                Toast.makeText(context, "No se pudo realizar", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }



}
