package Controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import Interfaces.sync;

public class FollowVolley {

    private String servidor = ip.host();
    private String seguir = "/";
    private String list_seguidos = "/listar_seguidos";
    private String list_seguidores = "/listar_seguidores";
    private String contar = "/usuario/contar?authid=";

    Context context;

    public FollowVolley(Context contexto){
        this.context = contexto;
    }

    public void Contar_S(String id, final sync sincro){

        String url = servidor + contar + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sincro.response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se pudo obtener los datos, intente nuevamente", Toast.LENGTH_SHORT).show();
                Log.e("error", error.getMessage());
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

}
