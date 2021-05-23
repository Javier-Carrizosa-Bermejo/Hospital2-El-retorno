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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author luismi
 */
public class Log {
    private int mensaje, contador;
    //generosF.delete();
    static String sep = File.separator;
    static Path path = Paths.get("evolucionHospital.txt");
    //File generosF = new File(sep + "temp" + sep + "evolucionHospital.txt");
    File generosF = new File("evolucionHospital.txt");
    PrintWriter pw = new PrintWriter(generosF);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss.SSS");
   
   Log(int mensaje) throws FileNotFoundException{
       this.mensaje = mensaje;
       PrintWriter pw = new PrintWriter(generosF);
   } 
   
   public static void borrarArchivo(String path){
       File file = new File(path);
       if(file.delete()){
           System.out.println("Se elimino el fichero.");
       }
            
       
   }
    
   public void escritura(int mensaje, String var1, String var2, String var3) {
        try {           
            switch (mensaje){
                case 0:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Paciente " + var1 + " vacunado en el puesto " + var2 + " por " + var3 + ".");
                    break;
                case 1:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Paciente " + var1 + " ha acudido sin cita.");
                    contador ++;
                    break;
                case 2:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Paciente " + var1 + " sufre una reacción y es atendido por " + var2 +".");
                    break; 
                case 3:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " " + var1 + " comienza su descanso.");
                    break;
                case 4:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " " + var1 + " se marcha a casa del puesto " + var2 + ".");
                    contador ++;
                    break;
                case 5:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Auxiliar A2 comienza su descanso. ");
                    break;
                case 6:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Se han vacunado 15 pacientes, " + var1 + " procede a cerrarse");
                    break; 
                case 7:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Auxiliar A1 termina su descanso. ");
                    break;
                case 8: 
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " El auxiliar 2 ha añadido una vacuna. Hay " + var1 + " vacunas disponibles. ");
                    break;
                case 9: 
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " El auxiliar 2 ha terminado su trabajo. Comienza a recoger sus cosas y se marcha a casa.");
                    break;
                case 10:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " El medico " + var1 + " entra en la sala " + var2);
                    break;
                case 11:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " No hay ningún puesto disponible");
                    break;
                case 12:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Las salas de Vacunación están llenas");
                    break;
                case 13:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Se libera la sala " + var1);
                    break;
                case 14:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " El medico ha cogido una vacuna. Quedan " + var1 + " vacunas disponibles.");
                    break;
                case 15:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Sanitario " + var1 + " entra en el hospital");
                    break;
                case 16:
                    pw.println("Momento de ejecución -> "+dtf.format(LocalDateTime.now()) + " Se abre el hospital.");
                    break;
            }
            if (contador == 2000){
                System.out.println("Se cierra el archivo");
                pw.close();
            }            
            
        } catch (Exception e) {
                throw e;
        }
    }
       
    
}