package com.example.parcial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements OnMessageListener {

    EditText txNom;
    Button btOk;
    String texto;

    private TcpSingleton tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciar
        txNom = findViewById(R.id.txNom);
        btOk = findViewById(R.id.btOk);

        tcp = TcpSingleton.getInstance();
        tcp.start();
        tcp.setObserver(this);

        btOk.setOnClickListener(
                (v) -> {
                    texto = txNom.getText().toString().trim();
                    if(texto.equals("")){
                        Toast.makeText(this, "Escriba un nombre", Toast.LENGTH_LONG).show();
                    }else{

                        //envio de nombre por json
                        Nombre n = new Nombre(texto);
                        Gson gson = new Gson();
                        String json = gson.toJson(n);
                        tcp.enviarMensaje(json);

                        Intent i = new Intent(this, controles.class);
                        startActivity(i);
                    }
                }
        );
    }


    @Override
    public void MensajeLlegando(String msg) {
        Log.d("msg llego a pantalla 1", ""+msg);
    }
}