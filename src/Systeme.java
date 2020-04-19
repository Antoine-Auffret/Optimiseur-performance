import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Systeme {

    private List<Transformateur> tList;

    private Optimiseur opti;

    private Integer nbTour = 0;

    boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

    private static final Logger LOGGER = Logger.getLogger("");

    public Systeme(){
        if (isDebug) {
            LOGGER.setLevel(Level.FINE);
            LOGGER.getHandlers()[0].setLevel(Level.FINE);
        }

        tList = new ArrayList<>();

        int nbTranfo = Conf.nbTransfo;

        // Instanciation des transformateurs
        for(int i=0; i<nbTranfo; i++){
            tList.add(new Transformateur());
        }

        // Instanciation de l'optimiseur
        opti = new Optimiseur();

        run();
    }

    public void run(){
        int strategieId = 0;
        int stratSize = opti.getStratSize();
        int transfoId;

        double timer = Math.round(((double) Conf.timer / (double) Conf.nbTransfo)*100.0)/100.0;

        String[] arr=String.valueOf(timer).split("\\.");
        int[] intArr=new int[2];
        intArr[0]=Integer.parseInt(arr[0]);
        intArr[1]=Integer.parseInt(arr[1]);

        System.out.println(String.format("%d.%d",intArr[0], intArr[1]));

        String request, response;
        Transformateur transfoToTreat;

        List<Integer> scoreStrat = new ArrayList<>();

        // Header du tableau de stats
        List<String> tableHeader = Arrays.asList("Name", "Score", "Min", "Max", "Rejet", "Moyenne");
        List<String> tableRow = new ArrayList<>();

        System.out.println();

        for ( String column : tableHeader) {
            System.out.print(String.format("%25s", column));
        }

        // On parcoure tout les strategies
        while(strategieId < stratSize){

            // On effectue un nombre de test prédéfini
            for (int nbRun = 1; nbRun <= Conf.nbRun; nbRun++) {
                // On remet à jour le transformateur pour réalier un nouveau test
                for(Transformateur t : tList){
                    t.reset();
                    t.resume();
                }

                opti.resetScore(strategieId);

                // On réalise un nombre de tour prédéfini pour le test
                while (nbTour < Conf.nbProcess) {

                    // On récupère l'id du transformateur à traité
                    transfoId = opti.chooseTransfo(getTransfoSize(), strategieId);
                    transfoToTreat = tList.get(transfoId);

                    nbTour++;

                    // Si le transformateur n'est pas vide, on traite une requête
                    if (transfoToTreat.getBufferSize() > 0) {
                        request = transfoToTreat.getElement();

                        // Traitement ...

                        if(request != "") {
                            // Envoie d'une réponse
                            response = "Y";
                            transfoToTreat.sendResponse(response);
                        }
                    }


                    try {
                        // Temporisation
                        sleep(intArr[0], intArr[1]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int score = opti.getScore(strategieId) + 25*getNbFullError();

                scoreStrat.add(score);

                nbTour = 0;
            }

            // Affiche d'une ligne de statisiques pour la stratégie en cours
            tableRow.add(opti.getStrategieName());
            tableRow.add(String.valueOf(scoreStrat.stream().mapToInt(Integer::intValue).sum()));
            tableRow.add(String.valueOf(scoreStrat.get(scoreStrat.indexOf(Collections.min(scoreStrat)))));
            tableRow.add(String.valueOf(scoreStrat.get(scoreStrat.indexOf(Collections.max(scoreStrat)))));
            tableRow.add(String.format("%d/%d (%.2f%%)", getTotalError(), getNbReqSend(), (double) getTotalError()/getNbReqSend()*100));
            tableRow.add(String.valueOf(Math.round(scoreStrat.stream().mapToInt(Integer::intValue).average().getAsDouble())));

            System.out.println();
            for ( String row : tableRow) {
                System.out.print(String.format("%25s", row));
            }

            tableRow.clear();
            scoreStrat.clear();
            strategieId++;

            // On remet à 0 les stats du transformateur
            for(Transformateur t : tList){
                t.resetStats();
            }
        }

        // On arrête le système
        for(Transformateur t : tList){
            t.stop();
        }
    }

    // Retourne la taille de tout les buffers
    private List<Integer> getTransfoSize(){
        List<Integer> bufferSizeList = new ArrayList<>();

        for(Transformateur t : tList){
            bufferSizeList.add(t.getBufferSize());
        }

        return bufferSizeList;
     }

     // Retourne le nombre total de requête envoyer
    private int getNbReqSend(){
        int totalSend = 0;
        for(Transformateur t : tList){
            totalSend += t.getNbReqSend();
        }
        return totalSend;
    }

    // Retourne le nombre d'erreur durant un run
    private int getNbFullError(){
        int totalError = 0;
        for(Transformateur t : tList){
            totalError += t.getNbErrorFull();
        }
        return totalError;
    }

    // Retourne le nombre total d'erreur lors d'un test
    private int getTotalError(){
        int totalError = 0;
        for(Transformateur t : tList){
            totalError += t.getTotalError();
        }
        return totalError;
    }

}
