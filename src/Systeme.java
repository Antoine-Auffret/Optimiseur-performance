import java.util.ArrayList;
import java.util.List;

public class Systeme {

    private List<Transformateur> tList;

    public Systeme(){
       tList = new ArrayList();

       tList.add(new Transformateur());

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
        }
    }

}
