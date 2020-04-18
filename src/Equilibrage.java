import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

public class Equilibrage extends Strategie {
    public Equilibrage(){
        name = "Equilibrage";
        cost = 2;
    }

    @Override
    public int process(List<Integer> bufferSizeList) {
        List<Integer> sortList = new ArrayList<>(bufferSizeList);

        Collections.sort(sortList);

        if(sortList.get(sortList.size()-1) - sortList.get(sortList.size()-2) >= 2){
            score += cost;
            return bufferSizeList.indexOf(sortList.get(sortList.size()-1));
        }
        else{
            return super.process(bufferSizeList);
        }
    }
}
