package com.example.parcial1;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpSingleton extends Thread {

    ////////constructor privado////////
    private TcpSingleton(){

    }

    //unica instancia de TcpSingleton
    private static TcpSingleton unicaInstancia;

    //metodo estático que devuelve la unica instancia
    public static TcpSingleton getInstance(){
         if(unicaInstancia == null){
             unicaInstancia = new TcpSingleton();

         }
         return unicaInstancia;
    }

    ////patron observer
    private OnMessageListener observer;

    public void setObserver (OnMessageListener observer){
        this.observer = observer;
    }


    //variables globales tcp
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    @Override
    public void run(){
        try {
            ////// IP CON EL EMULADOR
            //socket = new Socket("10.0.2.2", 5000);

            ////// IP CON EL COMPUTADOR (sin emulador)
            socket = new Socket("192.168.0.116", 5000);

            //emisor
            OutputStream os = socket.getOutputStream();
            writer = new BufferedWriter (new OutputStreamWriter(os));

            //receptor
            InputStream is = socket.getInputStream();
            reader = new BufferedReader (new InputStreamReader(is));

            while (true){
                String line = reader.readLine();
                //Log.d("msg llegando", ""+line);

                observer.MensajeLlegando(line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void enviarMensaje(String msg){
        new Thread(
                () -> {
                    try {
                        writer.write(msg + "\n");
                        writer.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        ).start();
    }


}
