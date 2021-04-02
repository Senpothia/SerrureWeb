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
import serrureweb.OrderProcessor;
import serrureweb.SerrureWeb;

public class ModemReader implements Runnable {

    private Socket socket;
    private OrderProcessor orderProcessor;

    public ModemReader() {
    }

    public ModemReader(Socket socket) {

        this.socket = socket;
        this.orderProcessor = new OrderProcessor();

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("Lancement du recepteur");

        try {

            while (!socket.isClosed()) {

                System.out.println("Init lecture");
                InputStreamReader inr = new InputStreamReader(this.socket.getInputStream());
                BufferedReader br = new BufferedReader(inr);
                String commande = br.readLine();

                System.out.println("Commande reçue: " + commande);
                orderProcessor.analyser(commande);
                
                System.out.println("Fin boucle de lecture");
            }

        } catch (IOException ex) {

        }

    }

    private void notifierOrdre(String commande) {

        SerrureWeb.contexte.setCommande(commande);
        SerrureWeb.contexte.setChanged(true);

    }


}
