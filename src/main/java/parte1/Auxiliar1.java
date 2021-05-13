/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Auxiliar1 extends Thread {
    private int atendidos = 0;
    long tiempo;
    private LinkedBlockingQueue<Paciente> pacientes; //cola de entrada
    private Recepcion recepcion;
    Paciente persona;
    Random rand = new Random();
    
    Auxiliar1(Recepcion recepcion, LinkedBlockingQueue<Paciente> pacientes){
        this.recepcion = recepcion;
        this.pacientes = pacientes;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                
                persona = pacientes.take(); //coje el primer elemento, si no se queda bloquado hasta que haya
                /*tiempo = ((rand.nextInt(2) + 1)/2) * 1000; 
                sleep(tiempo);*/ //tiempo de preparación
                sleep((int) ((Math.random() * 6) + 5) * 100);
                
                if(persona.getCitado()){//Si está citado....
                    PuestoVacunacion libre = recepcion.salaLibre(); //Busca un puesto de vacunación, se bloquea hasta que lo encuentra
                    System.out.println("El paciente " + persona.getID() 
                            + " procede a vacunarse en la sala " + libre.getID()
                            + " por " + libre.get_med_id());
                    persona.setPuesto(libre); //le da el puesto al paciente
                    persona.start();  //el paciente inicia su rutina
                }
                else{
                    //NO ESTÁ CITADO
                    System.out.println("El paciente no está citado para hoy: " + persona.getID());
                    persona.setPuesto(null); //se le asigna un puesto nulo
                    persona.start();
                }
                atendidos++;
                if(atendidos >= 10){ //Comprueba si le toca descansar
                    atendidos = 0;
                    tiempo = (rand.nextInt(3) + 3) * 1000;
                    sleep(tiempo);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Auxiliar1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
}
