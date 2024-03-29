package serrureweb.manager;

import com.pi4j.io.gpio.GpioController;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Sequence;
import serrureweb.SerrureWeb;

public class Controleur extends Observable implements Runnable {

    // public Resultat resultat = new Resultat();
    // Contexte contexte = new Contexte();
    private Boolean actif = false;

    // GPIO  -- Version Raspberry
    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalOutput relais1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "REL1", PinState.LOW);
    final GpioPinDigitalOutput relais2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "REL2", PinState.LOW);
    final GpioPinDigitalOutput relais3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "REL3", PinState.LOW);
    final GpioPinDigitalOutput alarm = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "LED1", PinState.LOW);
    final GpioPinDigitalOutput temoin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "LED1", PinState.LOW);

    final GpioPinDigitalInput sensor1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput sensor2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput sensor3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);

    final GpioPinDigitalInput contact1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput contact2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput contact3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_UP);

    private boolean stateSensor1;
    private boolean stateSensor2;
    private boolean stateSensor3;

    private boolean stateContact1;
    private boolean stateContact2;
    private boolean stateContact3;

    // pour test
    private GpioPinDigitalOutput[] relais = {relais1, relais2, relais3};
    private GpioPinDigitalInput[] sensors = {sensor1, sensor2, sensor3};
    private GpioPinDigitalInput[] contacts = {contact1, contact2, contact3};

    private int interTest = 24000;

    public void start() {

        boolean echValide = SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getErreurs()[0] || SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getErreurs()[1] || SerrureWeb.contexte.getActifs()[2] && !SerrureWeb.contexte.getErreurs()[2];
        temoin.high();
        while (true) {

            System.out.println("***** démarrage séquence de test du contrôleur  *****");

            if (SerrureWeb.contexte.getErreur()) {

                temoin.low();     // Erreur sur l'interface
                alarm.high();     // erreur de connexion avec le serveur

            } else {

                temoin.high();
                alarm.low();

            }
            // 
            //resultat.setErreurs(SerrureWeb.contexte.getErreurs());
            while (!SerrureWeb.contexte.getActif() && !SerrureWeb.contexte.getErreur()) {

                System.out.println("Actif? " + SerrureWeb.contexte.getActif());
                System.out.println("En attente de démarrage");
            }

            while (SerrureWeb.contexte.getActif() && !SerrureWeb.contexte.getErreur()) {

                echValide = SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getErreurs()[0] || SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getErreurs()[1] || SerrureWeb.contexte.getActifs()[2] && !SerrureWeb.contexte.getErreurs()[2];
                System.out.println("Actif? " + SerrureWeb.contexte.getActif());
                System.out.println("Marche? " + SerrureWeb.contexte.getMarche());
                System.out.println("echValide? " + echValide);
                int k = 0;

                if (echValide) {

                    if (SerrureWeb.contexte.getErreur()) {

                        temoin.low();     // Erreur sur l'interface
                        alarm.high();     // erreur de connexion avec le serveur

                    } else {

                        temoin.high();
                        alarm.low();

                    }

                } else {

                    temoin.high();   // Statut de fonctionnement de l'interface OK
                    alarm.high();    // Erreur sur tous les échantillons
                }

                while (SerrureWeb.contexte.getMarche() && echValide) {

                    if (!SerrureWeb.contexte.getPause()) {

                        System.out.println("******** DEBUT DE SEQUENCE *********");

                        //  methodeDetest(i);
                        k++;
                        sequence(k);
                        System.out.println("******** FIN DE SEQUENCE *********");

                    } else {

                        while (SerrureWeb.contexte.getPause()) {

                            System.err.println("!!!!!!!   Controleur en Pause   !!!!!!!");

                        }

                    }

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    echValide = SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getErreurs()[0] || SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getErreurs()[1] || SerrureWeb.contexte.getActifs()[2] && !SerrureWeb.contexte.getErreurs()[2];

                }

            }
            System.out.println("***** Fin de sequence  *****");
            SerrureWeb.contexte.setActif(false);

        }

    }

    /*
    public Resultat getResultat() {
        return resultat;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public void initResultat(Resultat resultat) {
        this.resultat = resultat;
    }
     */
    public void setContexte(Contexte contexte) {
        SerrureWeb.contexte = contexte;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    /*
    public void notifierResultat() {   // notification au ModemWriter - Verion pattern Observer

        resultat.setErreurs(SerrureWeb.contexte.getErreurs());
        resultat.setTotaux(SerrureWeb.contexte.getTotaux());
        resultat.setActifs(SerrureWeb.contexte.getActifs());
        resultat.setPauses(SerrureWeb.contexte.getPauses());
        resultat.setInterrompus(SerrureWeb.contexte.getInterrompus());
        resultat.setFin(!SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getActifs()[2]);
        this.setChanged();
        this.notifyObservers(this.getResultat());

    }
     */
    public void notifierResultat() {   // notification au ModemWriter - Version standard

        SerrureWeb.contexte.setFin(!SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getActifs()[2]);
        SerrureWeb.contexte.setChanged(true);
    }

    private void methodeDetest(int i) {

        System.out.println("*****   Test: " + i);
        long compteur1 = SerrureWeb.contexte.getTotaux()[0];
        long compteur2 = SerrureWeb.contexte.getTotaux()[1];
        long compteur3 = SerrureWeb.contexte.getTotaux()[2];

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controleur.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (SerrureWeb.contexte.getActif()) {

            if (!SerrureWeb.contexte.getPause()) {

                if (SerrureWeb.contexte.getActifs()[0] && !SerrureWeb.contexte.getPauses()[0]) {

                    compteur1 = SerrureWeb.contexte.getTotaux()[0] + 1L;
                    System.out.println("Compteur 1: " + compteur1);
                }

                if (SerrureWeb.contexte.getPauses()[0]) {

                    System.err.println("Echantillon 1 en pause");
                }

                if (SerrureWeb.contexte.getInterrompus()[0]) {

                    System.err.println("Echantillon 1 en interrompu");
                }

                if (SerrureWeb.contexte.getActifs()[1] && !SerrureWeb.contexte.getPauses()[1]) {

                    compteur2 = SerrureWeb.contexte.getTotaux()[1] + 1L;
                    System.out.println("Compteur 2: " + compteur2);
                }

                if (SerrureWeb.contexte.getPauses()[1]) {

                    System.err.println("Echantillon 2 en pause");
                }

                if (SerrureWeb.contexte.getInterrompus()[1]) {

                    System.err.println("Echantillon 2 en interrompu");
                }

                if (SerrureWeb.contexte.getActifs()[2] && !SerrureWeb.contexte.getPauses()[2]) {

                    compteur3 = SerrureWeb.contexte.getTotaux()[2] + 1L;
                    System.out.println("Compteur 3: " + compteur3);
                }

                if (SerrureWeb.contexte.getPauses()[2]) {

                    System.err.println("Echantillon 3 en pause");
                }

                if (SerrureWeb.contexte.getInterrompus()[2]) {

                    System.err.println("Echantillon 3 en interrompu");
                }

                long totaux[] = {compteur1, compteur2, compteur3};

                SerrureWeb.contexte.setTotaux(totaux);
                notifierResultat();
                System.out.println("Fin de Test    ************");

            } else {

                System.out.println("****   La séquende est en pause   ******");
            }

        } else {

            System.out.println("La séquence de test a été arrêté");
        }

    }

    void sequence(int k) {

        System.out.println("**************   Test: " + k + "*********************");
// sequence de test
/*
        System.out.println("*****   Test: " + i);
        // activer relais   
        System.out.println("Activation relais: ");
        relais[0].high();
        // delai anti-rebond
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }

        // désactiver relais
        relais[0].low();
         */
// Fin sequence de test

        for (int i = 0; i < 3; i++) {

            if (SerrureWeb.contexte.getActifs()[i] && !SerrureWeb.contexte.getPauses()[i] && !SerrureWeb.contexte.getErreurs()[i]) {

                // activer relais
                System.out.println("----------------------------------------------");
                System.err.println(">>>>>>>   Echantillon:" + i + " ACTIF");
                System.out.println("Activation relais: " + i);

                boolean sensor = sensors[i].isHigh();
                System.out.println("Lecture sensor AV: " + i + " " + sensor);

                boolean contact = contacts[i].isHigh();
                System.out.println("Lecture contact AV: " + i + " " + contact);

                System.out.println("%%%%%%% TYPE ECHANTILLON " + i + " " + SerrureWeb.contexte.getTypes()[i]);

                if (SerrureWeb.contexte.getTypes()[i].equals("DX200I")) {

                    if (SerrureWeb.contexte.getContacteurs()[i]) {  // Validation test des contacteurs

                        if (!sensor && contact) {

                            System.out.println("#######   CONTACTS ECHANTILLON DX200I " + i + " CONFORMES AVANT ACTIVATION");

                        } else {

                            //SerrureWeb.contexte.getActifs()[i] = true;
                            SerrureWeb.contexte.getErreurs()[i] = true;
                            System.out.println("#######   CONTACTS ECHANTILLON DX200I " + i + " NON ONFORMES AVANT ACTIVATION");
                        }

                    }

                }

                if (SerrureWeb.contexte.getTypes()[i].equals("APX200")) {

                    if (SerrureWeb.contexte.getContacteurs()[i]) {

                        if (!sensor && !contact) {

                            System.out.println("#######   CONTACTS ECHANTILLON APX200 " + i + " CONFORMES AVANT ACTIVATION");

                        } else {

                            // SerrureWeb.contexte.getActifs()[i] = true;
                            SerrureWeb.contexte.getErreurs()[i] = true;
                            System.out.println("#######   CONTACTS ECHANTILLON APX200 " + i + " NON CONFORMES AVANT ACTIVATION");
                        }

                    }

                }

                relais[i].high();

                if (SerrureWeb.contexte.getTypes()[i].equals("DX200I")) {

                    // delai anti-rebond
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // désactiver relais
                    relais[i].low();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    sensor = sensors[i].isHigh();
                    System.out.println("Lecture sensor DX200 AP: " + i + " " + sensor);

                    contact = contacts[i].isHigh();
                    System.out.println("Lecture contact DX200 AP: " + i + " " + contact);

                    if (SerrureWeb.contexte.getContacteurs()[i]) {  // Validation test des contacteurs

                        if (!sensor && !contact) {

                            System.out.println("#######   CONTACTS ECHANTILLON DX200I " + i + " CONFORMES APRES ACTIVATION");
                            SerrureWeb.contexte.getTotaux()[i] = SerrureWeb.contexte.getTotaux()[i] + 1L;
                            System.out.println("Total echantillon:" + i + " " + SerrureWeb.contexte.getTotaux()[i]);

                        } else {
                            System.out.println("#######   CONTACTS ECHANTILLON DX200I " + i + " NON CONFORMES APRES ACTIVATION");
                            //SerrureWeb.contexte.getActifs()[i] = true;
                            SerrureWeb.contexte.getErreurs()[i] = true;
                            System.out.println("///////   Test échoué echantillon:" + i);
                        }

                    } else {

                        System.out.println("#######   CONTACTS ECHANTILLON DX200I " + i + " NON PRIS EN COMPTE");
                        SerrureWeb.contexte.getTotaux()[i] = SerrureWeb.contexte.getTotaux()[i] + 1L;
                        System.out.println("Total echantillon:" + i + " " + SerrureWeb.contexte.getTotaux()[i]);

                    }

                }

                if (SerrureWeb.contexte.getTypes()[i].equals("APX200")) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    sensor = sensors[i].isHigh();
                    System.out.println("Lecture sensor APX200 AP: " + i + " " + sensor);

                    contact = contacts[i].isHigh();
                    System.out.println("Lecture contact APX200 AP: " + i + " " + contact);

                    if (SerrureWeb.contexte.getContacteurs()[i]) {

                        if (!sensor && contact) {

                            System.out.println("#######   CONTACTS ECHANTILLON APX200 " + i + " CONFORMES APRES ACTIVATION");
                            SerrureWeb.contexte.getTotaux()[i] = SerrureWeb.contexte.getTotaux()[i] + 1L;
                            System.out.println("Total echantillon:" + i + " " + SerrureWeb.contexte.getTotaux()[i]);

                        } else {

                            System.out.println("#######   CONTACTS ECHANTILLON APX200 " + i + " NON CONFORMES APRES ACTIVATION");
                            //SerrureWeb.contexte.getActifs()[i] = true;
                            SerrureWeb.contexte.getErreurs()[i] = true;
                            System.out.println("///////   Test échoué echantillon:" + i);
                        }

                    } else {

                        System.out.println("#######   CONTACTS ECHANTILLON APX200 " + i + " NON PRIS EN COMPTE");
                        SerrureWeb.contexte.getTotaux()[i] = SerrureWeb.contexte.getTotaux()[i] + 1L;
                        System.out.println("Total echantillon:" + i + " " + SerrureWeb.contexte.getTotaux()[i]);

                    }

                    relais[i].low();
                }

            } else {

                if (SerrureWeb.contexte.getPauses()[i]) {

                    System.err.println(">>>>>>>   ECHANTILLON:" + i + " EN PAUSE");

                }

                if (SerrureWeb.contexte.getErreurs()[i]) {

                    System.err.println(">>>>>>>   ECHANTILLON:" + i + " EN ERREUR");

                }

                if (!SerrureWeb.contexte.getActifs()[i]) {

                    System.err.println(">>>>>>>   Echantillon:" + i + " INACTIF");

                }

            }

        }

        notifierResultat();
        try {
            Thread.sleep(interTest);    // Tempo entte deux tours de test
        } catch (InterruptedException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {

        start();
    }

}
