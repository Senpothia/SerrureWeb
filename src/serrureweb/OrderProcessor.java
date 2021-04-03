package serrureweb;

import serrureweb.manager.Contexte;

public class OrderProcessor {

    // private Contexte contexte;
    public OrderProcessor() {

    }

    public void analyser(String commande) {

        if (commande.startsWith("@SERV:>") && commande.endsWith(">#")) {
            
            decodageSimple(commande);

        } else {

            decodageGo(commande);
        }
        
        System.out.println(SerrureWeb.contexte.toString());

    }

    private void decodageGo(String commande) {

        if (commande.startsWith("@SERV:GO>") && commande.endsWith(">#")) {// ****

            String[] strings = commande.split(">");

            for (String retval : commande.split(">")) {
                System.out.println(retval);

            }
            long[] totaux = {0, 0, 0};
            totaux[0] = Long.parseLong(strings[3]);
            totaux[1] = Long.parseLong(strings[13]);
            totaux[2] = Long.parseLong(strings[23]);
            SerrureWeb.contexte.setTotaux(totaux);

            boolean[] actifs = {false, false, false};
            actifs[0] = Boolean.parseBoolean(strings[5]);
            actifs[1] = Boolean.parseBoolean(strings[15]);
            actifs[2] = Boolean.parseBoolean(strings[25]);
            SerrureWeb.contexte.setActifs(actifs);

            boolean[] erreurs = {false, false, false};
            erreurs[0] = Boolean.parseBoolean(strings[7]);
            erreurs[1] = Boolean.parseBoolean(strings[17]);
            erreurs[2] = Boolean.parseBoolean(strings[27]);
            SerrureWeb.contexte.setErreurs(erreurs);

            boolean[] pauses = {false, false, false};
            pauses[0] = Boolean.parseBoolean(strings[9]);
            pauses[1] = Boolean.parseBoolean(strings[19]);
            pauses[2] = Boolean.parseBoolean(strings[29]);
            SerrureWeb.contexte.setPauses(pauses);

            boolean[] interrompus = {false, false, false};
            interrompus[0] = Boolean.parseBoolean(strings[11]);
            interrompus[1] = Boolean.parseBoolean(strings[21]);
            interrompus[2] = Boolean.parseBoolean(strings[31]);
            SerrureWeb.contexte.setInterrompus(interrompus);
            
            SerrureWeb.contexte.setMarche(true);
            SerrureWeb.contexte.setActif(true);  
            SerrureWeb.contexte.setChanged(true);
            
        } else {

        }
    }

    private void decodageSimple(String commande) {

        try {

            String[] strings = commande.split(">");

            for (String retval : commande.split(">")) {
                System.out.println(retval);

            }

            switch (strings[1]) {
                case "START":
                    System.out.println("START");
                    SerrureWeb.contexte.setMarche(true);

                    break;
                case "STOP":
                    System.out.println("STOP");
                    serrureweb.SerrureWeb.contexte.setMarche(false);

                    break;
                case "PAUSE":
                    System.out.println("PAUSE");
                    serrureweb.SerrureWeb.contexte.setPause(true);
                    break;
                case "C1":
                    System.out.println("C1");
                    SerrureWeb.contexte.getTotaux()[0] = Long.parseLong(strings[2]);
                    break;
                case "C2":
                    System.out.println("C2");
                    SerrureWeb.contexte.getTotaux()[1] = Long.parseLong(strings[2]);
                    break;
                case "C3":
                    System.out.println("C3");
                    SerrureWeb.contexte.getTotaux()[2] = Long.parseLong(strings[2]);
                    break;

                case "T1":
                    System.out.println("T1");

                    break;

                case "T2":
                    System.out.println("T2");
                    break;

                case "T3":
                    System.out.println("T3");
                    break;

                case "A1":
                    System.out.println("A1");
                    SerrureWeb.contexte.getActifs()[0] = Boolean.parseBoolean(strings[2]);
                    break;

                case "A2":
                    System.out.println("A2");
                    SerrureWeb.contexte.getActifs()[1] = Boolean.parseBoolean(strings[2]);
                    break;

                case "A3":
                    System.out.println("A3");
                    SerrureWeb.contexte.getActifs()[2] = Boolean.parseBoolean(strings[2]);
                    break;

                case "E1":
                    System.out.println("E1");
                    SerrureWeb.contexte.getErreurs()[0] = Boolean.parseBoolean(strings[2]);
                    break;

                case "E2":
                    System.out.println("E2");
                    SerrureWeb.contexte.getErreurs()[1] = Boolean.parseBoolean(strings[2]);
                    break;

                case "E3":
                    System.out.println("E3");
                    SerrureWeb.contexte.getErreurs()[2] = Boolean.parseBoolean(strings[2]);
                    break;

                case "P1":
                    System.out.println("P1");
                    SerrureWeb.contexte.getPauses()[0] = Boolean.parseBoolean(strings[2]);
                    break;

                case "P2":
                    System.out.println("P2");
                    SerrureWeb.contexte.getPauses()[1] = Boolean.parseBoolean(strings[2]);
                    break;

                case "P3":
                    System.out.println("P3");
                    SerrureWeb.contexte.getPauses()[2] = Boolean.parseBoolean(strings[2]);
                    break;

                case "I1":
                    System.out.println("I1");
                    SerrureWeb.contexte.getInterrompus()[0] = Boolean.parseBoolean(strings[2]);
                    break;

                case "I2":
                    System.out.println("I2");
                    SerrureWeb.contexte.getInterrompus()[1] = Boolean.parseBoolean(strings[2]);
                    break;

                case "I3":
                    System.out.println("I2");
                    SerrureWeb.contexte.getInterrompus()[2] = Boolean.parseBoolean(strings[2]);
                    break;
                default:
                    System.out.println("Commande non reconnue");
            }

        } catch (Exception e) {

        }

    }

}
