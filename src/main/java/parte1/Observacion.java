/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import static java.lang.Thread.sleep;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    AtomicBoolean[] salasObservacion = new AtomicBoolean[20];
    Semaphore reaccion = new Semaphore(0, true);
    private Recepcion recepcion;
    private ConcurrentLinkedQueue<Integer[]> problematicos;
    
    
    Observacion(Recepcion recepcion, ConcurrentLinkedQueue<Integer[]> problematicos){
        this.problematicos = problematicos;
        this.recepcion = recepcion;
        for(int l = 0; l<20 ; l++){
            salasObservacion[l] = new AtomicBoolean(false);
        }
    }
    Observacion(){
        
    }
    
    public void descansar(int id_paciente) throws InterruptedException{
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
            Integer[] conReaccion = {id_paciente, posicion}; 
            problematicos.add(conReaccion);
            reaccion.acquire();
        }
        else{
            sleep(10000);
            
        }
        salasObservacion[posicion].set(false);
        recepcion.puestoLibre();
        System.out.println(id_paciente + " Se marcha a casa");
        

    }
    
    public void vistoBueno(){
        reaccion.release();
    }
    
   
}
