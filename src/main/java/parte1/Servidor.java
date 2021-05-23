/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Servidor extends Thread {
    private Registro registro;
    ExecutorService conexiones = Executors.newCachedThreadPool();
    private Recepcion recepcion;
    private boolean cerrado = false;
    
    Servidor(Registro registro, Recepcion recepcion){
        this.registro = registro;
        this.recepcion = recepcion;
        
    }
    
    
    @Override
    public void run(){
        ServerSocket servidor;
               
        try {
            servidor = new ServerSocket(5000); //Creamos un ServerSocket en el Puerto 5000
            Socket conexion = servidor.accept(); //Esperamos una conexión
            ConexionTCP cliente = new ConexionTCP(registro, recepcion, conexion);
            conexiones.execute(cliente);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
