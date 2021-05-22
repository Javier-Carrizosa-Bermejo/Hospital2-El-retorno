/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Revij
 */
public class Ayudante extends Thread {
    
    private Recepcion recepcion;
    private int puesto;
    
    Ayudante(Recepcion recepcion, int puesto){
        this.recepcion = recepcion;
        this.puesto = puesto;
    }
    
    @Override
    public void run(){
        try {
            recepcion.cerrarSala(puesto);
        } catch (InterruptedException ex) {
            Logger.getLogger(Ayudante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
