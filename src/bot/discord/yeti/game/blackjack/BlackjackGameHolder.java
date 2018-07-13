package bot.discord.yeti.game.blackjack;

import java.io.Serializable;
import java.util.ArrayList;

public class BlackjackGameHolder implements Serializable {
    ArrayList<BlackjackGame> blackjackGames = new ArrayList();

    public ArrayList<BlackjackGame> getBlackjackGames() {
        return blackjackGames;
    }

    public void setBlackjackGames(ArrayList<BlackjackGame> blackjackGames) {
        this.blackjackGames = blackjackGames;
    }
}
