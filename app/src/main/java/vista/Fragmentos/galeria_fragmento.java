package vista.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.youface.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Controlador.UserVolley;
import vista.Perfil;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link galeria_fragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link galeria_fragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class galeria_fragmento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView fotoasubir;
    private ProgressBar progressBar;
    private Button boton, terminar;
    private Bitmap bitmap;
    UserVolley sw = new UserVolley(getContext());

    public galeria_fragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment galeria_fragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static galeria_fragmento newInstance(String param1, String param2) {
        galeria_fragmento fragment = new galeria_fragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_galeria_fragmento, container, false);

        fotoasubir = vista.findViewById(R.id.imagen_seleccionada);
        boton = vista.findViewById(R.id.btn_selecciona_foto);
        progressBar = vista.findViewById(R.id.progeso_barra);
        terminar = vista.findViewById(R.id.terminar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setType("image/");
                startActivityForResult(gallery.createChooser(gallery, "Selecciona la APP"), 10);
            }
        });
        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubirFoto();
                Intent intento = new Intent(getActivity(), Perfil.class);
                startActivity(intento);
            }
        });

        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK){

                Uri mypath = data.getData();
                //fotoasubir.setImageURI(mypath);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mypath);
                    fotoasubir.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.e("Err", "Error bitmap");
                    e.printStackTrace();
                }
                terminar.setVisibility(View.VISIBLE);
            }

    }

    private void SubirFoto(){

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String ide = preferences.getString("id", "");
        String foto = convertirImagen(bitmap);
        sw.CambiarFoto(ide, foto);

    }

    private String convertirImagen(Bitmap bitmapa){

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmapa.compress(Bitmap.CompressFormat.JPEG, 50, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return  imagenString;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
