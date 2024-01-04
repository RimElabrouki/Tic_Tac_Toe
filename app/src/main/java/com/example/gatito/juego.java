package com.example.gatito;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class juego extends AppCompatActivity implements View.OnClickListener {

    Button btnregresar, musica;
    MediaPlayer micancion;
    ImageView play;
    private Transition transition;
    public static final long DURACION = 1000;

    protected Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    protected Button[] ArrButton;
    Button btnJugarMaquina;
    TextView etiqueta;

    int[][] unaMatriz;
    boolean esTurno;
    int contador_tiros;

    public void init() {
        unaMatriz = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                unaMatriz[i][j] = R.drawable.f;
            }
        }
        esTurno = true;
        contador_tiros = 0;
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
//comback
        btnregresar = (Button) findViewById(R.id.btnregreso);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagina1 = new Intent(juego.this, MainActivity.class);
                startActivity(pagina1);
            }
        });
        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);
        etiqueta = (TextView) findViewById(R.id.etPrueba);


        this.init();
        ArrButton = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};
        for (Button b : ArrButton) {
            b.setOnClickListener(this);
        }
 }

    public void onClick(View v) {
        Button b = (Button) v;
        this.click(b);
    }

    private List<Integer> botonesPresionados = new ArrayList<>();

    private void click(Button b) {
        int x = 0;
        if (esTurno) {
            x = R.drawable.xagua;
            b.setBackgroundResource(x);
        } else {
            x = R.drawable.myo;
            b.setBackgroundResource(x);
        }
        switch (b.getId()) {
            case R.id.a1:
                unaMatriz[0][0] = x;
                break;
            case R.id.a2:
                unaMatriz[0][1] = x;
                break;
            case R.id.a3:
                unaMatriz[0][2] = x;
                break;
            case R.id.b1:
                unaMatriz[1][0] = x;
                break;
            case R.id.b2:
                unaMatriz[1][1] = x;
                break;
            case R.id.b3:
                unaMatriz[1][2] = x;
                break;
            case R.id.c1:
                unaMatriz[2][0] = x;
                break;
            case R.id.c2:
                unaMatriz[2][1] = x;
                break;
            case R.id.c3:
                unaMatriz[2][2] = x;
                break;
        }

        esTurno = !esTurno;
        contador_tiros++;
        botonesPresionados.add(b.getId());
        b.setClickable(false);
        this.verificaMatriz();
    }

    private void deshabilitarBotones() {
        for (Button button : ArrButton) {
            button.setClickable(false);
        }
    }

    private void verificaMatriz() {
        int contador_X_F = 0, contador_O_F = 0, contador_X_C = 0, contador_O_C = 0,
                contador_X_DP = 0, contador_O_DP = 0, contador_X_DI = 0, contador_O_DI = 0, tamanioGato = 3;

        boolean hayGanador = false;

        for (int i = 0; i < unaMatriz.length; i++) {
            for (int j = 0; j < unaMatriz.length; j++) {
                if (unaMatriz[i][j] == R.drawable.xagua) // sirve para encontrar las x en la fila
                {
                    contador_X_F++;
                    if (contador_X_F == 3) {
                        hayGanador = true;
                    }
                }

                if (unaMatriz[i][j] == R.drawable.myo) // sirve para encontrar las o en la fila
                {
                    contador_O_F++;
                    if (contador_O_F == 3) {
                        hayGanador = true;
                    }
                }

                if (unaMatriz[j][i] == R.drawable.xagua) {
                    contador_X_C++;
                    if (contador_X_C == 3) {
                        hayGanador = true;
                    }
                }

                if (unaMatriz[j][i] == R.drawable.myo) //sirve para tener las o en la columna
                {
                    contador_O_C++;
                    if (contador_O_C == 3) {
                        hayGanador = true;
                    }
                }

                if (i == j) // solo es i = 0 j=0, i =1 j=1 y i = 2 j = 2
                {
                    if (unaMatriz[i][j] == R.drawable.xagua) // sirve para encontrar las x en la direccional principal
                    {
                        contador_X_DP++;
                        if (contador_X_DP == 3) {
                            hayGanador = true;
                        }
                    }

                    if (unaMatriz[i][j] == R.drawable.myo) // sirve para encontrar las o en la diagonal principal
                    {
                        contador_O_DP++;
                        if (contador_O_DP == 3) {
                            hayGanador = true;
                        }
                    }
                }

                if ((i + j) == tamanioGato - 1) {
                    if (unaMatriz[i][j] == R.drawable.xagua) //Este sirve para encontrar las x la diagonal inversa
                    {
                        contador_X_DI++;
                        if (contador_X_DI == 3) {
                            hayGanador = true;
                        }
                    }
                    if (unaMatriz[i][j] == R.drawable.myo) // este sirve para encontrar las o en la diagonal inversa
                    {
                        contador_O_DI++;
                        if (contador_O_DI == 3) {
                            hayGanador = true;
                        }
                    }
                }
                //fin del segundo ciclo
            }

            contador_X_F = 0;
            contador_O_F = 0;
            contador_X_C = 0;
            contador_O_C = 0;
            //fin del segundo ciclo
        }

        if (hayGanador) {
            String v = "";
            if (!esTurno) {
                v = "X WINS";
            } else {
                v = "O WINS";
            }
            deshabilitarBotones();
            etiqueta.setText(v);
            contador_tiros = 0;
        }

        if (contador_tiros == 9) {
            etiqueta.setText("DRAW");
        }
    }// fin de verificacion

    /**
     * metodo que inicia la musica del juego
     * @param v de tipo View
     */
    public void juegalo(View v) {
        if (micancion == null) {
            micancion = MediaPlayer.create(juego.this, R.raw.play);
            micancion.start();

        }else{
            Toast.makeText(getApplicationContext(), "Intenta de nuevo ", Toast.LENGTH_LONG).show();
        }if(micancion != null){
            Toast.makeText(getApplicationContext(), "la canción comenzó ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Intenta de nuevo ", Toast.LENGTH_LONG).show();
        }
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
                Toast.makeText(juego.this, "la canción se detiene", Toast.LENGTH_LONG).show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}