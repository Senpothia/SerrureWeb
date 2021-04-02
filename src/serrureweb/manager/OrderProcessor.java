package serrureweb.manager;

public class OrderProcessor {

    private Contexte contexte;

    public OrderProcessor() {

    }

    public Contexte getContexte() {
        return contexte;
    }

    public void setContexte(Contexte contexte) {
        this.contexte = contexte;
    }

    public Contexte analyser(String commande) {

        if (commande.startsWith("@SERV:>") && commande.endsWith(">#")) {// ****

            try {

                String[] strings = commande.split(">");

                for (String retval : commande.split(">")) {
                    System.out.println(retval);

                }

                switch (strings[1]) {
                    case "START":
                        System.out.println("START");
                        serrureweb.SerrureWeb.contexte.setMarche(true);

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
                        this.contexte.getTotaux()[0] = Long.parseLong(strings[2]);
                        break;
                    case "C2":
                        System.out.println("C2");
                        this.contexte.getTotaux()[1] = Long.parseLong(strings[2]);
                        break;
                    case "C3":
                        System.out.println("C3");
                        this.contexte.getTotaux()[2] = Long.parseLong(strings[2]);
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
                        this.contexte.getActifs()[0] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "A2":
                        System.out.println("A2");
                        this.contexte.getActifs()[1] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "A3":
                        System.out.println("A3");
                        this.contexte.getActifs()[2] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "E1":
                        System.out.println("E1");
                        this.contexte.getErreurs()[0] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "E2":
                        System.out.println("E2");
                        this.contexte.getErreurs()[1] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "E3":
                        System.out.println("E3");
                        this.contexte.getErreurs()[2] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "P1":
                        System.out.println("P1");
                        this.contexte.getPauses()[0] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "P2":
                        System.out.println("P2");
                        this.contexte.getPauses()[1] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "P3":
                        System.out.println("P3");
                        this.contexte.getPauses()[2] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "I1":
                        System.out.println("I1");
                        this.contexte.getInterrompus()[0] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "I2":
                        System.out.println("I2");
                        this.contexte.getInterrompus()[1] = Boolean.parseBoolean(strings[2]);
                        break;

                    case "I3":
                        System.out.println("I2");
                        this.contexte.getInterrompus()[2] = Boolean.parseBoolean(strings[2]);
                        break;
                    default:
                        System.out.println("Commande non reconnue");
                }

            } catch (Exception e) {

            }

        }
        return this.contexte;

    }

}
