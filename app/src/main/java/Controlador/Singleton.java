package Controlador;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {

    private RequestQueue queue;
    private Context contexto;

    private static Singleton instancia;

    public Singleton(Context context){
        this.contexto = context;
        queue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if (queue == null){
            queue = Volley.newRequestQueue(contexto.getApplicationContext());
        }

        return  queue;
    }

    public static synchronized Singleton getInstance(Context contexte){
        if (instancia == null){
            instancia = new Singleton(contexte);
        }
        return instancia;
    }

    public <T> void addToRequestQueue(Request request){
        queue.add(request);
    }

}
