package bot.discord.yeti.game.jackpot;


import java.io.Serializable;
import java.util.ArrayList;

public class JackpotGameHolder implements Serializable {

    private ArrayList<JackpotGame> jackpotGames = new ArrayList();

    public ArrayList<JackpotGame> getJackPotGame() {
        return jackpotGames;
    }

    public void setJackPotGame(ArrayList<JackpotGame> rouletteGames) {
        this.jackpotGames = rouletteGames;
    }
}
