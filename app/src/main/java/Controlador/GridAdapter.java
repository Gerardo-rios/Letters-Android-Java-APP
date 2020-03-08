package Controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youface.R;

import java.util.ArrayList;
import java.util.List;

import Modelo.Post;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public GridAdapter(Context contexto, ArrayList<String> lista){

        this.context = contexto;
        this.list = lista;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid_posts, null);

        }

        TextView content = (TextView) convertView.findViewById(R.id.post_contenido_grid);
        content.setText(list.get(position));

        return convertView;
    }

}
