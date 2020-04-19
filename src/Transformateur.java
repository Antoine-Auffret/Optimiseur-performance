import java.io.ObjectInputFilter;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Transformateur {

    private ConcurrentLinkedQueue<String> requestFIFO;

    private Fournisseur f;

    private String lastRequest;

    private int cptErrorFull = 0;
    private int cptReqSend = 0;
    private int totalError = 0;

    public Transformateur(){

        requestFIFO = new ConcurrentLinkedQueue<>();

        f = new Fournisseur(this);
        f.start();
    }

    // Recoit une requête de Fournisseur
    public void getRequest(String request){
        cptReqSend++;

        if(getBufferSize() < Conf.maxBufferSize){
            requestFIFO.add(request);
        }
        else{
            cptErrorFull++;
            f.getResponse(writeBuffStatus(), getBufferSize(), request,"Error : Buffer is full");
        }
    }

    // Retourne le status du buffer
    public String writeBuffStatus() {
        int buffSize = getBufferSize();

        Capacite[] capacities = Capacite.values();

        for (int i=0; i<capacities.length-1; i++)
        {
            if(buffSize >= capacities[i].getValue() && buffSize < capacities[i+1].getValue()){
                return capacities[i].name();
            }
        }

        return capacities[capacities.length - 1].name();
    }

    // Retourne l'élément le plus vieux de la queue si la queue n'est pas vide (FIFO)
    public String getElement(){
        if(getBufferSize() > 0){
            lastRequest = requestFIFO.remove();
            return lastRequest;
        }
        else{
            return "";
        }
    }

    // Envoie une réponse pour une requête
    public void sendResponse(String response){
        f.getResponse(writeBuffStatus(), getBufferSize(), lastRequest, response);
    }

    // Réinitialise des éléments nécessaire pour un prochain test
    public void reset() {
        requestFIFO.clear();
        totalError += cptErrorFull;
        cptErrorFull = 0;
        f.pause();
    }

    // Redémarre le fournisseur
    public void resume(){
        f.restart();
    }

    // Retourne la taille du buffer
    public int getBufferSize(){
        return requestFIFO.size();
    }

    // Retourne le nombre d'erreur d'une itération
    public int getNbErrorFull(){
        return cptErrorFull;
    }

    // Retourne le nombre total d'erreur
    public int getTotalError(){
        return totalError;
    }

    // Retourne le nombre total de requete envoyé par le fournisseur
    public int getNbReqSend(){
        return cptReqSend;
    }

    // Remet à 0 les compteurs nécesaire pour les statistiques
    public void resetStats(){
        cptReqSend = 0;
        totalError = 0;
    }

    // Arrête le thread du fournisseur
    public void stop(){
        f.shutdown();
    }
}
