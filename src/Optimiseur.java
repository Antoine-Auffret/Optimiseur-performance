import java.util.List;

public class Optimiseur {
    private Strategie strat;

    public Optimiseur(){
        strat = new Strategie();
    }

    public int chooseTransfo(List<Integer> transfoList){
        return strat.process(transfoList);
    }

    public void printStats(){

    }
}
