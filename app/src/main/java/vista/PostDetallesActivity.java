package vista;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youface.R;

public class PostDetallesActivity extends AppCompatActivity {

    ImageView foto_post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallespost);

        foto_post = findViewById(R.id.post_foto);

        foto_post.setImageResource(Integer.parseInt(getIntent().getExtras().get("Recurso").toString()));

    }
}
