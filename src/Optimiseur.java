import java.util.ArrayList;
import java.util.List;

public class Optimiseur {
    private List<Strategie> strat;

    public Optimiseur(){
        strat = new ArrayList<>();

        strat.add(new Strategie());
        strat.add(new Debalancement());
        strat.add(new Surcharge());

    }

    public int chooseTransfo(List<Integer> transfoList){
        return strat.get(0).process(transfoList);
    }

    public int getScore(){
        return strat.get(0).getScore();
    }

    public void resetScore(){
        strat.get(0).setScore(0);
    }
}
