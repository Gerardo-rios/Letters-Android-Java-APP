package Controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Interfaces.sync;

public class UserVolley {

    private String servidor = ip.host();
    private String logear = "/usuario/logear";
    private String registrarse = "/usuario/registrar";
    private String modificar_bio = "/usuario/modificar_perfil";
    private String obtener = "/obtener?id=";
    private String cambiar_foto = "/usuario/actualizar_foto";

    Context context;

    public UserVolley(Context contexto){
        this.context = contexto;
    }

    /**
     * LOgear en la aplicacion
     * @param user
     * @param password
     * @param asy
     */
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
                Toast.makeText(context, "No se pudo obtener los datos, intente nuevamente", Toast.LENGTH_SHORT).show();
                String er = error.getMessage();
                Log.e("mal", er);
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * Registrarse en la aplicacion
     * @param mail
     * @param user
     * @param pass
     * @param cell
     * @param asy
     */
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
                Toast.makeText(context, "No se pudo enviar los datos, intente nuevamente", Toast.LENGTH_SHORT).show();
                Log.e("mal", error.getMessage());
            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * Editar los datos del usuario, bio y nombre.
     * @param id
     * @param nombre
     * @param desc
     * @param sincronizador
     */
    public void EditarDatos(String id, String nombre, String desc, final sync sincronizador){

        String url = servidor.concat(modificar_bio);
        JSONObject json = new JSONObject();

        try {
            json.put("identificador", id);
            json.put("nombre", nombre);
            json.put("descripcion", desc);
        } catch (JSONException e) {
            Log.e("Error json", "No se pudo enviar parametros");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincronizador.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
                Toast.makeText(context, "No se pudo enviar los datos, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);

    }

    /**
     * Cambiar la foto de perfil de usuario
     * @param id
     * @param foto
     */
    public void CambiarFoto(String id, String foto){

        String url = servidor.concat(cambiar_foto);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", id);
            jsonObject.put("foto", foto);
            jsonObject.put("hos", servidor);
        } catch (JSONException e) {
            Log.e("Error json", "no se pudo insertar datos en el json foto");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Buenardo", "Actualizada");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
                //Toast.makeText(context, "No se pudo enviar los datos, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                Log.e("Malardo", "No se hizo coneccion para la foto");
            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }



 /*   public void ObtenerUsuario(String id, final sync sincro){

        String url = servidor + obtener + id;
        JSONObject json = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                sincro.response(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
                Toast.makeText(context, "No se pudo obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }
    */





}
