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

        System.out.println("Lancement du recepteur");

        try {

            while (!socket.isClosed()) {

                System.out.println("Init lecture");
                InputStreamReader inr = new InputStreamReader(this.socket.getInputStream());
                BufferedReader br = new BufferedReader(inr);
                String commande = br.readLine();
                System.out.println("serrureweb.com.ModemReader.run()");
                System.out.println("Commande reçue: " + commande);

                if (!SerrureWeb.contexte.getMarche()) {

                    if (commande.startsWith("@SERV:>START>")) {

                        String[] strings = commande.split(">");
                        decodage(strings);

                        System.out.println("Mise en marche séquence");
                        SerrureWeb.contexte.setMarche(true);

                    }

                } else {

                    notifierOrdre(commande);

                }

                System.out.println("Fin boucle de lecture");
            }

        } catch (IOException ex) {

        }

    }

    private void notifierOrdre(String commande) {

     
        SerrureWeb.contexte.setCommande(commande);
        SerrureWeb.contexte.setChanged(true);

    }

    private void decodage(String[] strings) {

        long[] totaux = {0, 0, 0};
        totaux[0] = Long.parseLong(strings[3]);
        totaux[1] = Long.parseLong(strings[13]);
        totaux[2] = Long.parseLong(strings[23]);
        SerrureWeb.contexte.setTotaux(totaux);

        boolean[] actifs = {false, false, false};
        actifs[0] = Boolean.parseBoolean(strings[4]);
        actifs[1] = Boolean.parseBoolean(strings[14]);
        actifs[2] = Boolean.parseBoolean(strings[24]);

        boolean[] erreurs = {false, false, false};
        erreurs[0] = Boolean.parseBoolean(strings[7]);
        erreurs[1] = Boolean.parseBoolean(strings[17]);
        erreurs[2] = Boolean.parseBoolean(strings[27]);

        boolean[] pauses = {false, false, false};
        pauses[0] = Boolean.parseBoolean(strings[9]);
        pauses[1] = Boolean.parseBoolean(strings[19]);
        pauses[2] = Boolean.parseBoolean(strings[29]);

        boolean[] interrompus = {false, false, false};
        interrompus[0] = Boolean.parseBoolean(strings[11]);
        interrompus[1] = Boolean.parseBoolean(strings[21]);
        interrompus[2] = Boolean.parseBoolean(strings[31]);
       // SerrureWeb.contexte.setCommande(commande);
        SerrureWeb.contexte.setChanged(true);

    }

}
