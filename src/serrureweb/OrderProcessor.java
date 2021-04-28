package serrureweb;

import serrureweb.manager.Contexte;
import serrureweb.manager.Controleur;

public class OrderProcessor {

    // private Contexte contexte;
    public OrderProcessor() {

    }

    public void analyser(String commande) {

        if (commande != null) {

            if (commande.startsWith("@SERV:>") && commande.endsWith(">#")) {

                decodageSimple(commande);

            } else {

                decodageGo(commande);
            }
        }else {
        
            SerrureWeb.contexte.setMarche(false);
            SerrureWeb.contexte.setErreur(true);
            System.out.println("Erreur de connexion. Séquence interrompue!");
        
        }

     
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
            System.out.println("A1:" + actifs[0]);
            actifs[1] = Boolean.parseBoolean(strings[15]);
            System.out.println("A2:" + actifs[1]);
            actifs[2] = Boolean.parseBoolean(strings[25]);
            System.out.println("A3:" + actifs[2]);
            SerrureWeb.contexte.setActifs(actifs);

            System.out.println("A1 contexte:" + SerrureWeb.contexte.getActifs()[0]);
            System.out.println("A2 contexte:" + SerrureWeb.contexte.getActifs()[1]);
            System.out.println("A3 contexte:" + SerrureWeb.contexte.getActifs()[2]);

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

            String[] types = {"NA", "NA", "NA"};
            types[0] = strings[33];
            types[1] = strings[35];
            types[2] = strings[37];
            SerrureWeb.contexte.setTypes(types);

            SerrureWeb.contexte.setMarche(true);
            SerrureWeb.contexte.setActif(true);

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
                case "START":    // Sortie de pause

                    if (strings[2].equals("#")) {
                        SerrureWeb.contexte.setMarche(true);
                        SerrureWeb.contexte.setPause(false);
                        SerrureWeb.contexte.setActif(true);
                        System.out.println("START");
                    } else {

                        if (strings[2].equals("1")) {

                            SerrureWeb.contexte.getActifs()[0] = true;
                            SerrureWeb.contexte.getPauses()[0] = false;
                            SerrureWeb.contexte.getInterrompus()[0] = false;
                            System.out.println("START echantillon 1");

                        }

                        if (strings[2].equals("2")) {

                            SerrureWeb.contexte.getActifs()[1] = true;
                            SerrureWeb.contexte.getPauses()[1] = false;
                            SerrureWeb.contexte.getInterrompus()[1] = false;
                            System.out.println("START echantillon 2");

                        }

                        if (strings[2].equals("3")) {

                            SerrureWeb.contexte.getActifs()[2] = true;
                            SerrureWeb.contexte.getPauses()[2] = false;
                            SerrureWeb.contexte.getInterrompus()[2] = false;
                            System.out.println("START echantillon 2");

                        }

                    }

                    break;
                case "STOP":

                    if (strings[2].equals("#")) {

                        System.out.println("STOP");         // Arrêt de toute la scéance 
                        SerrureWeb.contexte.setMarche(false);
                        SerrureWeb.contexte.setPause(false);
                        SerrureWeb.contexte.setActif(false);

                    } else {

                        if (strings[2].equals("1")) {

                            SerrureWeb.contexte.getActifs()[0] = false;
                            SerrureWeb.contexte.getPauses()[0] = false;
                            SerrureWeb.contexte.getInterrompus()[0] = true;
                            System.out.println("STOP échantillon 1");

                        }

                        if (strings[2].equals("2")) {

                            SerrureWeb.contexte.getActifs()[1] = false;
                            SerrureWeb.contexte.getPauses()[1] = false;
                            SerrureWeb.contexte.getInterrompus()[1] = true;
                            System.out.println("STOP échantillon 2");

                        }

                        if (strings[2].equals("3")) {

                            SerrureWeb.contexte.getActifs()[2] = false;
                            SerrureWeb.contexte.getPauses()[2] = false;
                            SerrureWeb.contexte.getInterrompus()[2] = true;
                            System.out.println("STOP échantillon 3");

                        }

                    }

                    break;
                case "PAUSE":

                    if (strings[2].equals("#")) {// Mise en pause de toute la séquence
                        System.out.println("PAUSE");

                        SerrureWeb.contexte.setMarche(true);
                        SerrureWeb.contexte.setPause(true);
                        SerrureWeb.contexte.setActif(true);

                    } else {

                        if (strings[2].equals("1")) {

                            SerrureWeb.contexte.getActifs()[0] = true;
                            SerrureWeb.contexte.getPauses()[0] = true;
                            SerrureWeb.contexte.getInterrompus()[0] = false;

                            System.out.println("PAUSE échantillon 1");

                        }

                        if (strings[2].equals("2")) {

                            SerrureWeb.contexte.getActifs()[1] = true;
                            SerrureWeb.contexte.getPauses()[1] = true;
                            SerrureWeb.contexte.getInterrompus()[1] = false;
                            System.out.println("PAUSE échantillon 2");

                        }

                        if (strings[2].equals("3")) {

                            SerrureWeb.contexte.getActifs()[2] = true;
                            SerrureWeb.contexte.getPauses()[2] = true;
                            SerrureWeb.contexte.getInterrompus()[2] = false;
                            System.out.println("PAUSE échantillon 3");

                        }

                    }

                    break;
                case "C1":                              // Réinitialisation compteur échantillon 1
                    System.out.println("C1");
                    SerrureWeb.contexte.getTotaux()[0] = Long.parseLong(strings[2]);
                    System.out.println("Init compteur échantillon 1");

                    break;
                case "C2":                              // Réinitialisation compteur échantillon 2
                    System.out.println("C2");
                    SerrureWeb.contexte.getTotaux()[1] = Long.parseLong(strings[2]);
                    System.out.println("Init compteur échantillon 2");
                    break;
                case "C3":                              // Réinitialisation compteur échantillon 3
                    System.out.println("C3");
                    SerrureWeb.contexte.getTotaux()[2] = Long.parseLong(strings[2]);
                    System.out.println("Init compteur échantillon 3");
                    break;

                case "T1":                              // Initilisation type échantillon 1 - Non utilisé
                    System.out.println("T1");

                    break;

                case "T2":                               // Initilisation type échantillon 2 - Non utilisé
                    System.out.println("T2");
                    break;

                case "T3":                                // Initilisation type échantillon 3 - Non utilisé
                    System.out.println("T3");
                    break;

                case "E1":                                  // Signalisation erreur échantillon 1
                    System.out.println("E1");
                    SerrureWeb.contexte.getErreurs()[0] = Boolean.parseBoolean(strings[2]);
                    break;

                case "E2":                                  // Signalisation erreur échantillon 2
                    System.out.println("E2");
                    SerrureWeb.contexte.getErreurs()[1] = Boolean.parseBoolean(strings[2]);
                    break;

                case "E3":                                  // Signalisation erreur échantillon 3
                    System.out.println("E3");
                    SerrureWeb.contexte.getErreurs()[2] = Boolean.parseBoolean(strings[2]);
                    break;

                default:
                    System.out.println("Commande non reconnue");
            }

        } catch (Exception e) {

        }

    }

}
