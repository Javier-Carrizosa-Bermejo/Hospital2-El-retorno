/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class ConexionTCP extends Thread{
    private Registro registro;
    ExecutorService ayudantes = Executors.newCachedThreadPool();
    ConcurrentHashMap<Integer, ArrayList<String>> informacion;
    private ArrayList<Integer> puestosACerrar;
    private Recepcion recepcion;
    private boolean cerrado = false;
    private Socket conexion;
    ObjectOutputStream salida;
    ObjectInputStream entrada;
    
    
    ConexionTCP(Registro registro, Recepcion recepcion, Socket conexion){
        this.registro = registro;
        this.recepcion = recepcion;
        this.conexion = conexion;
        
    }
    
    
    
    @Override
    public void run(){
        try {
            entrada = new ObjectInputStream(conexion.getInputStream()); //Abrimos los canales de E/S
            salida = new ObjectOutputStream(conexion.getOutputStream());
            while(!cerrado){
                informacion = registro.getInformacion(); //Leemos el mensaje del cliente
                salida.writeObject(informacion);
                puestosACerrar = (ArrayList<Integer>) entrada.readObject();
                
                
                for(int i = 0; i < puestosACerrar.size(); i++){
                    if(puestosACerrar.get(i) == 11){
                        cerrado = true;
                    }
                    else{
                        Ayudante hilo = new Ayudante(recepcion, puestosACerrar.get(i));
                        ayudantes.execute(hilo);
                    }
                }
                sleep(1000);
                salida.reset();
            }
            salida.close();
            entrada.close(); //Cerramos los flujos de entrada y salida
            conexion.close(); //Y cerramos la conexión
            ayudantes.shutdown();
        }
        catch (IOException ex) {
            Logger.getLogger(ConexionTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConexionTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
