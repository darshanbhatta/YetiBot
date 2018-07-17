package bot.discord.yeti.game.numguesser;

import java.io.Serializable;
import java.util.ArrayList;

public class NumGuessHolder implements Serializable {
    ArrayList<NumGuessGame> numGuessHolders = new ArrayList<>();

    public ArrayList<NumGuessGame> getNumGuessHolders() {
        return numGuessHolders;
    }

    public void setNumGuessHolders(ArrayList<NumGuessGame> numGuessHolders) {
        this.numGuessHolders = numGuessHolders;
    }
}
