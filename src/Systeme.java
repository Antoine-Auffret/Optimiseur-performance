import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Systeme {

    private ArrayList<Transformateur> tList;

    public Systeme(){
       tList = new ArrayList<>();

       int nbTranfo = 4;

       for(int i=0; i<nbTranfo; i++){
           tList.add(new Transformateur());
       }

       run();
    }

    public void run(){
        while(true){

            // Strategie de base
            for (Transformateur t : tList){

                if(t.getBufferSize() > 0){

                    String request = t.getElement();

                    // Traitement ....

                    String response = "Y";
                    t.sendResponse(response);
                }

            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
