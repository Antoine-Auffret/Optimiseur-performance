import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fournisseur extends Thread {

    private Transformateur t;

    private int id;

    private static int _id = 0;

    private boolean pause;

    boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

    private static final Logger LOGGER = Logger.getLogger("");

    public Fournisseur(Transformateur tInit){
        if (isDebug){
            LOGGER.setLevel(Level.FINE);
            LOGGER.getHandlers()[0].setLevel(Level.FINE);
        }
        LOGGER.fine("Initialisation d'un fournisseur");
        t = tInit;
        id=_id++;
    }

    public void run() {
        int min = (int) Math.round(Conf.timer*0.5);
        int max = (int) Math.round(Conf.timer*1.2);

        pause=false;

        while(true) {
            int randomTime = ThreadLocalRandom.current().nextInt(min, max + 1);

            try {
                sleep(randomTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!pause){
                sendRequest();
            }
        }
    }

    private void sendRequest(){
        String request = "Vent gas ?";
        t.getRequest(request);
    }

    public void getResponse(String buffStatus, Integer bufferSize, String request, String response){
        String log = String.format("Id : %d | BuffStatus : %s | BuffSize : %d | Req : %s | Resp : %s", id, buffStatus, bufferSize, request, response);
        LOGGER.fine(log);
    }

    public void pause(){
        pause=true;
    }

    public void restart(){
        pause=false;
    }
}
