import java.util.LinkedList;

public class Transformateur {

    private LinkedList<String> requestFIFO;

    private int maxListSize = 10;
    private Fournisseur f;

    private String lastRequest;

    public Transformateur(){

        requestFIFO = new LinkedList<String>();

        f = new Fournisseur(this);
        f.start();
    }

    public void getRequest(String request){
        if(getBufferSize() < maxListSize){
            requestFIFO.add(request);
        }
        else{
            f.getResponse(request,"Error : Buffer is full");
        }
    }

    public String getElement(){
        lastRequest = requestFIFO.remove();
        return lastRequest;
    }

    public void sendResponse(String response){
        f.getResponse(lastRequest,response);
    }

    public int getBufferSize(){
        return requestFIFO.size();
    }
}
