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
    private PuestoVacunacion[] puestos; //lista de puestos, 
    private Lock libre = new ReentrantLock(), medicos = new ReentrantLock();
    private Semaphore vacuna = new Semaphore(0);
    public int vacunas = 0;
    private Condition ningunLibre = libre.newCondition(), ningunPuesto = libre.newCondition();
    private Observacion salaObservacion;
    private int ocupados = 0; //mantiene la cuenta de cuantos pacientes han pasado a vacunarse
    
    
    Recepcion(PuestoVacunacion[] puestos, Observacion salaObservacion){
        for(int i = 0; i < 10;i++){
            listaSalas[i] = false; //false si la sala está ocupada, true si está disponible. Ninguna sala está disponible al comienzo
            salasMedicos[i] = false;
        }
        this.puestos = puestos;
        this.salaObservacion = salaObservacion;
        
    }
    
    
    public PuestoVacunacion salaLibre() throws InterruptedException{
        libre.lock();
        try {
            //no pueden haber más de 20 pacientes siendo atendidos, si hubiera más puede que la
            //sala de observación sufriera desbordamiento
            while(ocupados >= 20 ){
                System.out.println("No hay ningún puesto disponible");
                ningunPuesto.await();
            }
            while(true){
                for(int i = 0; i < 10;i++){
                    if (listaSalas[i]){
                        listaSalas[i] = false; //La sala queda ocupada
                        ocupados++; //aumentamos el contador de atendidos
                        return puestos[i]; //Devolvemos el puesto al auxiliar
                    }
                }  //Si no hay ninguna sala libre toca esperar
                
                
                System.out.println("Las salas de Vacunación están llenas");
                ningunLibre.await();
            }
        } finally {
            libre.unlock();
        }
        
    }
    
    
    public void liberarSala(int id){ //Libera la sala de vacunación indicada
        libre.lock();
        System.out.println("Se libera la sala " + id);
        try {
            listaSalas[id] = true;
            ningunLibre.signal();
        } finally {
            libre.unlock();
        }
    }
    
    
    public void puestoLibre(){ //Baja el contador de atendidos, avisa de que ha quedado un puesto libre
        libre.lock();
        try {
            ocupados--;
            ningunPuesto.signal();
        } finally {
            libre.unlock();
        }
    }
    
    
    
    public void medicoAbandonaSala(int id){
        medicos.lock();
        //Se libera la sala en la que estaba el médico
        try {
            salasMedicos[id] = false;
        } finally {
            medicos.unlock();
        }
    }
    
    
    public PuestoVacunacion medicoEntraEnSala() throws InterruptedException{
        //El médico busca la primera sala disponible. Solo hay 10 salas y 10 médicos, supuestamente
        //siempre va a haber una sala para todos
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
                    System.out.println("El medico ha cogido una vacuna. Quedan " + vacunas + " vacunas disponibles.");
                    encontrado = true;
                    salasMedicos[posicion] = true;
                }
            }
            return puestos[posicion]; //Devolvemos al sanitario el puesto en el que va a vacunar
        } finally {
            medicos.unlock();
        }
    }
    
    public void getVacuna(){
        vacunas++;
        vacuna.release();
    }
    
}
