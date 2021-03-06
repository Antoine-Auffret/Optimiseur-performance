import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fournisseur extends Thread {

    private Transformateur t;

    private int id;

    private static int _id = 0;

    private boolean pause;
    private boolean isRunning;

    // Vaut true si le programme est lancé en mode debug, sinon vaut false.
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

        // Le temps minimum et maximum entre deux envoie de requête.
        double min = Math.round((Conf.timer*0.5)*100)/100.0;
        double max = Math.round((Conf.timer*1.1)*100)/100.0;

        pause=false;
        isRunning=true;

        while(isRunning) {
            double randomTime = Math.round(ThreadLocalRandom.current().nextDouble(min, max + 1)*100.0)/100.0;

            String[] arr=String.valueOf(randomTime).split("\\.");
            int[] intArr=new int[2];
            intArr[0]=Integer.parseInt(arr[0]);
            intArr[1]=Integer.parseInt(arr[1]);

            try {
                sleep(intArr[0], intArr[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!pause){
                // Envoie d'une requête
                sendRequest();
            }
        }
    }

    // Envoie d'une requête au transformateur
    private void sendRequest(){
        String request = "Vent gas ?";
        t.getRequest(request);
    }

    // Affiche la réponse d'une requête (en mode debug)
    public void getResponse(String buffStatus, Integer bufferSize, String request, String response){
        String log = String.format("Id : %d | BuffStatus : %s | BuffSize : %d | Req : %s | Resp : %s", id, buffStatus, bufferSize, request, response);
        LOGGER.fine(log);
    }

    // Permet d'arréter les envoie de requête manière temporaire
    public void pause(){
        pause=true;
    }

    // Permet de relancer l'envoie de requête
    public void restart(){
        pause=false;
    }

    // Arrêt du thread
    public void shutdown(){isRunning=false;}
}
