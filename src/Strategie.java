import java.util.List;

public class Strategie {
    protected String name = "Defaut";

    // Id du dernier transformateur traité
    protected static int lastProcess;

    // Score actuel de la stratégie en analyse
    protected static int score = 0;

    protected int cost;

    public Strategie(){
        lastProcess = -1;
    }

    // Méthode qui retourne l'id du transformateur à traité
    // A override pour chaque stratégie enfant
    public int process (List<Integer> bufferSizeList){
        lastProcess = (lastProcess+1)% Conf.nbTransfo;
        score+=1;
        return lastProcess;
    }

    // Retourne le nom de la stratégie
    public String getName(){
        return this.name;
    }

    // Retourne le score actuel
    public int getScore() { return score;}

    // Met à jour le score
    public void setScore(int newScore){
        score = newScore;
    }
}
