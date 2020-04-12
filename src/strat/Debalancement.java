package strat;

import java.util.Collections;
import java.util.List;

public class Debalancement extends Strategie {
    public Debalancement(int nbElem){
        super(nbElem);
        name = "Debalancement";
    }

    @Override
    public int process(List<Integer> bufferSizeList) {
        lastProcess = bufferSizeList.indexOf(Collections.min(bufferSizeList));
        return lastProcess;
    }
}
