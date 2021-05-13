/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Sanitario extends Thread{
    Random rand = new Random();
    private int tiempoPreparacion, id;
    private CyclicBarrier descanso = new CyclicBarrier(2);
    private Recepcion recepcion;
    private PuestoVacunacion miPuesto;
    private ConcurrentLinkedQueue<Integer[]> problematicos;
    private Integer[] paciente;
    private Observacion salaObservacion;
    
    Sanitario(Recepcion recepcion, int id, ConcurrentLinkedQueue<Integer[]> problematicos, 
            Observacion salaObservacion){
        this.problematicos = problematicos;
        tiempoPreparacion = rand.nextInt(3) + 1;
        tiempoPreparacion *= 1000;
        this.recepcion = recepcion;
        this.id = id;
        this.salaObservacion = salaObservacion;
        
    }
    
    @Override
    public void run(){
        try {
            sleep(tiempoPreparacion);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {      
            System.out.println("Sanitario " + id  + " entra en el hospital");
            /*
            Tiene que encontrar sala de recepcion y ponerse a vacunar
            
            
            
            Despues tiene que descansar
            
            
            Si tiene una emergencia la socorre primero
            
            
            Si no, vuelve a curar
            
            
            */
            
            miPuesto = recepcion.medicoEntraEnSala();
            miPuesto.vacunar(id);
            sleep(15000);
            while((paciente = problematicos.poll()) != null){
                tiempoPreparacion = rand.nextInt(4) + 2;
                sleep(tiempoPreparacion);
                System.out.println("El paciente " + paciente[0] + " Ha tenido una reacción y"
                        + " ha sido tratado por " + this.id + " en el puesto " + paciente[1]);
                salaObservacion.vistoBueno();
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
}
