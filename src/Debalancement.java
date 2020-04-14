import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Debalancement extends Strategie {
    public Debalancement(){
        name = "Debalancement";
    }

    @Override
    public int process(List<Integer> bufferSizeList) {
        int max = Collections.max(bufferSizeList);

        List<Integer> maxIndices = new ArrayList<>();
        for (int i = 0; i < bufferSizeList.size(); i++) {
            if (bufferSizeList.get(i).equals(max)) {
                maxIndices.add(i);
            }
        }

        if(maxIndices.size() == 1){
            score+=2;
            lastProcess = maxIndices.get(0);
            return lastProcess;
        }
        else{
            return super.process(bufferSizeList);
        }
    }
}
