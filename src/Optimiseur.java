import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Optimiseur {
    private List<Strategie> strat;

    private String strategieName;

    public Optimiseur(){
        strat = new ArrayList<>();

        Strategie defaut = new Strategie();
        Debalancement debalancement = new Debalancement();
        Surcharge surcharge = new Surcharge();

        strat.add(defaut);
        strat.add(debalancement);
        strat.add(surcharge);

    }

    public int chooseTransfo(List<Integer> transfoList, Integer stategieId){
        strategieName = strat.get(stategieId).getName();
        return strat.get(stategieId).process(transfoList);
    }

    public String getStrategieName() {
        return strategieName;
    }

    public List<Strategie> getStrat() {
        return strat;
    }

    public void printStats(){

    }
}
