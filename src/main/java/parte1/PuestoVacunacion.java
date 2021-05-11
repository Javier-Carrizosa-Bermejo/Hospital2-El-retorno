/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Revij
 */
public class PuestoVacunacion {
Random rand = new Random();
    private Lock turno = new ReentrantLock();
    private Condition enEspera = turno.newCondition();
    private int pacientes = 0, tiempo, id;
    private Recepcion recepcion;
    
    PuestoVacunacion(int id, Recepcion recepcion){
        this.id = id;
        this.recepcion = recepcion;
        
    }
    
    public void vacunarse() throws InterruptedException{
        turno.lock();
        try {
            pacientes ++;
            tiempo = (rand.nextInt(2) + 3) * 1000;
            sleep(tiempo);
            
            if(pacientes == 15)
            {
                //EL MEDICO ABANDONA CON LO QUE LA SALA NO QUEDA LIBRE 
                enEspera.notify();
            }
            
            else{
                //LIBERAR SALA 
                recepcion.liberarSala(id);
            }
            
        } finally {
            turno.unlock();
        }  
    }
    
    
    public void vacunar() throws InterruptedException{
        turno.lock();
        //PRIMERO LIBERA SALA
        recepcion.liberarSala(id);
        try {
            while (pacientes < 15){
                enEspera.await();
            }
            pacientes = 0;
        } finally {
            turno.unlock();
        }
    }
    
    public int getID(){
        return this.id;
    }
    
}
