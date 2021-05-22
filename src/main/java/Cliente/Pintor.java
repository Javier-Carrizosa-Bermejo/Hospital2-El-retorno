/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Pintor extends Thread{
    InterfazHospital interfaz;
    private ConcurrentHashMap<Integer, ArrayList<String>> informacion;
    Lock cerradura = new ReentrantLock();
    
    Pintor(){
        interfaz = new InterfazHospital();
        interfaz.setVisible(true);
        //interfaz.inicializarse();
    }
    
    @Override
    public void run(){
        while(true){
            
            pintar();
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pintor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void pintar(){
        cerradura.lock();
        try {
            if(informacion != null){
               colaPacientes(informacion.get(0));
               pacienteAtendido(informacion.get(1));
               salaDescanso(informacion.get(3));
               puestoDelAuxiliar(informacion.get(2));
               puestosDeVacunacion();
               puestosDeObservacion();
               puestoDelAuxiliar2(informacion.get(14));
               vacunasDisponibles(informacion.get(15));
            }

        } finally {
            cerradura.unlock();
        }
    }
    
    public void setInfo(ConcurrentHashMap<Integer, ArrayList<String>> informacion){
        cerradura.lock();
        try {
            this.informacion = informacion;
        } finally {
            cerradura.unlock();
        }
        
    }
    
    public void colaPacientes(ArrayList<String> pacientes){
        String textoColaPacientes = " ";
        String paciente;
        for(int j = 0; j < pacientes.size(); j++){
            
            if(pacientes.get(j).length() == 1){
                paciente = "P000" + pacientes.get(j);
            }
            else if(pacientes.get(j).length() == 2){
                paciente = "P00" + pacientes.get(j);
            }
            else if(pacientes.get(j).length() == 3){
                paciente = "P0" + pacientes.get(j);
            }
            else {
                paciente = "P" + pacientes.get(j);
            }
            textoColaPacientes = textoColaPacientes + paciente + " ";
        }
        interfaz.colaPacientesModificar(textoColaPacientes);
        
    }
    
    public void pacienteAtendido(ArrayList<String> pacientes){
        String texto = " ";
        String paciente;
        for(int j = 0; j < pacientes.size(); j++){
            if(pacientes.get(j).length() == 1){
                paciente = "P000" + pacientes.get(j);
            }
            else if(pacientes.get(j).length() == 2){
                paciente = "P00" + pacientes.get(j);
            }
            else if(pacientes.get(j).length() == 3){
                paciente = "P0" + pacientes.get(j);
            }
            else {
                paciente = "P" + pacientes.get(j);
            }
            texto= texto+ paciente + " ";
        }
        interfaz.pacienteAtendidoModificar(texto);
    }
    
    public void salaDescanso(ArrayList<String> durmientes){
        String texto = " ";
        String persona;
        for(int j = 0; j < durmientes.size(); j++){
            if(durmientes.get(j).length() == 1){
                persona = "S0" + durmientes.get(j);
            }
            else if(durmientes.get(j).length() == 2){
                persona = "S" + durmientes.get(j);
            }
            
            else {
                persona =  durmientes.get(j);
            }
            texto= texto + persona + " ";
        }
        interfaz.salaDescansoModificar(texto);
    }
    
    public void puestoDelAuxiliar(ArrayList<String> elAuxiliar){
        String texto = " ";
        for(int j = 0; j < elAuxiliar.size(); j++){
            texto= "Auxiliar1";
        }
        
        interfaz.modificarAuxiliar(texto);
    }
    
    public void puestoDelAuxiliar2(ArrayList<String> elAuxiliar){
        String texto = " ";
        for(int j = 0; j < elAuxiliar.size(); j++){
            texto= "Auxiliar2";
        }
        
        interfaz.modificarAuxiliar2(texto);
    }
    
    public void vacunasDisponibles(ArrayList<String> elAuxiliar){
        String texto = " ";
        for(int j = 0; j < elAuxiliar.size(); j++){
            texto= elAuxiliar.get(j);
        }
        
        interfaz.setVacunasDisponibles(texto);
    }
    
    public void puestosDeVacunacion(){
        for(int i = 4; i < 14; i++){
            interfaz.pintarVacunacion(stringPuestoVacunacion(informacion.get(i)), i);
        }
    }
    
    public String stringPuestoVacunacion(ArrayList<String> puesto){
        String texto = " ";
        if(puesto.size() == 1){
            if(puesto.get(0).length() == 1){
                texto = "S0" + puesto.get(0);
            }
            else if (puesto.get(0).length() == 2){
                texto = "S" + puesto.get(0);
            }
        }
        else if(puesto.size() == 2){
            if(puesto.get(0).length() == 1){
                texto = "S0" + puesto.get(0);
            }
            else if (puesto.get(0).length() == 2){
                texto = "S" + puesto.get(0);
            }
            
            if(puesto.get(1).length() == 1){
                texto = texto + " P000" + puesto.get(1);
            }
            else if(puesto.get(1).length() == 2){
                texto = texto + " P00" + puesto.get(1);
            }
            else if(puesto.get(1).length() == 3){
                texto = texto + " P0" + puesto.get(1);
            }
            else if(puesto.get(1).length() == 4){
                texto = texto + " P" + puesto.get(1);
            }
        }
        
        return texto;
        
    }

    public void puestosDeObservacion(){
        for(int i = 16; i < 36; i++){
            interfaz.pintarObservacion(stringPuestoObservacion(informacion.get(i)), i);
        }
    }
    
    
    public String stringPuestoObservacion(ArrayList<String> puesto){
        String texto = " ";
        if(puesto.size() == 1){
            if(puesto.get(0).length() == 1){
                texto = "P000" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 2){
                texto = "P00" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 3){
                texto = "P0" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 4){
                texto = "P" + puesto.get(0);
            }
        }
        else if(puesto.size() == 2){
            if(puesto.get(0).length() == 1){
                texto = "P000" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 2){
                texto = "P00" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 3){
                texto = "P0" + puesto.get(0);
            }
            else if(puesto.get(0).length() == 4){
                texto = "P" + puesto.get(0);
            }
            
            if(puesto.get(1).length() == 1){
                texto = texto + " S0" + puesto.get(1);
            }
            else if (puesto.get(1).length() == 2){
                texto = texto + " S" + puesto.get(1);
            }
        }
        
        return texto;
        
    }
    
    public ArrayList<Integer> getPuestosACerrar(){
        return interfaz.getPuestosACerrar();
    }
    
    public void limpiarPuestos(){
        interfaz.limpiarPuestosACerrar();
    }
}
