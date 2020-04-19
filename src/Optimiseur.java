import java.util.ArrayList;
import java.util.List;

public class Optimiseur {
    private List<Strategie> strat;

    private String strategieName;

    public Optimiseur(){
        // Initialisation de chaque stratégie

        strat = new ArrayList<>();

        strat.add(new Strategie());
        strat.add(new Debalancement());
        strat.add(new Surcharge());
        strat.add(new Hasard());
        strat.add(new Equilibrage());
    }

    // Choisi le transformateur à traité en priorité
    public int chooseTransfo(List<Integer> transfoList, Integer stategieId){
        strategieName = strat.get(stategieId).getName();
        return strat.get(stategieId).process(transfoList);
    }

    // Retourne le nom de la stratégie courrante
    public String getStrategieName() {
        return strategieName;
    }

    // Retourne le nombre de stratégie différente
    public int getStratSize() {
        return strat.size();
    }

    // Retourne le score courant
    public int getScore(Integer strategieId){
        return strat.get(strategieId).getScore();
    }

    // Remet à 0 le score
    public void resetScore(Integer strategieId){
        strat.get(strategieId).setScore(0);
    }
}
