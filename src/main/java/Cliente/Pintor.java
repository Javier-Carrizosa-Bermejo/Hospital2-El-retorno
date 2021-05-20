/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Pintor extends Thread{
    InterfazHospital interfaz;
    private ConcurrentHashMap<Integer, ArrayList<String>> informacion;
    Lock cerradura = new ReentrantLock();
    
    Pintor(){
        interfaz = new InterfazHospital();
        interfaz.setVisible(true);
        //interfaz.inicializarse();
    }
    
    @Override
    public void run(){
        while(true){
            
            pintar();
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pintor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void pintar(){
        cerradura.lock();
        try {
            if(informacion == null){
                System.out.println("no hay info");
            }
            else{
                interfaz.colaPacientesModificar(informacion.get(0).toString());
            }
        } finally {
            cerradura.unlock();
        }
    }
    
    public void setInfo(ConcurrentHashMap<Integer, ArrayList<String>> informacion){
        cerradura.lock();
        try {
            this.informacion = informacion;
        } finally {
            cerradura.unlock();
        }
        
    }
}
