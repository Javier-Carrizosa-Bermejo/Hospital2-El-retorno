/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Paciente extends Thread{
    private boolean citado;
    Random rand = new Random();
    private int probabilidad, id;
    private PuestoVacunacion puesto;
    private Observacion salaDeObservacion;
    
    Paciente(int id, Observacion salaDeObservacion){
        this.id = id;
        probabilidad = rand.nextInt(100);
        this.citado = true; 
        if(probabilidad == 99){
            citado = false;
        }
        this.salaDeObservacion = salaDeObservacion;
    }
    
    @Override
    public void run(){ //solo se inicia tras asignarle un puesto en recepcion 
        try {
            if(puesto!= null){
                puesto.vacunarse(); //procede a vacunarse una vez le han indicado el puesto
                salaDeObservacion.descansar(id); //va a la sala de observación y se sienta en el primer puesto libre
            }
            else{

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean getCitado(){ 
        return citado;
    }
    
    public void setPuesto(PuestoVacunacion puesto){ //En recepción se le asigna un puesto
        this.puesto = puesto;
    }
    
    public int getID(){
        return id;
    }
}
