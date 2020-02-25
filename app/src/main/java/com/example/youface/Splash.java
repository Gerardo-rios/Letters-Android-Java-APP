package com.example.youface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import vista.Login;

public class Splash extends Activity {

    private final int DURACION_SPLASH = 1500;

    TextView lbl;
    private Typeface font;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        lbl = findViewById(R.id.textView2);
        String fuente = "fuentes/boldB.TTF";
        this.font = Typeface.createFromAsset(getAssets(), fuente);
        lbl.setTypeface(font);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);



    }
}
