package com.example.parcial1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.parcial1.model.Color;
import com.example.parcial1.model.Movimiento;
import com.example.parcial1.model.Nombre;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class controles extends AppCompatActivity implements View.OnTouchListener, OnMessageListener{

    Button btUp, btDown, btLeft, btRight, btColor;
    float posx = 100;
    float posy = 100;

    Boolean UPpresionado = false;
    Boolean DOWNpresionado = false;
    Boolean LEFTpresionado = false;
    Boolean RIGHTpresionado = false;

    private TcpSingleton tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controles);

        //referenciar
        btUp = findViewById(R.id.btUp);
        btDown = findViewById(R.id.btDown);
        btLeft = findViewById(R.id.btLeft);
        btRight = findViewById(R.id.btRight);
        btColor = findViewById(R.id.btColor);

        tcp = TcpSingleton.getInstance();
        tcp.setObserver(this);

        btUp.setOnTouchListener(this);
        btDown.setOnTouchListener(this);
        btRight.setOnTouchListener(this);
        btLeft.setOnTouchListener(this);


        //cambio de color
        btColor.setOnClickListener(
                (v)->{
                    //envio de nombre por json
                    Gson gson = new Gson();
                    Color c = new Color((int) (Math.random()*300),(int) (Math.random()*300),(int) (Math.random()*300));
                    String json = gson.toJson(c);
                    tcp.enviarMensaje(json);
                }
            );
        }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            ////////////////si mantiene pulsado el boton de movimiento////////////////
            case MotionEvent.ACTION_DOWN:

                switch (v.getId()){
                    ////////si el boton undido es el boton de arriba////////
                    case R.id.btUp:
                        Log.e("UP", "PRESIONADO");

                        UPpresionado = true;
                        Gson gsonU = new Gson();
                        new Thread(
                                () -> {
                                    while(UPpresionado){
                                        if(posy >= 39){
                                            posy -= 0.2;

                                            //envio de nombre por json
                                            Movimiento m = new Movimiento(posx,posy);
                                            String json = gsonU.toJson(m);
                                            tcp.enviarMensaje(json);
                                        }
                                    }
                                }
                        ).start();
                        break;

                    ////////si el boton undido es el boton de abajo////////
                    case R.id.btDown:
                        Log.e("DOWN", "PRESIONADO");
                        DOWNpresionado = true;
                        Gson gsonD = new Gson();
                        new Thread(
                                () -> {
                                    while(DOWNpresionado){
                                        if(posy <= 475){
                                            posy += 0.2;

                                            //envio de nombre por json
                                            Movimiento m = new Movimiento(posx,posy);
                                            String json = gsonD.toJson(m);
                                            tcp.enviarMensaje(json);
                                        }
                                    }
                                }
                        ).start();
                        break;

                    ////////si el boton undido es el boton de la izquierda////////
                    case R.id.btLeft:
                        Log.e("LEFT", "PRESIONADO");
                        LEFTpresionado = true;
                        Gson gsonL = new Gson();
                        new Thread(
                                () -> {
                                    while(LEFTpresionado){
                                        if(posx >= 25){
                                            posx -= 0.2;

                                            //envio de nombre por json
                                            Movimiento m = new Movimiento(posx,posy);
                                            String jsonD = gsonL.toJson(m);
                                            tcp.enviarMensaje(jsonD);
                                        }
                                    }
                                }
                        ).start();
                        break;

                    ////////si el boton undido es el boton de la derecha////////
                    case R.id.btRight:
                        Log.e("RIGHT", "PRESIONADO");
                        RIGHTpresionado = true;
                        Gson gsonR = new Gson();
                        new Thread(
                                () -> {
                                    while(RIGHTpresionado){
                                        if(posx <= 775){
                                            posx += 0.2;

                                            //envio de nombre por json
                                            Movimiento m = new Movimiento(posx,posy);
                                            String json = gsonR.toJson(m);
                                            tcp.enviarMensaje(json);
                                        }
                                    }
                                }
                        ).start();
                        break;
                }
                break;

            ////////////////si mantiene suelta el boton de movimiento ////////////////
            case MotionEvent.ACTION_UP:

                switch (v.getId()){
                    ////////si el boton soltado es el boton de arriba////////
                    case R.id.btUp:
                        UPpresionado = false;
                        Log.e("UP", "SOLTADO");
                        break;

                    ////////si el boton soltado es el boton de abajo////////
                    case R.id.btDown:
                        DOWNpresionado = false;
                        Log.e("DOWN", "SOLTADO");
                        break;

                    ////////si el boton soltado es el boton de la izquierda////////
                    case R.id.btLeft:
                        LEFTpresionado = false;
                        Log.e("LEFT", "SOLTADO");
                        break;

                    ////////si el boton soltado es el boton de la derecha////////
                    case R.id.btRight:
                        RIGHTpresionado = false;
                        Log.e("RIGHT", "SOLTADO");
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void MensajeLlegando(String msg) {
        Log.d("msg llego a pantalla 2", ""+msg);
    }
}