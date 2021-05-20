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
            System.out.println("Esperando conexion");
            conexion = servidor.accept(); //Esperamos una conexión
            System.out.println("Conexion recibida");
            entrada = new ObjectInputStream(conexion.getInputStream()); //Abrimos los canales de E/S
            System.out.println("Hasta aqui si que si no ? llego ?");
            salida = new ObjectOutputStream(conexion.getOutputStream());
            System.out.println("Hasta aqui llego ?");
            while(contador < 200){
                System.out.println("Conexion establecida -- servidor");
                informacion = registro.getInformacion(); //Leemos el mensaje del cliente
                System.out.println(informacion.get(0) + " Esto de aqui va bien");
                salida.writeObject(informacion);
                sleep(1000);
                contador ++;
                salida.reset();
            }
            
            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            conexion.close(); //Y cerramos la conexión
            
        } catch (IOException e) { } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void pulsacionesAumentar(){
        pulsaciones++;
    }

}
