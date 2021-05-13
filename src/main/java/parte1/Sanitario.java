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
    private int tiempoDormir, id;
    private Recepcion recepcion;
    private PuestoVacunacion miPuesto;
    private ConcurrentLinkedQueue<Integer[]> problematicos; //cola de pacientes que sufren una reacción
    private Integer[] paciente; //paciente que sufre una reacción
    private Observacion salaObservacion; 
    
    Sanitario(Recepcion recepcion, int id, ConcurrentLinkedQueue<Integer[]> problematicos, 
            Observacion salaObservacion){
        this.problematicos = problematicos;
        tiempoDormir= ( rand.nextInt(3) + 1 ) * 1000;
        this.recepcion = recepcion;
        this.id = id;
        this.salaObservacion = salaObservacion;
        
    }
    
    @Override
    public void run(){
        System.out.println("Sanitario " + id  + " entra en el hospital");
        try {
            sleep(tiempoDormir);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {                  
            miPuesto = recepcion.medicoEntraEnSala();   //El médico pilla la primera sala libre que vea
            miPuesto.vacunar(id);                       //Procede a vacunar, tras 15 pacientes se va a descansar
            tiempoDormir = (rand.nextInt(4) + 5) * 1000;
            sleep(tiempoDormir);                        //Descansa
            while((paciente = problematicos.poll()) != null){ //Si hay pacientes que sufren reacción los atiende
                //poll() devuelve null si no hay elementos, si el paciente es null no se entra en el bucle
                tiempoDormir = (rand.nextInt(4) + 2) * 1000;
                sleep(tiempoDormir);
                System.out.println("El paciente " + paciente[0] + " Ha tenido una reacción y"
                        + " ha sido tratado por " + this.id + " en el puesto " + paciente[1]);
                salaObservacion.vistoBueno();   //Da el visto bueno al paciente y lo deja ir
                
            }
            System.out.println("Sanitario " + id  + " ha salido de su descanso");
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
}
