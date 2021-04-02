
package serrureweb;


import serrureweb.com.Modem;
import serrureweb.com.ModemReader;
import serrureweb.com.ModemWriter;
import serrureweb.manager.Contexte;



public class SerrureWeb {
    
    /*
    public static Boolean marche;   // Etat de la séquence en cours
    public static Boolean pause;    // Etat de la séquence en cours
    public static Boolean actif;    // Etat de la séquence en cours
    public static String commande = "";  // Commande reçu du serveur et à traiter par le contrôleur
    public static Boolean changed = false;   // flag de notification d'une nouvelle commande à traier par le contrôleur
    */
    
    public static Contexte contexte = new Contexte();
    
    public static void main(String[] args) {
        
        Modem modem = new Modem();
        modem.open();
      

        // TODO code application logic here
    }

   

}
