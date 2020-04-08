import java.util.LinkedList;

public class Transformateur {

    private LinkedList<String> requestFIFO;

    private int maxListSize = 10;
    private Fournisseur f;

    private String lastRequest;

    private String buffStatus;

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
            f.getResponse(writeBuffStatus(), getBufferSize(), request,"Error : Buffer is full");
        }
    }

    public String writeBuffStatus() {
        int buffSize = getBufferSize();
        int VIDE = 0;
        int OK = 3;
        int DANGER = 7;
        int MAX = 10;

        if (buffSize >= VIDE && buffSize < OK)
            this.buffStatus = "VIDE";
        else if (buffSize >= OK && buffSize < DANGER)
            this.buffStatus = "OK";
        else if (buffSize >= DANGER && buffSize < MAX)
            this.buffStatus = "DANGER";
        else if (buffSize >= MAX)
            this.buffStatus = "MAX";

        return this.buffStatus;
    }

    public String getElement(){
        lastRequest = requestFIFO.removeFirst();
        return lastRequest;
    }

    public void sendResponse(String response){
        f.getResponse(writeBuffStatus(), getBufferSize(), lastRequest, response);
    }

    public int getBufferSize(){
        return requestFIFO.size();
    }
}
