package serrureweb;

import serrureweb.com.Modem;
import serrureweb.com.ModemReader;
import serrureweb.com.ModemWriter;
import serrureweb.manager.Contexte;

public class SerrureWeb {

    public static Contexte contexte = new Contexte();

    public static void main(String[] args) {

        Modem modem = new Modem();
        modem.open();

    }

}
