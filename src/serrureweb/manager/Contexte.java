package serrureweb.manager;

public class Contexte {
    
    private Boolean marche = false;   // Etat de la séquence en cours
    private Boolean pause = false;    // Etat de la séquence en cours
    private Boolean actif = false;    // Etat de la séquence en cours
    private long[] totaux = {0, 0, 0};
    private boolean[] erreurs = {false, false, false};
    private boolean[] pauses = {false, false, false};
    private boolean[] actifs = {false, false, false};
    private boolean[] interrompus = {false, false, false};
    private String commande = null;
    private String rapport = null;
    private Boolean changed = false;
    private Boolean fin = false;

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

    public Boolean getFin() {
        return fin;
    }

    public void setFin(Boolean fin) {
        this.fin = fin;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }
    
    

    @Override
    public String toString() {
        return "Contexte{" + "marche=" + marche + ", pause=" + pause + ", actif=" + actif + ", totaux=" + totaux + ", erreurs=" + erreurs + ", pauses=" + pauses + ", actifs=" + actifs + ", interrompus=" + interrompus + ", commande=" + commande + ", changed=" + changed + '}';
    }
    
    

}
