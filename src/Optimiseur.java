import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Optimiseur {
    private List<Strategie> strat;

    public Optimiseur(){
        strat = new ArrayList<>();

        strat.add(new Strategie());
        strat.add(new Debalancement());
        strat.add(new Surcharge());

    }

    public int chooseTransfo(List<Integer> transfoList){
        return strat.get(2).process(transfoList);
    }

    public void printStats(){

    }
}
