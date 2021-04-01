package serrureweb.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import serrureweb.Constants.Constants;
import serrureweb.manager.Controleur;

public class ModemWriter implements Runnable, Observer  {

    // private String server = Constants.SERVER;
    //private int port = Constants.PORT;
    private Socket socket;
    private Controleur controleur = new Controleur();

    public ModemWriter() {

        // this.server = Constants.SERVER;
        //this.port = Constants.PORT;
    }

    public ModemWriter(Socket socket) {

        //   this.server = server;
        //   this.port = port;
        this.socket = socket;

    }

    @Override
    public void run() {

        System.out.println("Lancement reepteur");
        try {
            int i = 0;
            while(!socket.isClosed()) {
                
                
                PrintWriter writer = null;
                writer = new PrintWriter(this.socket.getOutputStream());
                System.out.println("serrureweb.com.ModemWriter.run()");
                String rapport = "pi:>MARCHE>true>1000>false>false>false>false>2000>true>true>false>false>3000>true>false>false>false>6";
                writer.println(rapport);
                writer.flush();
                System.out.println("Fin: " + i);
                i++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ModemWriter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }  // fin while

        } catch (IOException ex) {

        }

    }

    @Override
    public void update(Observable o, Object arg) {
       
        
        
    }

}
