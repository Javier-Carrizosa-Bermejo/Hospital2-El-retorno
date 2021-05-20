/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

/**
 *
 * @author Revij
 */
import java.util.ArrayList; 
import java.util.concurrent.ConcurrentHashMap;

public class Registro {
    ConcurrentHashMap<Integer, ArrayList<String>> informacion = new ConcurrentHashMap<>();
    
    Registro(){
        for(int i = 0;i < 36 ;i++){
            informacion.put(i, new ArrayList<>()); 
        }
        
    }
    

    public void nuevoPacienteCola(int id){
        informacion.get(0).add(String.valueOf(id));
    }
    
    public void fueraPacienteCola(){
        informacion.get(0).remove(0);
    }
    
    public void atendidoEnRecepcion(int id){
        informacion.get(1).add(String.valueOf(id));
    }
    
    public void fueraDeRecepcion(){
        informacion.get(1).remove(0);
    }
    
    public void auxiliarTrabaja(){
        informacion.get(3).remove("Auxiliar1");
        informacion.get(2).add("Auxiliar1");
    }
    
    public void descansa(){
        informacion.get(2).remove("Auxiliar1");
        informacion.get(3).add("Auxiliar1");
    }
    
    public void medicoEntraEnSala(int id, int puesto){
         informacion.get(puesto + 4).add(String.valueOf(id));
        
    }
    
    public void medicoAbandonaSala(int puesto){
         informacion.get(3).add(informacion.get(puesto + 4).get(0));
         informacion.get(puesto + 4).remove(0);
        
    }
    
    public void medicoAbandonaDescanso(int id_med){
        informacion.get(3).remove(String.valueOf(id_med));
    }
    
    public void pacienteSeVacuna(int id, int puesto){
        informacion.get(puesto + 4).add(String.valueOf(id));
    }
    
    public void pacienteSaleDeVacunacion(int puesto){
        informacion.get(puesto + 4).remove(1);
    }
    
    public void auxiliar2Trabaja(){
        informacion.get(3).remove("Auxiliar2");
        informacion.get(14).add("Auxiliar2");
    }
    
    public void auxiliar2Descansa(){
        informacion.get(3).add("Auxiliar2");
        informacion.get(14).remove("Auxiliar2");
    }
    
    public void seModificanVacunas(int vacunas){
        informacion.get(15).add(String.valueOf(vacunas));
        
    }
    
    public void pacienteEntraObersvacion(int puestos, int id){
        informacion.get(16 + puestos).add(String.valueOf(id));
    }
    
    public void pacienteSaleDeObersvacion(int puestos){
        informacion.get(16 + puestos).remove(0);
    }
    
    public void MedicoCuraEnObersvacion(int puestos, int id_med){
        informacion.get(16 + puestos).add(String.valueOf(id_med));
    }
    
    public void MedicoMarchaDeObersvacion(int puestos, int id_med){
        informacion.get(16 + puestos).remove(1);
    }
    
    public ConcurrentHashMap<Integer, ArrayList<String>> getInformacion(){
        return informacion;
    }
}

/*
0 - cola de pacientes
1 - paciente atendido por el auxiliar
2 - auxiliar
3 - sala de descanso
4-13 - Puestos de vacunacion
14 - auxiliar2
15 - vacunas
16-35 - puestos de observacion
*/