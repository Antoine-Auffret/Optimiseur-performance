import java.util.List;

public class Strategie {
    protected String name = "defaut";
    protected static int lastProcess;

    public Strategie(){
        lastProcess = -1;
    }

    public int process (List<Integer> bufferSizeList){
        lastProcess = (lastProcess+1)% Conf.nbTransfo;
        return lastProcess;
    }

    public String getName(){
        return this.name;
    }
}
