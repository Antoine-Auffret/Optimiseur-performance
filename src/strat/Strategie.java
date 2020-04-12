package strat;

import java.util.List;

public class Strategie {
    protected String name = "defaut";
    protected static int lastProcess;

    protected static int nbElem;

    public Strategie(int newNbElem){
        nbElem = newNbElem;
        lastProcess = -1;
    }

    public int process (List<Integer> bufferSizeList){
        lastProcess = (lastProcess+1)% nbElem;
        return lastProcess;
    }

    public String getName(){
        return this.name;
    }
}
