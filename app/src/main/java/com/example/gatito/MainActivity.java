package com.example.gatito;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button  btnivel,btnJugarMaquina;
    MediaPlayer micancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        // Boton para sar a los niveles que tiene el juego
        btnivel=(Button) findViewById(R.id.btniveles);
        btnivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(MainActivity.this, juego.class));
              Intent intent = new Intent(v.getContext(), juego.class);
               startActivityForResult(intent, 0);
            }
         });

        btnJugarMaquina = findViewById(R.id.btn_jugar_maquina);
        btnJugarMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RobotTime.class);
                startActivityForResult(intent, 0);
            }
        });

    }
    public void juegalo(View v) {
        if (micancion == null) {
            micancion = MediaPlayer.create(MainActivity.this, R.raw.play);
            micancion.start();

        }else{
            Toast.makeText(getApplicationContext(), "Try again ", Toast.LENGTH_LONG).show();
        }if(micancion != null){
            Toast.makeText(getApplicationContext(), "the song started ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "try again ", Toast.LENGTH_LONG).show();
        }       btnivel = findViewById(R.id.btniveles);


    }

    /**
     * Metodo que se cierra la musica del juego
     *
     * @param v de tipo View
     */
    public void Stop(View v) {
        if (micancion != null) {
            micancion.release();
            micancion = null;
        } else {
            try {
                Toast.makeText(MainActivity.this, "The song stoped", Toast.LENGTH_LONG).show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}


