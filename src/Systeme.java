import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Systeme {

    private ArrayList<Transformateur> tList;

    public Systeme(){
       tList = new ArrayList<>();

       tList.add(new Transformateur());

       run();
    }

    public void run(){
        while(true){

            // Strategie de base
            for (Transformateur t : tList){

                System.out.println("buffer size : " + t.getBufferSize());

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
