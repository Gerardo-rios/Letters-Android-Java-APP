package Controlador;

import android.content.Context;
import android.util.Log;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserVolley {

    private String servidor = ip.host();
    private String logear = "/usuario/logear";
    private String registrarse = "/usuario/registrar";

    private boolean estado;
    private String datos;
    Context context;

    public UserVolley(Context contexto){
        this.context = contexto;
    }

    public void Logear(String user, String password, final sync asy){

        String url = servidor.concat(logear);
        JSONObject json = new JSONObject();
        //yeison = new JSONObject();
        try {
            json.put("username", user);
            json.put("password", password);
        } catch (JSONException e) {
            Log.e("Error json", "no se pudo insertar datos en el json");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                asy.response(response);
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String er = error.getMessage();
                Log.e("mal", er);
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public void Registrarse(String mail, String user, String pass, String cell, final sync asy){

        String url = servidor.concat(registrarse);
        final JSONObject json = new JSONObject();

        try {
            json.put("correo", mail);
            json.put("username", user);
            json.put("password", pass);
            json.put("telefono", cell);

        } catch (JSONException e) {
            Log.e("Error json", "No se pudo enviar parametros");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                asy.response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("mal", error.getMessage());
            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

}
