package serrureweb.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import serrureweb.Constants.Constants;
import serrureweb.SerrureWeb;
import serrureweb.manager.Controleur;
import serrureweb.manager.Resultat;

public class ModemWriter implements Runnable {

    private Socket socket;
    // private Controleur controleur;

    //public boolean envoyer = false;
    //private String rapport;

    public ModemWriter() {

    }

    public ModemWriter(Socket socket) {

        this.socket = socket;
        // this.controleur = getControleur();

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /*
    public Controleur getControleur() {
        
        if (this.controleur == null) {
            this.controleur = new Controleur();
            this.controleur.addObserver(this);
            
        }
        return this.controleur;
    }
    
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }
     */
    @Override
    public void run() {

        System.out.println("Lancement de l'émetteur");
        // this.controleur.start(); // démarrage du contrôleur
        try {

            while (!socket.isClosed()) {

                PrintWriter writer = null;
                writer = new PrintWriter(this.socket.getOutputStream());

                if (SerrureWeb.contexte.getChanged()) {
                    System.out.println("Nouveau rapport rapport");
                    String rapport = updateRapport();
                    writer.println(rapport);
                    System.out.println("Rapport: " + rapport);
                    writer.flush();
                    System.out.println("Fin de l'envoie du rapport");
                    SerrureWeb.contexte.setChanged(false);
                }

            }  // fin while

        } catch (IOException ex) {

        }

    }

    // @Override
    public String updateRapport() {

        // Resultat SerrureWeb.contexte = (Resultat) arg;
        String rapport = "pi:>";

        if (SerrureWeb.contexte.getMarche()) {

            rapport = rapport + "MARCHE>";
        }

        if (!SerrureWeb.contexte.getMarche() && !SerrureWeb.contexte.getPause()) {

            rapport = rapport + "ARRET>";
        }
        if (SerrureWeb.contexte.getPause()) {

            rapport = rapport + "PAUSE>";
        }

        if (SerrureWeb.contexte.getFin()) {  // "TOUS LES ECHANTILLONS SONT EN ERREUR. FIN DE TEST"

            rapport = rapport + "false>";
            
        }else {
        
         rapport = rapport + "true>";
         
        }

        //***************** Rapport échantillon 1 ***********************
        Long total1 = SerrureWeb.contexte.getTotaux()[0];
        String compteur1 = Long.toString(total1);
        rapport = rapport + compteur1 + ">";

        Boolean actif1 = SerrureWeb.contexte.getActifs()[0];

        if (actif1) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean erreur1 = SerrureWeb.contexte.getErreurs()[0];

        if (erreur1) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean pause1 = SerrureWeb.contexte.getPauses()[0];

        if (pause1) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean interrompu1 = SerrureWeb.contexte.getInterrompus()[0];

        if (interrompu1) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 1 *************
        //***************** Rapport échantillon 2 ***********************
        Long total2 = SerrureWeb.contexte.getTotaux()[1];
        String compteur2 = Long.toString(total2);
        rapport = rapport + compteur2 + ">";

        Boolean actif2 = SerrureWeb.contexte.getActifs()[1];

        if (actif2) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean erreur2 = SerrureWeb.contexte.getErreurs()[1];

        if (erreur2) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean pause2 = SerrureWeb.contexte.getPauses()[1];

        if (pause2) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean interrompu2 = SerrureWeb.contexte.getInterrompus()[1];

        if (interrompu2) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 2 *************
        //***************** Rapport échantillon 3 ***********************
        Long total3 = SerrureWeb.contexte.getTotaux()[2];
        String compteur3 = Long.toString(total3);
        rapport = rapport + compteur3 + ">";

        Boolean actif3 = SerrureWeb.contexte.getActifs()[2];

        if (actif3) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean erreur3 = SerrureWeb.contexte.getErreurs()[2];

        if (erreur3) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean pause3 = SerrureWeb.contexte.getPauses()[2];

        if (pause3) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        Boolean interrompu3 = SerrureWeb.contexte.getInterrompus()[2];

        if (interrompu3) {

            rapport = rapport + "true>";

        } else {

            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 3 *************
        rapport = rapport + "#";
        //envoyer = true;
        
        return rapport;
    }

}
