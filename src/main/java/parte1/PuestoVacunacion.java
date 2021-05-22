/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Revij
 */
public class PuestoVacunacion {
    Random rand = new Random();
    private escrituraSegura escrituraS;
    private Lock turno = new ReentrantLock();
    private Condition enEspera = turno.newCondition();
    private Condition cierre = turno.newCondition();
    private int pacientes = 0, tiempo, id, medico_id;
    private Recepcion recepcion;
    private Registro registro;
    private boolean pacienteEnCamino = false, cerrar = false;
    
    PuestoVacunacion(int id, Recepcion recepcion, escrituraSegura escrituraS, Registro registro){
        this.escrituraS = escrituraS;
        this.id = id; //identificador de la sala
        this.recepcion = recepcion;
        this.registro = registro;
        
    }
    
    public void vacunarse() throws InterruptedException{
        turno.lock();
        try {
            pacientes ++;
            recepcion.cogerVacuna();
            String var1;
            //escrituraS.escritura(14, var1, "", "");
            //System.out.println("El medico ha cogido una vacuna. Quedan " + vacunas + " vacunas disponibles.");
            tiempo = (rand.nextInt(3) + 3) * 1000;
            sleep(tiempo); //tiempo que tarda en vacunarse
            
            if(pacientes >= 15 || cerrar)
            {
                //EL MEDICO ABANDONA CON LO QUE LA SALA NO QUEDA LIBRE 
                var1 = Integer.toString(this.id);
                escrituraS.escritura(6, var1, "", "");
                //System.out.println("Se han vacunado 15 pacientes, " + this.id + " procede a cerrarse");
                pacienteEnCamino = false;
                registro.pacienteSaleDeVacunacion(this.id);
                enEspera.signal();
            }
            
            else if (pacientes < 15){
                //LIBERAR SALA
                pacienteEnCamino = false;
                registro.pacienteSaleDeVacunacion(this.id);
                recepcion.liberarSala(id);
            }
            cierre.signalAll();
            
            
        } finally {
            turno.unlock();
        }  
    }
    
    
    public void vacunar(int med_id) throws InterruptedException{
        turno.lock();
        cerrar = false;
        this.medico_id = med_id; //se registra el medico asignado a la sala
        String var1 = Integer.toString(medico_id);
        String var2 = Integer.toString(this.id);
        escrituraS.escritura(10, var1, var2, "");
        //System.out.println("El medico " + medico_id + " entra en la sala " + this.id);
        recepcion.liberarSala(this.id); //Se libera la sala (si no hay medico, el puesto de vacunacion no está disponible)
        try {
            while (pacientes < 15 && !cerrar){
                enEspera.await(); //Espera a que se atiendan 15 pacientes
            }
            pacientes = 0;  //Se resetea el contador
            escrituraS.escritura(3, var1, "", "");
            //System.out.println("El medico " + medico_id + " abandona la sala " + this.id);
            recepcion.medicoAbandonaSala(this.id); //El médico abandona la sala
        } finally {
            turno.unlock();
        }
    }
    
    public int getID(){
        return this.id;
    }
    
    public int get_med_id(){
        return this.medico_id;
    }
    
    public void setPacienteEnCamino(){
        turno.lock();
        try {
            pacienteEnCamino = true;
        } finally {
            turno.unlock();
        }
    }
    
    public void cerrarPuesto() throws InterruptedException{
        turno.lock();
        try {
            cerrar = true;
            while(pacienteEnCamino){
                cierre.await();
            }
            enEspera.signal();
        } finally {
            turno.unlock();
            
        }
    }
    
}
