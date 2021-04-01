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

public class Controleur extends Observable {

    private boolean marche = false;
    private boolean pause = false;
    public boolean[] echantillons = {false, false, false};
    public Resultat resultat = new Resultat();
    public long[] totaux = {0, 0, 0};
    public boolean[] erreurs = {false, false, false};
    public boolean[] actifs = {false, false, false};

    // GPIO  -- Version Raspberry
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

    private boolean stateSensor1;
    private boolean stateSensor2;
    private boolean stateSensor3;

    private boolean stateContact1;
    private boolean stateContact2;
    private boolean stateContact3;

    private GpioPinDigitalOutput[] relais = {relais1, relais2, relais3};
    private GpioPinDigitalInput[] sensors = {sensor1, sensor2, sensor3};
    private GpioPinDigitalInput[] contacts = {contact1, contact2, contact3};

    public void start() {

        System.out.println("***** Lancement thread  *****");
        boolean echValide = actifs[0] || actifs[1] || actifs[2];
        resultat.setErreurs(erreurs);

        while (marche && echValide) {

            if (!pause) {

                System.out.println("***** Nouvelle sequence  *****");
                for (int i = 0; i < 3; i++) {

                    if (this.actifs[i]) {

                        // activer relais
                        System.out.println("Activation relais: " + i);
                        relais[i].high();
                        // delai anti-rebond
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // désactiver relais
                        relais[i].low();
                        // lecture sensor
                        System.out.println("Lecture sensor: " + i);
                        boolean sensor = sensors[i].isHigh();
                        // lecture contact
                        System.out.println("Lecture contact: " + i);
                        boolean contact = contacts[i].isHigh();
                        // incrémentation compteur - invalidation echantillon
                        if (!sensor && !contact) {

                            totaux[i]++;
                            this.setTotaux(totaux);
                            System.out.println("Total echantillon:" + i + " " + this.totaux[i]);

                        } else {

                            this.actifs[i] = false;
                            this.erreurs[i] = true;
                            this.setErreurs(erreurs);
                            System.out.println("Test échoué echantillon:" + i);
                        }

                        notifierResultat();

                    }

                }

            } else {

                while (pause) {
                }

            }

            echValide = actifs[0] || actifs[1] || actifs[2];
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println("***** Fin de sequence  *****");

    }

    public boolean[] getEchantillons() {
        return echantillons;
    }

    public boolean[] getErreurs() {
        return erreurs;
    }

    public boolean[] getActifs() {
        return actifs;
    }

    public long[] getTotaux() {
        return totaux;
    }

    public void setEchantillons(boolean[] echantillons) {
        this.echantillons = echantillons;
    }

    public void setErreurs(boolean[] erreurs) {
        this.erreurs = erreurs;
        // this.setChanged();
        // this.notifyObservers(this.getErreurs());
    }

    public void setActifs(boolean[] actifs) {
        this.actifs = actifs;
    }

    public void setTotaux(long[] totaux) {
        this.totaux = totaux;

    }

    public void initTotaux(long[] totaux) {
        this.totaux = totaux;
    }

    public boolean isMarche() {
        return marche;
    }

    public void setMarche(boolean marche) {
        this.marche = marche;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
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

    public void notifierResultat() {

        resultat.setErreurs(erreurs);
        resultat.setTotaux(totaux);
        resultat.setFin(!actifs[0] && !actifs[1] && !actifs[2]);
        this.setChanged();
        this.notifyObservers(this.getResultat());

    }

}
