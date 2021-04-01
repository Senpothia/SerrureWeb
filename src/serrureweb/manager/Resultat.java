package serrureweb.manager;

public class Resultat {

    public long[] totaux = {0, 0, 0};
    public boolean[] erreurs = {false, false, false};
    public boolean[] actifs = {false, false, false};
    public boolean[] pauses = {false, false, false};
    public boolean[] interrompus = {false, false, false};
    public boolean fin = false;

    public Resultat() {

    }

    public Resultat(long[] totaux, boolean[] erreurs) {

        this.totaux = totaux;
        this.erreurs = erreurs;
        this.fin = false;

    }

    public long[] getTotaux() {
        return totaux;
    }

    public void setTotaux(long[] totaux) {
        this.totaux = totaux;
    }

    public boolean[] getErreurs() {
        return erreurs;
    }

    public void setErreurs(boolean[] erreurs) {
        this.erreurs = erreurs;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public boolean[] getActifs() {
        return actifs;
    }

    public void setActifs(boolean[] actifs) {
        this.actifs = actifs;
    }

    public boolean[] getPauses() {
        return pauses;
    }

    public void setPauses(boolean[] pauses) {
        this.pauses = pauses;
    }

    public boolean[] getInterrompus() {
        return interrompus;
    }

    public void setInterrompus(boolean[] interrompus) {
        this.interrompus = interrompus;
    }

   
    
    
    
    

}
