package serrureweb.manager;

public class Contexte {
    
    private Boolean marche;   // Etat de la séquence en cours
    private Boolean pause;    // Etat de la séquence en cours
    private Boolean actif;    // Etat de la séquence en cours
    private long[] totaux = {0, 0, 0};
    private boolean[] erreurs = {false, false, false};
    private boolean[] pauses = {false, false, false};
    private boolean[] actifs = {false, false, false};
    private boolean[] interrompus = {false, false, false};
    private String commande;
    private Boolean changed;

    public Contexte() {
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

    public boolean[] getPauses() {
        return pauses;
    }

    public void setPauses(boolean[] pauses) {
        this.pauses = pauses;
    }

    public boolean[] getActifs() {
        return actifs;
    }

    public void setActifs(boolean[] actifs) {
        this.actifs = actifs;
    }

    public boolean[] getInterrompus() {
        return interrompus;
    }

    public void setInterrompus(boolean[] interrompus) {
        this.interrompus = interrompus;
    }

    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public Boolean getMarche() {
        return marche;
    }

    public void setMarche(Boolean marche) {
        this.marche = marche;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    
    

}