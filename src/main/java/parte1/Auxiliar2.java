/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luismi
 */
public class Auxiliar2 extends Thread {

    private Recepcion recepcion;
    public Semaphore vacuna = new Semaphore(0);

    Auxiliar2(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < 20; i++) {
                    recepcion.getVacuna();
                    System.out.println("El auxiliar 2 ha añadido una vacuna. Hay " + recepcion.vacunas + " vacunas disponibles.");
                    sleep((int) ((Math.random() * 6) + 5) * 100);
                }
                System.out.println("El auxiliar 2 se va a descansar.");
                sleep((int) ((Math.random() * 4) + 1) * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Auxiliar1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}