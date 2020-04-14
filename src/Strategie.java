import java.util.List;

public class Strategie {
    protected String name = "defaut";

    protected static int lastProcess;
    protected static int score;

    public Strategie(){
        lastProcess = -1;
    }

    public int process (List<Integer> bufferSizeList){
        lastProcess = (lastProcess+1)% Conf.nbTransfo;
        score+=1;
        return lastProcess;
    }

    public String getName(){
        return this.name;
    }

    public int getScore() { return score;}
    public void setScore(int newScore){
        score = newScore;
    }
}
