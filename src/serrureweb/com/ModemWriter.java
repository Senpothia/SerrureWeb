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

public class ModemWriter implements Runnable, Observer {

    // private String server = Constants.SERVER;
    //private int port = Constants.PORT;
    private Socket socket;
    private Controleur controleur;
    public boolean[] echantillons = {false, false, false};
    public long[] totaux = {0, 0, 0};
    public boolean[] erreurs = {false, false, false};
    public boolean[] actifs = {false, false, false};
    public boolean envoyer = false;
    private String rapport;
    
    public ModemWriter() {
        
    }
    
    public ModemWriter(Socket socket) {
        
        this.socket = socket;
        this.controleur = getControleur();
        
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public Controleur getControleur() {
        
        if (this.controleur == null) {
            this.controleur = new Controleur();
            this.controleur.getContexte().setActifs(this.actifs);
            this.controleur.getContexte().setTotaux(this.totaux);
            this.controleur.getContexte().setErreurs(this.erreurs);
            SerrureWeb.contexte.setMarche(false);
            SerrureWeb.contexte.setActif(true);
            this.controleur.addObserver(this);
            
        }
        return this.controleur;
    }
    
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }
    
    @Override
    public void run() {
        
        System.out.println("Lancement de l'émetteur");
        // this.controleur.start(); // démarrage du contrôleur
        try {
            
            while (!socket.isClosed()) {
                
                if (SerrureWeb.contexte.getMarche() && !controleur.getActif()) {
                    
                    this.controleur.start();
                    System.out.println("Contrôleur lancé!");
                    // SerrureWeb.changed = false;
                    SerrureWeb.contexte.setChanged(false);
                    
                }
                PrintWriter writer = null;
                writer = new PrintWriter(this.socket.getOutputStream());
                //System.out.println("serrureweb.com.ModemWriter.run()");
                if (envoyer) {
                    
                    writer.println(rapport);
                    writer.flush();
                    System.out.println("Fin de l'envoie du rapport");
                }
                
            }  // fin while

        } catch (IOException ex) {
            
        }
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        Resultat resultat = (Resultat) arg;
        rapport = "pi:>";
        
        if (SerrureWeb.contexte.getMarche()) {
            
            rapport = rapport + "MARCHE>";
        }
        
        if (!SerrureWeb.contexte.getMarche() && !SerrureWeb.contexte.getPause()) {
            
            rapport = rapport + "ARRET>";
        }
        if (SerrureWeb.contexte.getPause()) {
            
            rapport = rapport + "PAUSE>";
        }
        
        if (resultat.isFin()) {  // "TOUS LES ECHANTILLONS SONT EN ERREUR. FIN DE TEST"

            rapport = rapport + "false>";
        }

        //***************** Rapport échantillon 1 ***********************
        Long total1 = resultat.getTotaux()[0];
        String compteur1 = Long.toString(total1);
        rapport = rapport + compteur1 + ">";
        
        Boolean actif1 = resultat.getActifs()[0];
        
        if (actif1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean erreur1 = resultat.getErreurs()[0];
        
        if (erreur1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean pause1 = resultat.getPauses()[0];
        
        if (pause1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean interrompu1 = resultat.getInterrompus()[0];
        
        if (interrompu1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 1 *************
        //***************** Rapport échantillon 2 ***********************
        Long total2 = resultat.getTotaux()[1];
        String compteur2 = Long.toString(total2);
        rapport = rapport + compteur1 + ">";
        
        Boolean actif2 = resultat.getActifs()[1];
        
        if (actif1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean erreur2 = resultat.getErreurs()[1];
        
        if (erreur2) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean pause2 = resultat.getPauses()[1];
        
        if (pause1) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean interrompu2 = resultat.getInterrompus()[1];
        
        if (interrompu2) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 2 *************
        //***************** Rapport échantillon 3 ***********************
        Long total3 = resultat.getTotaux()[2];
        String compteur3 = Long.toString(total3);
        rapport = rapport + compteur1 + ">";
        
        Boolean actif3 = resultat.getActifs()[2];
        
        if (actif3) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean erreur3 = resultat.getErreurs()[2];
        
        if (erreur3) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean pause3 = resultat.getPauses()[2];
        
        if (pause3) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }
        
        Boolean interrompu3 = resultat.getInterrompus()[2];
        
        if (pause3) {
            
            rapport = rapport + "true>";
            
        } else {
            
            rapport = rapport + "false>";
        }

        //*********** Fin rapport echantillon 3 *************
        rapport = rapport + ">#";
        envoyer = true;
    }
    
}
