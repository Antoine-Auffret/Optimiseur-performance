import java.util.ArrayList;
import java.util.List;

public class Optimiseur {
    private List<Strategie> strat;

    private String strategieName;

    public Optimiseur(){
        strat = new ArrayList<>();

        Strategie defaut = new Strategie();
        Debalancement debalancement = new Debalancement();
        Surcharge surcharge = new Surcharge();
        Hasard hasard = new Hasard();

        strat.add(defaut);
        strat.add(debalancement);
        strat.add(surcharge);
        strat.add(hasard);
    }

    public int chooseTransfo(List<Integer> transfoList, Integer stategieId){
        strategieName = strat.get(stategieId).getName();
        return strat.get(stategieId).process(transfoList);
    }

    public String getStrategieName() {
        return strategieName;
    }

    public int getStratSize() {
        return strat.size();
    }

    public int getScore(Integer strategieId){
        return strat.get(strategieId).getScore();
    }

    public void resetScore(Integer strategieId){
        strat.get(strategieId).setScore(0);
    }
}
