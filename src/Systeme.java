import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Systeme {

    private List<Transformateur> tList;

    private Optimiseur opti;

    private Integer nbTour = 1;

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
        int transfoId;
        String request, response;
        Transformateur transfoToTreat;

        while(nbTour < 500){

            transfoId = opti.chooseTransfo(getTransfoSize());
            transfoToTreat = tList.get(transfoId);

            System.out.println("nbTour : " + nbTour++);

            if(transfoToTreat.getBufferSize() > 0){
                request = transfoToTreat.getElement();

                // Traitement ...

                response = "Y";
                transfoToTreat.sendResponse(response);
            }

            try {
                sleep(100/Conf.nbTransfo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Integer> getTransfoSize(){
        List<Integer> bufferSizeList = new ArrayList<>();

        for(Transformateur t : tList){
            bufferSizeList.add(t.getBufferSize());
        }

        return bufferSizeList;
    }

}
