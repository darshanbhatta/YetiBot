package bot.discord.yeti.game.connect4;

import java.io.Serializable;
import java.util.ArrayList;

public class Connect4Holder implements Serializable {

    ArrayList<Connect4Game> connect4GameArrayList = new ArrayList<>();

    public ArrayList<Connect4Game> getConnect4GameArrayList() {
        return connect4GameArrayList;
    }

    public void setConnect4GameArrayList(ArrayList<Connect4Game> ticTacToeGameArrayList) {
        this.connect4GameArrayList = ticTacToeGameArrayList;
    }
}
