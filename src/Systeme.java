import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Systeme {

    private List<Transformateur> tList;

    private Optimiseur opti;

    private Integer nbTour = 0;

    public Systeme(){
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
        int stratSize = opti.getStrat().size();
        int transfoId;
        String request, response;
        Transformateur transfoToTreat;

        while(strategieId < stratSize){

            for (int nbRun = 1; nbRun <= 2; nbRun++) {
                for(Transformateur t : tList){
                    t.resume();
                }

                opti.resetScore(strategieId);

                while (nbTour <= 500) {

                    transfoId = opti.chooseTransfo(getTransfoSize(), strategieId);
                    System.out.println("Strategie : " + opti.getStrategieName());
                    transfoToTreat = tList.get(transfoId);

                    System.out.println("nbTour : " + nbTour++);

                    if (transfoToTreat.getBufferSize() > 0) {
                        request = transfoToTreat.getElement();

                        // Traitement ...

                        response = "Y";
                        transfoToTreat.sendResponse(response);
                    }

                    try {
                        sleep(100 / Conf.nbTransfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (Transformateur t : tList) {
                    t.pause();
                }

                int score = opti.getScore(strategieId) + 25*getNbFullError();
                System.out.println("Score : " + score);

                System.out.println("Press Enter key to continue...");
                Scanner s = new Scanner(System.in);
                s.nextLine();

                nbTour = 0;
            }
            strategieId++;
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
