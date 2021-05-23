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
    private Random rand = new Random();
    private Log escrituraS;
    private int tiempoDormir, id;
    private Recepcion recepcion;
    private PuestoVacunacion miPuesto;
    private ConcurrentLinkedQueue<Integer[]> problematicos; //cola de pacientes que sufren una reacción
    private Integer[] paciente; //paciente que sufre una reacción
    private Observacion salaObservacion; 
    private Registro registro;
    
    Sanitario(Recepcion recepcion, int id, ConcurrentLinkedQueue<Integer[]> problematicos, 
            Observacion salaObservacion, Log escrituraS, Registro registro){
        this.escrituraS = escrituraS;
        this.problematicos = problematicos;
        tiempoDormir= ( rand.nextInt(3) + 1 ) * 1000;
        this.recepcion = recepcion;
        this.id = id;
        this.salaObservacion = salaObservacion;
        this.registro = registro;
        
    }
    
    @Override
    public void run(){
        String var1 = Integer.toString(id);
        escrituraS.escritura(15, var1, "", "");
        //System.out.println("Sanitario " + id  + " entra en el hospital");
        try {
            sleep(tiempoDormir);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {
            //registro.medicoAbandonaDescanso(id);
            miPuesto = recepcion.medicoEntraEnSala();   //El médico pilla la primera sala libre que vea
            registro.medicoEntraEnSala(id, miPuesto.getID());
            miPuesto.vacunar(id);                       //Procede a vacunar, tras 15 pacientes se va a descansar
            registro.medicoAbandonaSala(this.id, miPuesto.getID()); 
            tiempoDormir = (rand.nextInt(4) + 5) * 1000;
            sleep(tiempoDormir);                        //Descansa
            registro.medicoAbandonaDescanso(id);
            while((paciente = problematicos.poll()) != null){ //Si hay pacientes que sufren reacción los atiende
                //poll() devuelve null si no hay elementos, si el paciente es null no se entra en el bucle
                registro.MedicoCuraEnObersvacion(paciente[1], this.id);
                tiempoDormir = (rand.nextInt(4) + 2) * 1000;
                sleep(tiempoDormir);
                String var2 = Integer.toString(paciente[0]);
                String var3 = Integer.toString(this.id);
                escrituraS.escritura(2, var2, var3, "");
                System.out.println("El paciente " + paciente[0] + " Ha tenido una reacción y"
                        + " ha sido tratado por " + this.id + " en el puesto " + paciente[1]);
                registro.MedicoMarchaDeObersvacion(paciente[1]);
                salaObservacion.vistoBueno(paciente[1]);   //Da el visto bueno al paciente y lo deja ir
                
                
            }
            
            escrituraS.escritura(7, "", "", "");
            //System.out.println("Sanitario " + id  + " ha salido de su descanso");
        } catch (InterruptedException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
}
