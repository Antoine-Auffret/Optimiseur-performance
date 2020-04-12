import java.util.List;

public class Strategie {
    protected String name;
    protected static int lastProcess;

    public Strategie(){
        this.name = "defaut";
        lastProcess = -1;
    }

    public int process (List<Integer> bufferSizeList){
        lastProcess = (lastProcess+1)%Conf.nbTransfo;
        return lastProcess;
    }

    public String getName(){
        return this.name;
    }
}
