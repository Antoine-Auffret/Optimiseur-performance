import java.io.ObjectInputFilter;
import java.util.LinkedList;

public class Transformateur {

    private LinkedList<String> requestFIFO;

    private Fournisseur f;

    private String lastRequest;

    private String buffStatus;

    private int cptErrorFull = 0;

    public Transformateur(){

        requestFIFO = new LinkedList<String>();

        f = new Fournisseur(this);
        f.start();
    }

    public void getRequest(String request){
        if(getBufferSize() < Conf.maxBufferSize){
            requestFIFO.add(request);
        }
        else{
            cptErrorFull++;
            f.getResponse(writeBuffStatus(), getBufferSize(), request,"Error : Buffer is full");
        }
    }

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

    public String getElement(){
        lastRequest = requestFIFO.removeFirst();
        return lastRequest;
    }

    public void sendResponse(String response){
        f.getResponse(writeBuffStatus(), getBufferSize(), lastRequest, response);
    }

    public void pause() {
        requestFIFO.clear();

        f.pause();
    }

    public void resume(){
        f.restart();
    }

    public int getBufferSize(){
        return requestFIFO.size();
    }

    public int getNbErrorFull(){
        return cptErrorFull;
    }
}
