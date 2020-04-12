import org.reflections.Reflections;
import strat.Strategie;

import java.util.List;
import java.util.Set;

public class Optimiseur {
    private Strategie strat;

    public Optimiseur(){
        strat = new Strategie(Conf.nbTransfo);

        Reflections reflections = new Reflections("strat");
        Set<Class<? extends Strategie>> classes = reflections.getSubTypesOf(Strategie.class);

        System.out.println(classes);
    }

    public int chooseTransfo(List<Integer> transfoList){
        return strat.process(transfoList);
    }

    public void printStats(){

    }
}
