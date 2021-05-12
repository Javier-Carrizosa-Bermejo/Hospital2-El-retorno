/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Revij
 */
public class Recepcion {
    private boolean[] listaSalas = new boolean[10], salasMedicos = new boolean[10];
    private PuestoVacunacion[] puestos;
    private boolean noLibre = true;
    private Lock libre = new ReentrantLock(), medicos = new ReentrantLock();
    private Condition ningunLibre = libre.newCondition();
    private Semaphore vacuna = new Semaphore(0);
    public int vacunas = 0;
    
    
    Recepcion(PuestoVacunacion[] puestos){
        for(int i = 0; i < 10;i++){
            listaSalas[i] = false;
            salasMedicos[i] = false;
        }
        this.puestos = puestos;
        
    }
    
    
    public PuestoVacunacion salaLibre() throws InterruptedException{
        libre.lock();
        try {
            while(true){
                for(int i = 0; i < 10;i++){
                    if (listaSalas[i]){
                        listaSalas[i] = false;
                        return puestos[i];
                    }
                }  //Si no hay ninguna sala libre toca esperar
                System.out.println("No hay ninguna sala ");
                ningunLibre.await();
            }
        } finally {
            libre.unlock();
        }
        
    }
    
    
    public void liberarSala(int id){ //Libera la sala indicada
        libre.lock();
        System.out.println("Se libera la sala " + id);
        try {
            listaSalas[id] = true;
            ningunLibre.signal();
        } finally {
            libre.unlock();
        }
    }
    
    
    public void medicoAbandonaSala(int id){
        medicos.lock();
        try {
            salasMedicos[id] = false;
        } finally {
            medicos.unlock();
        }
    }
    
    
    public PuestoVacunacion medicoEntraEnSala() throws InterruptedException{
        medicos.lock();
        try {
            int posicion = 0;
            boolean encontrado = false;
            while(!encontrado && posicion < 10){
                if(salasMedicos[posicion]){
                    posicion++;
                    
                }
                else{
                    vacuna.acquire();
                    vacunas --;
                    encontrado = true;
                    salasMedicos[posicion] = true;
                }
            }
            return puestos[posicion];
        } finally {
            medicos.unlock();
        }
    }
    
    public void getVacuna(){
        vacunas++;
        vacuna.release();
    }
    
}
