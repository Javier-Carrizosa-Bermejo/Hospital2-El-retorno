/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import static java.lang.Thread.sleep;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Revij
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        LinkedBlockingQueue<Paciente> pacientes = new LinkedBlockingQueue<>();
        ConcurrentLinkedQueue<Integer[]> reaccionesObservacion = new ConcurrentLinkedQueue<Integer[]>();
        PuestoVacunacion[] puestos = new PuestoVacunacion[10];
        Observacion salaObservacion = new Observacion();
        Recepcion recepcion = new Recepcion(puestos, salaObservacion);
        salaObservacion = new Observacion(recepcion, reaccionesObservacion );
        
        Auxiliar2 javi = new Auxiliar2(recepcion);
        javi.start();
        
        for(int j = 0; j < 10; j++){
            PuestoVacunacion puesto = new PuestoVacunacion(j, recepcion);
            puestos[j] = puesto;
        }
        
        
        Auxiliar1 pedro = new Auxiliar1(recepcion, pacientes);
        pedro.start();
        
        
        for(int i = 0; i < 10; i ++){
            Sanitario alejandro = new Sanitario(recepcion, i, reaccionesObservacion, salaObservacion);
            alejandro.start();
        }
        
        
        for(int k = 0; k < 200; k ++){
            Paciente sara = new Paciente(k, salaObservacion);
            sleep(1000);
            pacientes.offer(sara);
        }
        
    }
    
}
