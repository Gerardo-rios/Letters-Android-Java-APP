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

    private String servidor = "http://192.168.0.104:8080";
    private String logear = "/usuario/logear";
    private String registrarse = "/usuario/registrar";

    private boolean estado;
    private String datos;
    private JSONObject yeison;
    Context context;

    public UserVolley(Context contexto){
        this.context = contexto;
    }

    public JSONObject Logear(String user, String password){

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

                yeison = response;
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

        return yeison;
    }

    public JSONObject Registrarse(String mail, String user, String pass, String cell){

        String url = servidor.concat(registrarse);
        JSONObject json = new JSONObject();

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
                yeison = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //String er = error.getMessage();
                error.printStackTrace();
                Log.e("mal", "Mal error en response");
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<>();
                parametros.put("correo", mail);
                parametros.put("username", user);
                parametros.put("password", pass);
                parametros.put("telefono", cell);
                parametros.put("fecha_nacimiento", fecha);

                return super.getParams();
            }
        }*/;
        Singleton.getInstance(context).addToRequestQueue(request);

        return yeison;
    }

}
