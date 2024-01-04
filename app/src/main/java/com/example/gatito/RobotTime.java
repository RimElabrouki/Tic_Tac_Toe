package com.example.gatito;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RobotTime extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegresar;
    private Button[] arrButton;
    private TextView etiqueta;

    private int[][] matriz;
    private boolean esTurnoJugador;
    private int contadorTiros;
    private boolean hayGanador;

    private final int X = R.drawable.xagua;
    private final int O = R.drawable.myo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_time);

        matriz = new int[3][3];
        esTurnoJugador = true;
        contadorTiros = 0;
        hayGanador = false;

        Button a1 = findViewById(R.id.a1);
        Button a2 = findViewById(R.id.a2);
        Button a3 = findViewById(R.id.a3);
        Button b1 = findViewById(R.id.b1);
        Button b2 = findViewById(R.id.b2);
        Button b3 = findViewById(R.id.b3);
        Button c1 = findViewById(R.id.c1);
        Button c2 = findViewById(R.id.c2);
        Button c3 = findViewById(R.id.c3);

        arrButton = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};
        for (Button button : arrButton) {
            button.setOnClickListener(this);
        }

        btnRegresar = findViewById(R.id.btnregreso);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RobotTime.this, MainActivity.class));
            }
        });

        etiqueta = findViewById(R.id.etPrueba);
    }

    @Override
    public void onClick(View v) {
        if (!hayGanador) {
            Button button = (Button) v;
            click(button);
            juegaMaquina();
        }
    }

    private void click(Button button) {
        int x = esTurnoJugador ? X : O;
        button.setBackgroundResource(x);

        switch (button.getId()) {
            case R.id.a1:
                matriz[0][0] = x;
                break;
            case R.id.a2:
                matriz[0][1] = x;
                break;
            case R.id.a3:
                matriz[0][2] = x;
                break;
            case R.id.b1:
                matriz[1][0] = x;
                break;
            case R.id.b2:
                matriz[1][1] = x;
                break;
            case R.id.b3:
                matriz[1][2] = x;
                break;
            case R.id.c1:
                matriz[2][0] = x;
                break;
            case R.id.c2:
                matriz[2][1] = x;
                break;
            case R.id.c3:
                matriz[2][2] = x;
                break;
        }

        esTurnoJugador = !esTurnoJugador;
        contadorTiros++;
        button.setClickable(false);

        hayGanador = verificaMatriz();
        if (hayGanador) {
            if (!esTurnoJugador) {
                etiqueta.setText("¡X WINS");
            } else {
                etiqueta.setText("O WINS");
            }
            deshabilitarBotones();
            return;
        } else if (contadorTiros == 9) {
            etiqueta.setText("¡DRAW!");
            return;
        }
    }

    private void deshabilitarBotones() {
        for (Button button : arrButton) {
            button.setClickable(false);
        }
    }

    private void juegaMaquina() {
        if (esTurnoJugador) {
            return;
        }

        List<Button> botonesDisponibles = new ArrayList<>();
        for (Button button : arrButton) {
            if (button.isClickable()) {
                botonesDisponibles.add(button);
            }
        }

        if (botonesDisponibles.size() > 0) {
            Random random = new Random();
            Button botonMaquina = botonesDisponibles.get(random.nextInt(botonesDisponibles.size()));
            click(botonMaquina);
        }
    }

    private boolean verificaMatriz() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (matriz[i][0] != 0 && matriz[i][0] == matriz[i][1] && matriz[i][0] == matriz[i][2]) {
                return true;
            }
        }

        // Verificar columnas
        for (int i = 0; i < 3; i++) {
            if (matriz[0][i] != 0 && matriz[0][i] == matriz[1][i] && matriz[0][i] == matriz[2][i]) {
                return true;
            }
        }

        // Verificar diagonales
        if (matriz[0][0] != 0 && matriz[0][0] == matriz[1][1] && matriz[0][0] == matriz[2][2]) {
            return true;
        }
        if (matriz[0][2] != 0 && matriz[0][2] == matriz[1][1] && matriz[0][2] == matriz[2][0]) {
            return true;
        }

        return false;
    }
}
