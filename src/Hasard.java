import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hasard extends Strategie {
    public Hasard(){
        name = "Hasard";
        cost = 1;
    }

    @Override
    public int process(List<Integer> bufferSizeList) {
        Random rand = new Random();
        score += cost;
        int newRand = rand.nextInt(bufferSizeList.size());
        return newRand;
    }
}
