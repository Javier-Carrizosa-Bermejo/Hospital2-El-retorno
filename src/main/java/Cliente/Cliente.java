/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Revij
 */
public class Cliente  extends Thread{
    
    Pintor picasso;
    Socket cliente;
    ObjectInputStream entrada;
    ObjectOutputStream salida;
    int contador = 0;
    private ConcurrentHashMap<Integer, ArrayList<String>> informacion;
    private boolean cerrado = false;
    
    public Cliente(){
        picasso = new Pintor();
        picasso.start();
    }
        
    @Override
    public void run(){
        try {
            cliente = new Socket(InetAddress.getLocalHost(), 5000); //Creamos el socket para conectarnos
            //al puerto 5000 del servidor
            
            salida = new ObjectOutputStream(cliente.getOutputStream());
            
            entrada = new ObjectInputStream(cliente.getInputStream()); //Creamos los canales de E/S
            while(!cerrado){
                //salida.writeObject((Integer) contador); //Enviamos un mensaje al servidor
                informacion = (ConcurrentHashMap<Integer, ArrayList<String>>) entrada.readObject(); //Leemos la respuesta
                salida.writeObject(picasso.getPuestosACerrar());
                picasso.limpiarPuestos();
                contador++;
                salida.reset();
                picasso.setInfo(informacion);
                cerrado = picasso.getCerrarCliente();
                
            }
            picasso.setPintar();
            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            cliente.close(); //Cerramos la conexión
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    
}

