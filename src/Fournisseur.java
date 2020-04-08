import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Fournisseur extends Thread {

    private Transformateur t;

    public Fournisseur(Transformateur tInit){
        System.out.println("Initialisation d'un fournisseur");
        t = tInit;
    }

    public void run() {
        int min = 500;
        int max = 1500;

        while(true) {
            int randomTime = ThreadLocalRandom.current().nextInt(min, max + 1);

            try {
                TimeUnit.MILLISECONDS.sleep(randomTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sendRequest();
        }
    }

    private void sendRequest(){
        String request = "Vent gas ?";
        t.getRequest(request);
    }

    public void getResponse(String request, String response){
        String log = String.format("Req : %s / Resp : %s", request, response);
        System.out.println(log);
    }
}
