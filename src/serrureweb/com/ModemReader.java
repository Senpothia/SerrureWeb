package serrureweb.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Clock;
import java.util.Observable;
import java.util.Observer;
import serrureweb.Constants.Constants;
import serrureweb.SerrureWeb;

public class ModemReader implements Runnable {

    //   private String server = Constants.SERVER;
    //   private int port = Constants.PORT;
    private Socket socket;

    public ModemReader() {
    }

    public ModemReader(Socket socket) {

        this.socket = socket;

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("Lanment du recepteur");

        try {

            while (!socket.isClosed()) {

                InputStreamReader inr = new InputStreamReader(this.socket.getInputStream());
                BufferedReader br = new BufferedReader(inr);
                String commande = br.readLine();
                System.out.println("serrureweb.com.ModemReader.run()");
                System.out.println("Commande reçue: " + commande);
               
                notifierOrdre(commande);

                System.out.println("Fin");
            }

        } catch (IOException ex) {

        }

    }

    private void notifierOrdre(String commande) {

        SerrureWeb.commande = commande;
        SerrureWeb.changed = true;

    }

}
