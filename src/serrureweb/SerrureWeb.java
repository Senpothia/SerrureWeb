
package serrureweb;


import serrureweb.com.Modem;
import serrureweb.com.ModemReader;
import serrureweb.com.ModemWriter;



public class SerrureWeb {
    
    public static Boolean marche;   
    public static Boolean pause;
    public static Boolean actif;   // Etat de la séquence en cours
    public static String commande = "";
    public static Boolean changed = false;
  
    
    public static void main(String[] args) {
        
        Modem modem = new Modem();
        modem.open();
      

        // TODO code application logic here
    }

   

}
