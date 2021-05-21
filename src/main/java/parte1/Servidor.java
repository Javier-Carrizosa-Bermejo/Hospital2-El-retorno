/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import Cliente.Cliente;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Servidor extends Thread {
    private int pulsaciones = 0;
    private Registro registro;
     ConcurrentHashMap<Integer, ArrayList<String>> informacion;
    
    Servidor(Registro registro){
        this.registro = registro;
    }
    
    
    @Override
    public void run(){
        ServerSocket servidor;
        Socket conexion;
        ObjectOutputStream salida;
        ObjectInputStream entrada;
        int contador = 0;
        
        

        try {
            servidor = new ServerSocket(5000); //Creamos un ServerSocket en el Puerto 5000ç
            conexion = servidor.accept(); //Esperamos una conexión
            entrada = new ObjectInputStream(conexion.getInputStream()); //Abrimos los canales de E/S
            salida = new ObjectOutputStream(conexion.getOutputStream());
            while(contador < 300){
                informacion = registro.getInformacion(); //Leemos el mensaje del cliente
                salida.writeObject(informacion);
                sleep(1000);
                contador ++;
                salida.reset();
            }
            salida.close();
            entrada.close(); //Cerramos los flujos de entrada y salida
            conexion.close(); //Y cerramos la conexión
            
        } catch (IOException e) { } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void pulsacionesAumentar(){
        pulsaciones++;
    }

}