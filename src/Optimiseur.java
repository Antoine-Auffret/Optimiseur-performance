import java.util.ArrayList;
import java.util.List;

public class Optimiseur {
    private List<Strategie> strat;

    private String strategieName;

    public Optimiseur(){
        strat = new ArrayList<>();

        strat.add(new Strategie());
        strat.add(new Debalancement());
        strat.add(new Surcharge());
        strat.add(new Hasard());
        strat.add(new Equilibrage());
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
