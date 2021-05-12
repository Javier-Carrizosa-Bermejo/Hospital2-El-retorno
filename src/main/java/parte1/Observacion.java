/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Revij
 */
public class Observacion {
    AtomicInteger enObservacion = new AtomicInteger(0);
    AtomicBoolean[] salasObservacion = new AtomicBoolean[20];
    Semaphore semaforo = new Semaphore(20);
    Lock turno = new ReentrantLock();
    Condition reaccion = turno.newCondition();
    private Recepcion recepcion;
    
    Observacion(Recepcion recepcion){
        this.recepcion = recepcion;
        for(int l = 0; l<20 ; l++){
            salasObservacion[l] = new AtomicBoolean(false);
        }
    }
    Observacion(){
        
    }
    
    public void descansar(int id_paciente) throws InterruptedException{
        enObservacion.incrementAndGet();
        double probabilidad = Math.floor(Math.random() * 100); 
        boolean encontrado = false; 
        int posicion = 0;
        while(!encontrado && posicion < 20){
            if (salasObservacion[posicion].compareAndSet(false, true)){
                encontrado = true;
                
            }
            else{
                posicion++;
            }
            
        }
        if(probabilidad < 5){
            System.out.println(id_paciente + " Ha reaccionado");
        }
        else{
            sleep(10000);
            salasObservacion[posicion].set(false);
            enObservacion.decrementAndGet();
            recepcion.puestoLibre();
        }
        System.out.println(id_paciente + " Se marcha a casa");
        

    }
    
    public int getEnObservavion(){
        return enObservacion.get();
    }
    
   
}
