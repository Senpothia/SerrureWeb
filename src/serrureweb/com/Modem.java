package serrureweb.com;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serrureweb.Constants.Constants;
import serrureweb.manager.Controleur;

public class Modem {

    private boolean isRunning = true;

    public Modem() {
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void open() {

        System.out.println("Modem process open");
        Thread modemProcess = new Thread(new Runnable() {

            public void run() {
                System.out.println("Lancement modem");

                try {

                    Socket socket = new Socket(Constants.LOCAL, Constants.PORT);
                    
                    Thread emetteur = new Thread(new ModemWriter(socket));
                    emetteur.start();

                    Thread recepteur = new Thread(new ModemReader(socket));
                    recepteur.start();
                    
                    Thread Controleur = new Thread(new Controleur());
                    Controleur.start();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
           

        });

        modemProcess.start();
    }
}
