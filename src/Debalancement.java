import java.util.Collections;
import java.util.List;

public class Debalancement extends Strategie {
    public Debalancement(){
        name = "Debalancement";
    }

    @Override
    public int process(List<Integer> bufferSizeList) {
        lastProcess = bufferSizeList.indexOf(Collections.max(bufferSizeList));
        return lastProcess;
    }
}
