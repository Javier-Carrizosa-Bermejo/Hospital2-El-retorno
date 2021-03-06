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
    private Log escrituraS;
    private AtomicBoolean[] salasObservacion = new AtomicBoolean[20]; //Muestra si una sala está ocupada o no
    //False si no está ocupado, true si lo está.  
    private Semaphore reaccion = new Semaphore(0, true); //Bloqueará a los pacientes que sufran una reacción. 
    private Recepcion recepcion;
    private ConcurrentLinkedQueue<Integer[]> problematicos; //guardará el puesto y el id de cada paciente que sufra una reacción
    private Registro registro;
    private Semaphore[] personasConReaccionEnEspera = new Semaphore[20];
    
    Observacion(Recepcion recepcion, ConcurrentLinkedQueue<Integer[]> problematicos, Log escrituraS, Registro registro){
        this.escrituraS = escrituraS;
        this.problematicos = problematicos;
        this.recepcion = recepcion;
        for(int l = 0; l<20 ; l++){ //Inicianizamos los puestos, todos a 0
            salasObservacion[l] = new AtomicBoolean(false);
            personasConReaccionEnEspera[l] = new Semaphore(0);
        }
        this.registro = registro;
    }
    
    Observacion(){ // Sin este constructor no se puede inicializar el programa
        
    }
    
    public void descansar(int id_paciente) throws InterruptedException{
        double probabilidad = Math.floor(Math.random() * 100); //probabilidad de tener una reacción
        boolean encontrado = false; 
        int posicion = 0;  //puesto
        while(!encontrado && posicion < 20){
            //Este if compara cada puesto, si no está ocupado (false) se cambia automáticamente a true
            if (salasObservacion[posicion].compareAndSet(false, true)){ 
                encontrado = true;
                
            }
            else{
                posicion++;
            }
            
        }
        registro.pacienteEntraObsersvacion(posicion, id_paciente);
        if(probabilidad < 15){
            Integer[] conReaccion = {id_paciente, posicion}; 
            problematicos.add(conReaccion); //se añade el id del paciente con su puesto
            personasConReaccionEnEspera[posicion].acquire();//el paciente se bloquea
        }
        else{
            sleep(10000);
            
        }
        salasObservacion[posicion].set(false); //Liberamos el puesto
        recepcion.puestoLibre();               //Informamos de que se ha ido el paciente
        String var1 = Integer.toString(id_paciente);
        String var2 = Integer.toString(posicion);
        escrituraS.escritura(4, var1, var2, "");
        registro.pacienteSaleDeObsersvacion(posicion);
        //System.out.println(id_paciente + " Se marcha a casa del puesto" + posicion);
        

    }
    
    public void vistoBueno(int posicion){ //Se libera al primer paciente en reacción
        personasConReaccionEnEspera[posicion].release();
    }
    
   
}
