
package serrureweb.manager;


public class Resultat {
    
     public long[] totaux = {0,0,0};
     public boolean[] erreurs = {false, false, false};
     public boolean fin = false;
     public boolean changed = false;

    public Resultat() {
        
    }
    
    public Resultat(long[] totaux,boolean[] erreurs) {
        
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

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    
    
}
