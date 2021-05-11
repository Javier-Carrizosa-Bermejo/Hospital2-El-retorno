/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Revij
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LinkedBlockingQueue<Paciente> pacientes = new LinkedBlockingQueue<Paciente>();
        Recepcion recepcion = new Recepcion(); 
        Auxiliar1 pedro = new Auxiliar1(recepcion, pacientes);
        
        for(int i = 0; i < 5;i ++){
            System.out.println("Ha funcionado");
        }
        
    }
    
}
