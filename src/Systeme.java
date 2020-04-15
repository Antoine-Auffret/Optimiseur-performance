import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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

        for(int i=0; i<nbTranfo; i++){
            tList.add(new Transformateur());
        }

        opti = new Optimiseur();

        run();
    }

    public void run(){
        int strategieId = 0;
        int stratSize = opti.getStratSize();
        int transfoId;
        String request, response;
        Transformateur transfoToTreat;

        List<Integer> scoreStrat = new ArrayList<>();

        List<String> tableHeader = Arrays.asList("Name", "Score", "Rejet", "Moyenne");
        List<String> tableRow = new ArrayList<>();

        System.out.println();

        for ( String column : tableHeader) {
            System.out.print(String.format("%15s", column));
        }

        while(strategieId < stratSize){

            for (int nbRun = 1; nbRun <= Conf.nbRun; nbRun++) {
                for(Transformateur t : tList){
                    t.resume();
                }

                opti.resetScore(strategieId);

                opti.chooseTransfo(getTransfoSize(), strategieId);
                //System.out.println("Run : " + nbRun + " | Strategie : " + opti.getStrategieName());

                while (nbTour <= Conf.nbProcess) {

                    transfoId = opti.chooseTransfo(getTransfoSize(), strategieId);
                    transfoToTreat = tList.get(transfoId);

                    LOGGER.fine("nbTour : " + nbTour++);

                    if (transfoToTreat.getBufferSize() > 0) {
                        request = transfoToTreat.getElement();

                        // Traitement ...

                        response = "Y";
                        transfoToTreat.sendResponse(response);
                    }

                    try {
                        sleep(Conf.timer / Conf.nbTransfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (Transformateur t : tList) {
                    t.pause();
                }

                int score = opti.getScore(strategieId) + 25*getNbFullError();

                scoreStrat.add(score);
                /*
                System.out.println("Score : " + score);

                System.out.println("Press Enter key to continue...");
                Scanner s = new Scanner(System.in);
                s.nextLine();*/

                nbTour = 0;
            }

            tableRow.add(opti.getStrategieName());
            tableRow.add(String.valueOf(scoreStrat.stream().mapToInt(Integer::intValue).sum()));
            tableRow.add(String.valueOf(Math.round( ((float) getNbFullError()/Conf.nbProcess)*100)));
            tableRow.add(String.valueOf(Math.round(scoreStrat.stream().mapToInt(Integer::intValue).average().getAsDouble())));

            System.out.println();
            for ( String row : tableRow) {
                System.out.print(String.format("%15s", row));
            }

            tableRow.clear();
            scoreStrat.clear();
            strategieId++;
        }

        for(double score : scoreStrat){
            System.out.format("");
        }
    }

    private List<Integer> getTransfoSize(){
        List<Integer> bufferSizeList = new ArrayList<>();

        for(Transformateur t : tList){
            bufferSizeList.add(t.getBufferSize());
        }

        return bufferSizeList;
     }

    private int getNbFullError(){
        int totalError = 0;
        for(Transformateur t : tList){
            totalError += t.getNbErrorFull();
        }
        return totalError;
    }

}
