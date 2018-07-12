package bot.discord.yeti.game.roulette;

import java.io.Serializable;
import java.util.ArrayList;

public class RouletteGameHolder implements Serializable {

    private ArrayList<RouletteGame> rouletteGames = new ArrayList();

    public ArrayList<RouletteGame> getRouletteGames() {
        return rouletteGames;
    }

    public void setRouletteGames(ArrayList<RouletteGame> rouletteGames) {
        this.rouletteGames = rouletteGames;
    }
}
