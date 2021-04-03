package serrureweb.manager;

import serrureweb.OrderProcessor;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Sequence;
import serrureweb.SerrureWeb;

public class Controleur extends Observable {

    public Resultat resultat = new Resultat();

    // Contexte contexte = new Contexte();
    private Boolean actif = false;

    // GPIO  -- Version Raspberry
    /*
    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalOutput relais1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "REL1", PinState.LOW);
    final GpioPinDigitalOutput relais2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "REL2", PinState.LOW);
    final GpioPinDigitalOutput relais3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "REL3", PinState.LOW);

    final GpioPinDigitalInput sensor1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput sensor2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput sensor3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);

    final GpioPinDigitalInput contact1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput contact2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput contact3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_UP);
     */
    private boolean stateSensor1;
    private boolean stateSensor2;
    private boolean stateSensor3;

    private boolean stateContact1;
    private boolean stateContact2;
    private boolean stateContact3;

    // pour test
    /*
    private GpioPinDigitalOutput[] relais = {relais1, relais2, relais3};
    private GpioPinDigitalInput[] sensors = {sensor1, sensor2, sensor3};
    private GpioPinDigitalInput[] contacts = {contact1, contact2, contact3};
     */
    public void start() {

        System.out.println("***** démarrage séquence de test du contrôleur  *****");
        boolean echValide = SerrureWeb.contexte.getActifs()[0] || SerrureWeb.contexte.getActifs()[1] || SerrureWeb.contexte.getActifs()[2];
        resultat.setErreurs(SerrureWeb.contexte.getErreurs());

        while (!SerrureWeb.contexte.getActif()) {

          //  System.out.println("En attente de démarrage");
        }

        while (SerrureWeb.contexte.getActif()) {

            while (SerrureWeb.contexte.getMarche() && echValide) {

                if (!SerrureWeb.contexte.getPause()) {

                    System.out.println("***** Nouvelle sequence  *****");
                    /*
                    for (int i = 0; i < 3; i++) {

                        if (SerrureWeb.contexte.getActifs()[i]) {

                            // activer relais
                            System.out.println("Activation relais: " + i);
                            // relais[i].high();
                            // delai anti-rebond
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            // désactiver relais
                            // relais[i].low();
                            // lecture sensor
                            System.out.println("Lecture sensor: " + i);
                            // boolean sensor = sensors[i].isHigh();

                            // lecture contact
                            System.out.println("Lecture contact: " + i);
                            // boolean contact = contacts[i].isHigh();
                            // incrémentation compteur - invalidation echantillon

                            Boolean sensor = false;  // pour test
                            Boolean contact = false; // pour test

                            if (!sensor && !contact) {

                                SerrureWeb.contexte.getTotaux()[i]++;

                                System.out.println("Total echantillon:" + i + " " + SerrureWeb.contexte.getTotaux()[i]);

                            } else {

                                SerrureWeb.contexte.getActifs()[i] = false;
                                SerrureWeb.contexte.getErreurs()[i] = true;

                                System.out.println("Test échoué echantillon:" + i);
                            }

                            notifierResultat();

                        }

                    }*/

                    methodeDetest();

                } else {

                    while (SerrureWeb.contexte.getPause()) {

                        System.err.println("Controleur en Pause");

                    }

                }

                echValide = SerrureWeb.contexte.getActifs()[0] || SerrureWeb.contexte.getActifs()[1] || SerrureWeb.contexte.getActifs()[2];

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        System.out.println("***** Fin de sequence  *****");
    }

    public Resultat getResultat() {
        return resultat;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public void initResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public void setContexte(Contexte contexte) {
        SerrureWeb.contexte = contexte;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public void notifierResultat() {   // notification au ModemWriter

        resultat.setErreurs(SerrureWeb.contexte.getErreurs());
        resultat.setTotaux(SerrureWeb.contexte.getTotaux());
        resultat.setActifs(SerrureWeb.contexte.getActifs());
        resultat.setPauses(SerrureWeb.contexte.getPauses());
        resultat.setInterrompus(SerrureWeb.contexte.getInterrompus());
        resultat.setFin(!SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getActifs()[2]);
        this.setChanged();
        this.notifyObservers(this.getResultat());

    }

    private void methodeDetest() {

        /*
        long compteur1 = SerrureWeb.contexte.getTotaux()[0];
        long compteur2 = SerrureWeb.contexte.getTotaux()[1];
        long compteur3 = SerrureWeb.contexte.getTotaux()[2];
         */
        System.out.println("*****   Test");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controleur.class.getName()).log(Level.SEVERE, null, ex);
        }
        long compteur1 = SerrureWeb.contexte.getTotaux()[0]++;
        long compteur2 = SerrureWeb.contexte.getTotaux()[1]++;
        long compteur3 = SerrureWeb.contexte.getTotaux()[2]++;
        long totaux[] = {compteur1, compteur2, compteur3};
        SerrureWeb.contexte.setTotaux(totaux);
        notifierResultat();
        System.out.println("Din Test    ************");

    }

}
