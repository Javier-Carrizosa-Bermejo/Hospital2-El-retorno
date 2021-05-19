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
    private escrituraSegura escrituraS;
    private Recepcion recepcion;
    public Semaphore vacuna = new Semaphore(0);

    Auxiliar2(Recepcion recepcion, escrituraSegura escrituraS) {
        this.escrituraS = escrituraS;
        this.recepcion = recepcion;
    }

    @Override
    public void run() {
        for (int j = 0; j <= 100; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    recepcion.getVacuna();
                    String var1 = Integer.toString(recepcion.vacunas);
                    escrituraS.escritura(8, var1, "", "");
                    //System.out.println("El auxiliar 2 ha añadido una vacuna. Hay " + recepcion.vacunas + " vacunas disponibles.");
                    sleep((int) ((Math.random() * 6) + 5) * 100);
                }
                escrituraS.escritura(5, "", "", "");
                //System.out.println("El auxiliar 2 se va a descansar.");
                sleep((int) ((Math.random() * 4) + 1) * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Auxiliar1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        escrituraS.escritura(9,"","","");
        //System.out.println("El auxiliar 2 ha terminado su trabajo. Comienza a recoger sus cosas y se marcha a casa. ");
    }
}