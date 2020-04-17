import java.util.Collections;
import java.util.List;

public class Surcharge extends Strategie{
    public Surcharge(){
        name = "Surcharge";
        cost = 3;
    }

    @Override
    public int process(List<Integer> bufferSizeList) {

        if(lastProcess >= 0){
            if(bufferSizeList.get(lastProcess) >= Capacite.DANGER.getValue()) {
                score+=cost;
                return lastProcess;
            }
            else{
                for(int i=0; i<bufferSizeList.size(); i++){
                    if(bufferSizeList.get(i) >= Capacite.DANGER.getValue()){
                        lastProcess = i;
                        score += cost;
                        return lastProcess;
                    }
                }
                return super.process(bufferSizeList);
            }
        }
        else{
            return super.process(bufferSizeList);
        }
    }
}
